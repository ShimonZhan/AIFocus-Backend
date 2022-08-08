package org.cmyk.aifocus.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.constants.ExamStatus;
import org.cmyk.aifocus.constants.QuestionType;
import org.cmyk.aifocus.constants.ResponseStatus;
import org.cmyk.aifocus.dao.ExamMarkRecordMapper;
import org.cmyk.aifocus.dao.ExamMarkSummaryMapper;
import org.cmyk.aifocus.entity.*;
import org.cmyk.aifocus.service.AnswerSheetService;
import org.cmyk.aifocus.service.ExamService;
import org.cmyk.aifocus.service.MarkService;
import org.cmyk.aifocus.service.UserService;
import org.cmyk.aifocus.utils.ExamPaper;
import org.cmyk.aifocus.utils.ExamResult;
import org.cmyk.aifocus.utils.ExamResultQuestion;
import org.cmyk.aifocus.utils.Response;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class MarkServiceImpl implements MarkService {
    @Resource
    private UserService userService;

    @Resource
    private AnswerSheetService answerSheetService;

    @Resource
    private ExamService examService;

    @Resource
    private ExamMarkRecordMapper examMarkRecordMapper;

    @Resource
    private ExamMarkSummaryMapper examMarkSummaryMapper;

    @Resource
    private RedisLockRegistry redisLockRegistry;

    @Override
    public Response<Null> submitPaper(List<AnswerSheet> answerSheets) {
        String studentId = answerSheets.get(0).getStudentId();
        if (userService.getById(studentId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "该学生不存在", null);
        }
        ExamMarkRecord examMarkRecord = examMarkRecordMapper.selectOne(Wrappers.lambdaQuery(ExamMarkRecord.class)
                .eq(ExamMarkRecord::getStudentId, studentId)
                .eq(ExamMarkRecord::getExamId, answerSheets.get(0).getExamId()));
        if (examMarkRecord == null) {
            return new Response<>(ResponseStatus.FAILURE, "学生没参加该场考试或考试不存在", null);
        }
        if (examMarkRecord.getIsSubmit()) {
            return new Response<>(ResponseStatus.SUCCESS, "已提交过该试卷", null);
        }
        answerSheetService.saveBatch(answerSheets);
        examMarkRecord.setIsSubmit(true);
        examMarkRecord.updateById();
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    @Async
    public Future<Boolean> markPaper(String examId, String studentId) {
        ExamMarkRecord examMarkRecord = examMarkRecordMapper.selectOne(Wrappers.lambdaQuery(ExamMarkRecord.class)
                .eq(ExamMarkRecord::getExamId, examId).eq(ExamMarkRecord::getStudentId, studentId));

        // 如果该记录为提交了
        if (examMarkRecord.getIsSubmit()) {
            ExamPaper examPaper = examService.getExamPaper(examId, true).getContent();

            // 创建 Map<题号,试卷试题> 方便下面运算
            Map<String, ExamPaperBlank> blankMap = examPaper.getBlanks()
                    .stream().collect(Collectors.toMap(ExamPaperBlank::getId, e -> e));
            Map<String, ExamPaperJudge> judgeMap = examPaper.getJudges()
                    .stream().collect(Collectors.toMap(ExamPaperJudge::getId, e -> e));
            Map<String, ExamPaperSingle> singleMap = examPaper.getSingles()
                    .stream().collect(Collectors.toMap(ExamPaperSingle::getId, e -> e));
            Map<String, ExamPaperMultiple> multipleMap = examPaper.getMultiples()
                    .stream().collect(Collectors.toMap(ExamPaperMultiple::getId, e -> e));

            // 找到这个人的答题卡的所有记录
            List<AnswerSheet> answerSheets = answerSheetService.list(Wrappers.lambdaQuery(AnswerSheet.class)
                    .eq(AnswerSheet::getExamId, examId).eq(AnswerSheet::getStudentId, studentId));

            // 4种题型分别记分
            Integer blankScore = 0;
            Integer judgeScore = 0;
            Integer singleScore = 0;
            Integer multipleScore = 0;

            // 遍历答题卡，直接在4个Map中匹配id即可
            for (AnswerSheet answerSheet : answerSheets) {
                String examPaperId = answerSheet.getExamPaperId();
                if (answerSheet.getQuestionType().equals(QuestionType.SINGLE)) {
                    ExamPaperSingle trueAnswer = singleMap.get(examPaperId);
                    if (Integer.parseInt(answerSheet.getAnswer()) == trueAnswer.getAnswer()) {
                        answerSheet.setScore(trueAnswer.getScore());
                        singleScore += trueAnswer.getScore();
                    }
                } else if (answerSheet.getQuestionType().equals(QuestionType.MULTIPLE)) {
                    ExamPaperMultiple trueAnswer = multipleMap.get(examPaperId);
                    if (Integer.parseInt(answerSheet.getAnswer()) == trueAnswer.getAnswer()) {
                        answerSheet.setScore(trueAnswer.getScore());
                        multipleScore += trueAnswer.getScore();
                    }
                } else if (answerSheet.getQuestionType().equals(QuestionType.BLANK)) {
                    ExamPaperBlank trueAnswer = blankMap.get(examPaperId);
                    if (answerSheet.getAnswer().equals(trueAnswer.getAnswer())) {
                        answerSheet.setScore(trueAnswer.getScore());
                        blankScore += trueAnswer.getScore();
                    }
                } else if (answerSheet.getQuestionType().equals(QuestionType.JUDGE)) {
                    ExamPaperJudge trueAnswer = judgeMap.get(examPaperId);
                    if (Integer.parseInt(answerSheet.getAnswer()) == trueAnswer.getAnswer()) {
                        answerSheet.setScore(trueAnswer.getScore());
                        judgeScore += trueAnswer.getScore();
                    }
                }
                answerSheet.setIsMarked(true);
            }

            answerSheetService.updateBatchById(answerSheets);

            // 将计算结果放回数据库中，并标记为已经批改
            examMarkRecord.setJudgeScore(judgeScore);
            examMarkRecord.setBlankScore(blankScore);
            examMarkRecord.setSingleScore(singleScore);
            examMarkRecord.setMultipleScore(multipleScore);
            examMarkRecord.setTotalScore(judgeScore + blankScore + singleScore + multipleScore);
            examMarkRecord.setIsMarked(true);
            examMarkRecord.updateById();
        }
        // redis分布式锁确保资源安全，根据exam-id锁唯一一个资源
        Lock lock = redisLockRegistry.obtain("examMarkSummary-Lock-" + examId);
        lock.lock();
        examMarkSummaryAddOne(examId);
        lock.unlock();
        return AsyncResult.forValue(true);
    }


    private void examMarkSummaryAddOne(String examId) {
        ExamMarkSummary examMarkSummary = examMarkSummaryMapper.selectOne(Wrappers.lambdaQuery(ExamMarkSummary.class)
                .eq(ExamMarkSummary::getExamId, examId));
        examMarkSummary.setMarkStudentNumber(examMarkSummary.getMarkStudentNumber() + 1);
        examMarkSummary.updateById();
        log.debug("examMarkSummary-" + examId + " markStudent:" + examMarkSummary.getMarkStudentNumber());

        if (examMarkSummary.getMarkStudentNumber().equals(examMarkSummary.getTotalStudentNumber())) {
            examService.update(Wrappers.lambdaUpdate(Exam.class)
                    .eq(Exam::getId, examId).set(Exam::getStatus, ExamStatus.AUTO_MARK_FINISH));
            log.info(examId + "全部批改完成");
        }
    }

    @Override
    public Response<ExamResult> getStudentExamResult(String examId, String studentId) {
        if (userService.getById(studentId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "学生不存在", null);
        }

        ExamMarkRecord examMarkRecord = examMarkRecordMapper.selectOne(Wrappers.lambdaQuery(ExamMarkRecord.class)
                .eq(ExamMarkRecord::getExamId, examId).eq(ExamMarkRecord::getStudentId, studentId));

        if (examMarkRecord == null) {
            return new Response<>(ResponseStatus.FAILURE, "学生没参加该场考试或考试不存在", null);
        }

        List<ExamResultQuestion> questions = answerSheetService.list(Wrappers.lambdaQuery(AnswerSheet.class)
                .eq(AnswerSheet::getExamId, examId).eq(AnswerSheet::getStudentId, studentId))
                .stream()
                .map(e -> ExamResultQuestion.builder()
                        .examPaperId(e.getExamPaperId())
                        .questionType(e.getQuestionType())
                        .answer(e.getAnswer())
                        .score(e.getScore())
                        .build())
                .collect(Collectors.toList());

        ExamResult examResult = ExamResult.builder()
                .studentId(examMarkRecord.getStudentId())
                .state(examMarkRecord.getState())
                .blankScore(examMarkRecord.getBlankScore())
                .judgeScore(examMarkRecord.getJudgeScore())
                .multipleScore(examMarkRecord.getMultipleScore())
                .singleScore(examMarkRecord.getSingleScore())
                .subjectiveScore(examMarkRecord.getSubjectiveScore())
                .totalScore(examMarkRecord.getTotalScore())
                .questions(questions)
                .build();

        return new Response<>(ResponseStatus.SUCCESS, "", examResult);
    }

    @Override
    public Response<Null> deletePaper(String examId, String studentId) {
        if (userService.getById(studentId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "学生不存在", null);
        }
        ExamMarkRecord examMarkRecord = examMarkRecordMapper.selectOne(Wrappers.lambdaQuery(ExamMarkRecord.class)
                .eq(ExamMarkRecord::getExamId, examId).eq(ExamMarkRecord::getStudentId, studentId));

        if (examMarkRecord == null) {
            return new Response<>(ResponseStatus.FAILURE, "学生没参加该场考试或考试不存在", null);
        }

        answerSheetService.remove(Wrappers.lambdaQuery(AnswerSheet.class)
                .eq(AnswerSheet::getStudentId, studentId).eq(AnswerSheet::getExamId, examId));
        examMarkRecord.setIsSubmit(false);
        examMarkRecord.updateById();
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    public Response<List<AnswerSheet>> getManualMarkAnswerSheet(String examId, Boolean isAll) {
        Exam exam = examService.getById(examId);
        if (exam == null) {
            return new Response<>(ResponseStatus.FAILURE, "该考试不存在", null);
        }
        if (!exam.getStatus().equals(ExamStatus.AUTO_MARK_FINISH)) {
            return new Response<>(ResponseStatus.FAILURE, "该考试状态不在自动批改完成状态", null);
        }
        List<AnswerSheet> answerSheets = answerSheetService.list(Wrappers.lambdaQuery(AnswerSheet.class)
                .eq(AnswerSheet::getExamId, examId)
                .eq(!isAll, AnswerSheet::getIsMarked, false)
                .eq(AnswerSheet::getQuestionType, QuestionType.SUBJECTIVE));
        return new Response<>(ResponseStatus.SUCCESS, "", answerSheets);
    }

    @Override
    public Response<Null> manualMark(String answerSheetId, Integer score) {
        AnswerSheet answerSheet = answerSheetService.getById(answerSheetId);
        if (answerSheet == null) {
            return new Response<>(ResponseStatus.FAILURE, "该记录不存在", null);
        }
        ExamMarkRecord examMarkRecord = examMarkRecordMapper.selectOne(Wrappers.lambdaQuery(ExamMarkRecord.class)
                .eq(ExamMarkRecord::getExamId, answerSheet.getExamId())
                .eq(ExamMarkRecord::getStudentId, answerSheet.getStudentId()));

        int addition;
        if (answerSheet.getIsMarked()) {
            addition = score - answerSheet.getScore();
        } else {
            addition = score;
            answerSheet.setIsMarked(true);
        }
        examMarkRecord.setTotalScore(examMarkRecord.getTotalScore() + addition);
        examMarkRecord.setSubjectiveScore(examMarkRecord.getSubjectiveScore() + addition);
        examMarkRecord.updateById();
        answerSheet.setScore(score);
        answerSheet.updateById();
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    public Response<List<ExamResult>> getExamResult(String examId) {
        if (examService.getById(examId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "该考试不存在", null);
        }

        Map<String, List<ExamResultQuestion>> studentQuestions =
                answerSheetService.list(Wrappers.lambdaQuery(AnswerSheet.class)
                        .eq(AnswerSheet::getExamId, examId))
                        .stream()
                        .collect(Collectors.groupingBy(AnswerSheet::getStudentId,
                                Collectors.mapping(e -> ExamResultQuestion.builder()
                                                .examPaperId(e.getExamPaperId())
                                                .questionType(e.getQuestionType())
                                                .answer(e.getAnswer())
                                                .score(e.getScore())
                                                .build(),
                                        Collectors.toList())));

        List<ExamResult> examResults =
                examMarkRecordMapper.selectList(Wrappers.lambdaQuery(ExamMarkRecord.class)
                        .eq(ExamMarkRecord::getExamId, examId))
                        .stream()
                        .map(e -> ExamResult.builder()
                                .studentId(e.getStudentId())
                                .state(e.getState())
                                .blankScore(e.getBlankScore())
                                .judgeScore(e.getJudgeScore())
                                .multipleScore(e.getMultipleScore())
                                .singleScore(e.getSingleScore())
                                .subjectiveScore(e.getSubjectiveScore())
                                .totalScore(e.getTotalScore())
                                .questions(studentQuestions.get(e.getStudentId()))
                                .build())
                        .collect(Collectors.toList());

        return new Response<>(ResponseStatus.SUCCESS, "", examResults);
    }
}
