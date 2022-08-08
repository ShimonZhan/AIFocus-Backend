package org.cmyk.aifocus.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.amqp.message.NotificationMessage;
import org.cmyk.aifocus.amqp.producer.RabbitNotificationSender;
import org.cmyk.aifocus.constants.*;
import org.cmyk.aifocus.dao.*;
import org.cmyk.aifocus.entity.*;
import org.cmyk.aifocus.service.ClassService;
import org.cmyk.aifocus.service.ExamMarkRecordService;
import org.cmyk.aifocus.service.ExamService;
import org.cmyk.aifocus.service.UserService;
import org.cmyk.aifocus.utils.ExamPaper;
import org.cmyk.aifocus.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {
    @Resource
    private ExamMapper examMapper;

    @Resource
    private UserService userService;

    @Resource
    private ClassService classService;

    @Resource
    private CheatMapper cheatMapper;

    @Resource
    private ExamMarkRecordService examMarkRecordService;

    @Resource
    private ExamMarkSummaryMapper examMarkSummaryMapper;

    @Resource
    private ExamPaperMultipleMapper examPaperMultipleMapper;

    @Resource
    private ExamPaperSingleMapper examPaperSingleMapper;

    @Resource
    private ExamPaperBlankMapper examPaperBlankMapper;

    @Resource
    private ExamPaperJudgeMapper examPaperJudgeMapper;

    @Resource
    private ExamPaperSubjectiveMapper examPaperSubjectiveMapper;

    @Resource
    private RabbitNotificationSender rabbitNotificationSender;

    @Resource
    private UserClassMapper userClassMapper;

    @Override
    public Response<String> createExam(Exam exam) {
        Clazz clazz = classService.getById(exam.getClassId());
        if (clazz == null) {
            return new Response<>(ResponseStatus.FAILURE, "班级不存在", null);
        } else if (userClassMapper.selectCount(Wrappers.lambdaQuery(UserClass.class).eq(UserClass::getClassId, clazz.getId()).eq(UserClass::getUserType, UserRole.STUDENT)) == 0) {
            return new Response<>(ResponseStatus.FAILURE, "班级中没有人", null);
        }
        String uuid = IdWorker.get32UUID();
        exam.setId(uuid);
        if (exam.insert()) {
            List<User> students = classService.getStudentsFromClass(new Page<>(1, Long.MAX_VALUE), exam.getClassId()).getContent().getRecords();
            List<ExamMarkRecord> recordList = students.stream()
                    .map(student ->
                            ExamMarkRecord.builder()
                                    .id(null)
                                    .examId(exam.getId())
                                    .studentId(student.getId())
                                    .blankScore(0)
                                    .judgeScore(0)
                                    .singleScore(0)
                                    .multipleScore(0)
                                    .subjectiveScore(0)
                                    .totalScore(0)
                                    .isMarked(false)
                                    .isSubmit(false)
                                    .state(CheatState.NORMAL)
                                    .build())
                    .collect(Collectors.toList());

            examMarkRecordService.saveBatch(recordList);

            ExamMarkSummary.builder()
                    .id(null)
                    .examId(exam.getId())
                    .markStudentNumber(0)
                    .totalStudentNumber(students.size())
                    .build().insert();

            return new Response<>(ResponseStatus.SUCCESS, "", uuid);
        }
        return new Response<>(ResponseStatus.FAILURE, "未知错误", null);
    }

    @Override
    public Response<Null> updateExam(Exam exam) {
        Exam origin = examMapper.selectById(exam.getId());
        if (origin == null) {
            return new Response<>(ResponseStatus.FAILURE, "考试不存在", null);
        }
        origin.setName(exam.getName());
        origin.setFullScore(exam.getFullScore());
        origin.setStartTime(exam.getStartTime());
        origin.setFinishTime(exam.getFinishTime());
        origin.updateById();
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    public Response<Null> deleteExam(String examId) {
        if (examMapper.selectById(examId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "考试不存在", null);
        }
        examPaperMultipleMapper.delete(Wrappers.lambdaQuery(ExamPaperMultiple.class)
                .eq(ExamPaperMultiple::getExamId, examId));
        examPaperSingleMapper.delete(Wrappers.lambdaQuery(ExamPaperSingle.class)
                .eq(ExamPaperSingle::getExamId, examId));
        examPaperBlankMapper.delete(Wrappers.lambdaQuery(ExamPaperBlank.class)
                .eq(ExamPaperBlank::getExamId, examId));
        examPaperJudgeMapper.delete(Wrappers.lambdaQuery(ExamPaperJudge.class)
                .eq(ExamPaperJudge::getExamId, examId));
        examPaperSubjectiveMapper.delete(Wrappers.lambdaQuery(ExamPaperSubjective.class)
                .eq(ExamPaperSubjective::getExamId, examId));
        examMarkRecordService.remove(Wrappers.lambdaQuery(ExamMarkRecord.class)
                .eq(ExamMarkRecord::getExamId, examId));
        examMarkSummaryMapper.delete(Wrappers.lambdaQuery(ExamMarkSummary.class)
                .eq(ExamMarkSummary::getExamId, examId));
        removeById(examId);
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    public Response<Page<Exam>> getStudentExams(Page<Exam> page, String studentId) {
        if (userService.getById(studentId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该同学", null);
        }
        return new Response<>(ResponseStatus.SUCCESS, "", examMapper.selectStudentExams(page, studentId));
    }

    @Override
    public Response<Exam> getExam(String examId) {
        Exam exam = examMapper.selectById(examId);
        return exam != null ?
                new Response<>(ResponseStatus.SUCCESS, "", exam) :
                new Response<>(ResponseStatus.FAILURE, "考试不存在", null);
    }

    @Override
    public <T extends Model<T>> Response<String> addQuestion2Exam(String examId, T question, Integer score) {
        Exam exam = examMapper.selectById(examId);
        if (exam == null) {
            return new Response<>(ResponseStatus.FAILURE, "考试不存在", null);
        } else if (!ExamStatus.DRAFT.equals(exam.getStatus())) {
            return new Response<>(ResponseStatus.FAILURE, "考试不在草稿状态", null);
        }

        String uuid = IdWorker.get32UUID();
        if (question instanceof QuestionJudge) {
            QuestionJudge questionJudge = (QuestionJudge) question;
            if (examPaperJudgeMapper.selectOne(Wrappers.lambdaQuery(ExamPaperJudge.class)
                    .eq(ExamPaperJudge::getExamId, examId)
                    .eq(ExamPaperJudge::getQuestionId, questionJudge.getId())) != null) {
                return new Response<>(ResponseStatus.FAILURE, "该问题已存在于该试卷", null);
            }
            ExamPaperJudge.builder()
                    .id(uuid)
                    .examId(examId)
                    .questionId(questionJudge.getId())
                    .content(questionJudge.getContent())
                    .answer(questionJudge.getAnswer())
                    .score(score)
                    .build().insert();
            exam.setFullScore(exam.getFullScore() + score);
            exam.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", uuid);
        } else if (question instanceof QuestionBlank) {
            QuestionBlank questionBlank = (QuestionBlank) question;
            if (examPaperBlankMapper.selectOne(Wrappers.lambdaQuery(ExamPaperBlank.class)
                    .eq(ExamPaperBlank::getExamId, examId)
                    .eq(ExamPaperBlank::getQuestionId, questionBlank.getId())) != null) {
                return new Response<>(ResponseStatus.FAILURE, "该问题已存在于该试卷", null);
            }
            ExamPaperBlank.builder()
                    .id(uuid)
                    .examId(examId)
                    .questionId(questionBlank.getId())
                    .content(questionBlank.getContent())
                    .answer(questionBlank.getAnswer())
                    .score(score)
                    .build().insert();
            exam.setFullScore(exam.getFullScore() + score);
            exam.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", uuid);
        } else if (question instanceof QuestionSingle) {
            QuestionSingle questionSingle = (QuestionSingle) question;
            if (examPaperSingleMapper.selectOne(Wrappers.lambdaQuery(ExamPaperSingle.class)
                    .eq(ExamPaperSingle::getExamId, examId)
                    .eq(ExamPaperSingle::getQuestionId, questionSingle.getId())) != null) {
                return new Response<>(ResponseStatus.FAILURE, "该问题已存在于该试卷", null);
            }
            ExamPaperSingle.builder()
                    .id(uuid)
                    .examId(examId)
                    .questionId(questionSingle.getId())
                    .content(questionSingle.getContent())
                    .a(questionSingle.getA())
                    .b(questionSingle.getB())
                    .c(questionSingle.getC())
                    .d(questionSingle.getD())
                    .answer(questionSingle.getAnswer())
                    .score(score)
                    .build().insert();
            exam.setFullScore(exam.getFullScore() + score);
            exam.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", uuid);
        } else if (question instanceof QuestionMultiple) {
            QuestionMultiple questionMultiple = (QuestionMultiple) question;
            if (examPaperMultipleMapper.selectOne(Wrappers.lambdaQuery(ExamPaperMultiple.class)
                    .eq(ExamPaperMultiple::getExamId, examId)
                    .eq(ExamPaperMultiple::getQuestionId, questionMultiple.getId())) != null) {
                return new Response<>(ResponseStatus.FAILURE, "该问题已存在于该试卷", null);
            }
            ExamPaperMultiple.builder()
                    .id(uuid)
                    .examId(examId)
                    .questionId(questionMultiple.getId())
                    .content(questionMultiple.getContent())
                    .a(questionMultiple.getA())
                    .b(questionMultiple.getB())
                    .c(questionMultiple.getC())
                    .d(questionMultiple.getD())
                    .answer(questionMultiple.getAnswer())
                    .score(score)
                    .build().insert();
            exam.setFullScore(exam.getFullScore() + score);
            exam.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", uuid);
        } else if (question instanceof QuestionSubjective) {
            QuestionSubjective questionSubjective = (QuestionSubjective) question;
            if (examPaperSubjectiveMapper.selectOne(Wrappers.lambdaQuery(ExamPaperSubjective.class)
                    .eq(ExamPaperSubjective::getExamId, examId)
                    .eq(ExamPaperSubjective::getQuestionId, questionSubjective.getId())) != null) {
                return new Response<>(ResponseStatus.FAILURE, "该问题已存在于该试卷", null);
            }
            ExamPaperSubjective.builder()
                    .id(uuid)
                    .examId(examId)
                    .questionId(questionSubjective.getId())
                    .content(questionSubjective.getContent())
                    .answer(questionSubjective.getAnswer())
                    .score(score)
                    .build().insert();
            exam.setFullScore(exam.getFullScore() + score);
            exam.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", uuid);
        } else {
            return new Response<>(ResponseStatus.FAILURE, "问题类型错误", null);
        }
    }

    @Override
    public Response<Null> updateQuestionScore(String examPaperId, Integer score) {
        ExamPaperSingle single = examPaperSingleMapper.selectById(examPaperId);
        ExamPaperMultiple multiple = examPaperMultipleMapper.selectById(examPaperId);
        ExamPaperSubjective subjective = examPaperSubjectiveMapper.selectById(examPaperId);
        ExamPaperBlank blank = examPaperBlankMapper.selectById(examPaperId);
        ExamPaperJudge judge = examPaperJudgeMapper.selectById(examPaperId);

        if (single != null) {
            Exam exam = getById(single.getExamId());
            exam.setFullScore(exam.getFullScore() - single.getScore() + score);
            exam.updateById();
            single.setScore(score);
            single.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        } else if (multiple != null) {
            Exam exam = getById(multiple.getExamId());
            exam.setFullScore(exam.getFullScore() - multiple.getScore() + score);
            exam.updateById();
            multiple.setScore(score);
            multiple.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        } else if (subjective != null) {
            Exam exam = getById(subjective.getExamId());
            exam.setFullScore(exam.getFullScore() - subjective.getScore() + score);
            exam.updateById();
            subjective.setScore(score);
            subjective.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        } else if (blank != null) {
            Exam exam = getById(blank.getExamId());
            exam.setFullScore(exam.getFullScore() - blank.getScore() + score);
            exam.updateById();
            blank.setScore(score);
            blank.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        } else if (judge != null) {
            Exam exam = getById(judge.getExamId());
            exam.setFullScore(exam.getFullScore() - judge.getScore() + score);
            exam.updateById();
            judge.setScore(score);
            judge.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        } else {
            return new Response<>(ResponseStatus.FAILURE, "问题不存在", null);
        }

    }

    @Override
    public Response<Null> deleteQuestionFromExam(String examPaperId) {
        ExamPaperSingle single = examPaperSingleMapper.selectById(examPaperId);
        ExamPaperMultiple multiple = examPaperMultipleMapper.selectById(examPaperId);
        ExamPaperSubjective subjective = examPaperSubjectiveMapper.selectById(examPaperId);
        ExamPaperBlank blank = examPaperBlankMapper.selectById(examPaperId);
        ExamPaperJudge judge = examPaperJudgeMapper.selectById(examPaperId);

        if (single != null) {
            Exam exam = getById(single.getExamId());
            exam.setFullScore(exam.getFullScore() - single.getScore());
            exam.updateById();
            single.deleteById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        } else if (multiple != null) {
            Exam exam = getById(multiple.getExamId());
            exam.setFullScore(exam.getFullScore() - multiple.getScore());
            exam.updateById();
            multiple.deleteById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        } else if (subjective != null) {
            Exam exam = getById(subjective.getExamId());
            exam.setFullScore(exam.getFullScore() - subjective.getScore());
            exam.updateById();
            subjective.deleteById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        } else if (blank != null) {
            Exam exam = getById(blank.getExamId());
            exam.setFullScore(exam.getFullScore() - blank.getScore());
            exam.updateById();
            blank.deleteById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        } else if (judge != null) {
            Exam exam = getById(judge.getExamId());
            exam.setFullScore(exam.getFullScore() - judge.getScore());
            exam.updateById();
            judge.deleteById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        } else {
            return new Response<>(ResponseStatus.FAILURE, "问题不存在", null);
        }
    }

    @Override
    public Response<ExamPaper> getExamPaper(String examId, Boolean hasAnswer) {
        if (examMapper.selectById(examId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "考试不存在", null);
        }
        List<ExamPaperJudge> judges = examPaperJudgeMapper.selectList(Wrappers.lambdaQuery(ExamPaperJudge.class)
                .eq(ExamPaperJudge::getExamId, examId)
                .select(i -> hasAnswer || !i.getProperty().equals("answer")));
        List<ExamPaperBlank> blanks = examPaperBlankMapper.selectList(Wrappers.lambdaQuery(ExamPaperBlank.class)
                .eq(ExamPaperBlank::getExamId, examId)
                .select(i -> hasAnswer || !i.getProperty().equals("answer")));
        List<ExamPaperSingle> singles = examPaperSingleMapper.selectList(Wrappers.lambdaQuery(ExamPaperSingle.class)
                .eq(ExamPaperSingle::getExamId, examId)
                .select(i -> hasAnswer || !i.getProperty().equals("answer")));
        List<ExamPaperMultiple> multiples = examPaperMultipleMapper.selectList(Wrappers.lambdaQuery(ExamPaperMultiple.class)
                .eq(ExamPaperMultiple::getExamId, examId)
                .select(i -> hasAnswer || !i.getProperty().equals("answer")));
        List<ExamPaperSubjective> subjectives = examPaperSubjectiveMapper.selectList(Wrappers.lambdaQuery(ExamPaperSubjective.class)
                .eq(ExamPaperSubjective::getExamId, examId)
                .select(i -> hasAnswer || !i.getProperty().equals("answer")));
        if (!hasAnswer) {
            Collections.shuffle(judges);
            Collections.shuffle(blanks);
            Collections.shuffle(singles);
            Collections.shuffle(multiples);
            Collections.shuffle(subjectives);
        }
        ExamPaper examPaper = ExamPaper.builder()
                .blanks(blanks)
                .multiples(multiples)
                .judges(judges)
                .singles(singles)
                .subjectives(subjectives)
                .build();
        return new Response<>(ResponseStatus.SUCCESS, "", examPaper);
    }

    @Override
    public Response<Page<Exam>> getTeacherExams(Page<Exam> page, String teacherId) {
        if (userService.getById(teacherId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该老师", null);
        }
        Page<Exam> examPage = page(page, Wrappers.lambdaQuery(Exam.class).eq(Exam::getTeacherId, teacherId).orderByDesc(Exam::getCreateTime));
        return new Response<>(ResponseStatus.SUCCESS, "", examPage);
    }

    @Override
    public Response<Null> changeExamStatus(String examId, Integer status) {
        Exam exam = getById(examId);
        if (exam == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该考试", null);
        }
        if (exam.getStatus().equals(ExamStatus.EXAM) && status.equals(ExamStatus.DRAFT)) {
            return new Response<>(ResponseStatus.FAILURE, "考试已经开始，不能更改会草稿状态", null);
        }
        if (exam.getStatus().equals(ExamStatus.DRAFT) && status.equals(ExamStatus.RELEASE)) {
            if (exam.getStartTime().isBefore(LocalDateTime.now())) {
                return new Response<>(ResponseStatus.FAILURE, "考试开始时间在当前时间之前，不能更改草稿状态", null);
            }
        }
        exam.setStatus(status);
        exam.updateById();
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    public Response<Null> recordCheat(String studentId, String examId, String monitorPhoto, LocalDateTime timeStamp) {
        if (userService.getById(studentId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该同学", null);
        }
        Exam exam = examMapper.selectById(examId);
        if (exam == null) {
            return new Response<>(ResponseStatus.FAILURE, "考试不存在", null);
        }
        int count = cheatMapper.selectCount(Wrappers.lambdaQuery(Cheat.class)
                .eq(Cheat::getStudentId, studentId).eq(Cheat::getExamId, examId));
        Cheat.builder()
                .id(null)
                .examId(examId)
                .studentId(studentId)
                .monitorPhoto(monitorPhoto)
                .timeStamp(timeStamp)
                .build()
                .insert();
        ExamMarkRecord examMarkRecord = examMarkRecordService.getOne(Wrappers.lambdaQuery(ExamMarkRecord.class)
                .eq(ExamMarkRecord::getExamId, examId).eq(ExamMarkRecord::getStudentId, studentId));
        if (count == 0 || count == 1) {
            examMarkRecord.setState(CheatState.SUSPICIOUS);
            rabbitNotificationSender.send(NotificationMessage.builder()
                    .messageId(IdWorker.get32UUID())
                    .fromUserId(studentId)
                    .toUserId(exam.getTeacherId())
                    .examId(exam.getId())
                    .notificationType(NotificationType.STUDENT_SUSPICIOUS)
                    .content("")
                    .build());
        } else if (count >= 2) {
            examMarkRecord.setState(CheatState.CHEAT);
            rabbitNotificationSender.send(NotificationMessage.builder()
                    .messageId(IdWorker.get32UUID())
                    .fromUserId(studentId)
                    .toUserId(exam.getTeacherId())
                    .examId(exam.getId())
                    .notificationType(NotificationType.STUDENT_CHEAT)
                    .content("")
                    .build());
        }
        examMarkRecord.updateById();
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    public Response<Null> changeStudentExamState(String studentId, String examId, Integer state) {
        if (userService.getById(studentId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该同学", null);
        } else if (examMapper.selectById(examId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "考试不存在", null);
        }
        ExamMarkRecord examMarkRecord = examMarkRecordService.getOne(Wrappers.lambdaQuery(ExamMarkRecord.class)
                .eq(ExamMarkRecord::getExamId, examId).eq(ExamMarkRecord::getStudentId, studentId));
        examMarkRecord.setState(state);
        examMarkRecord.updateById();
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    public Response<List<Cheat>> getStudentExamMonitorPhotos(String studentId, String examId) {
        if (userService.getById(studentId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该同学", null);
        } else if (examMapper.selectById(examId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "考试不存在", null);
        }
        List<Cheat> cheats = cheatMapper.selectList(Wrappers.lambdaQuery(Cheat.class)
                .eq(Cheat::getStudentId, studentId).eq(Cheat::getExamId, examId));
        return new Response<>(ResponseStatus.SUCCESS, "", cheats);
    }
}
