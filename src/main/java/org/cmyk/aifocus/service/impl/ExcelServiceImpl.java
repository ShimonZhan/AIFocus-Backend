package org.cmyk.aifocus.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.constants.CheatState;
import org.cmyk.aifocus.constants.ResponseStatus;
import org.cmyk.aifocus.dao.ExamMarkRecordMapper;
import org.cmyk.aifocus.entity.*;
import org.cmyk.aifocus.entity.excel.MarkSummary;
import org.cmyk.aifocus.service.*;
import org.cmyk.aifocus.service.excel.*;
import org.cmyk.aifocus.utils.QuestionListener;
import org.cmyk.aifocus.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @Author: ZhanX
 * @Date: 2021-03-31 11:25:44
 */
@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {
    @Resource
    private ExamService examService;

    @Resource
    private AnswerSheetService answerSheetService;

    @Resource
    private ExamMarkRecordService examMarkRecordService;

    @Resource
    private ExamMarkRecordMapper examMarkRecordMapper;

    @Resource
    private ClassService classService;

    @Override
    public CopyOnWriteArrayList<MarkSummary> exportMarkSummary(String examId) {
        if (examService.getById(examId) == null) {
            return new CopyOnWriteArrayList<>();
        }

//        Map<String, List<ExamResultQuestion>> studentQuestions =
//                answerSheetService.list(Wrappers.lambdaQuery(AnswerSheet.class)
//                        .eq(AnswerSheet::getExamId, examId))
//                        .stream()
//                        .collect(Collectors.groupingBy(AnswerSheet::getStudentId,
//                                Collectors.mapping(e -> ExamResultQuestion.builder()
//                                                .examPaperId(e.getExamPaperId())
//                                                .questionType(e.getQuestionType())
//                                                .answer(e.getAnswer())
//                                                .score(e.getScore())
//                                                .build(),
//                                        Collectors.toList())));

        Exam exam = examService.getExam(examId).getContent();

        Map<String, String> StudentsIdNameMap = classService.getStudentsFromClass(new Page<>(1, Integer.MAX_VALUE), exam.getClassId()).getContent().getRecords()
                .stream()
                .collect(Collectors.toMap(User::getId, User::getName));


        List<MarkSummary> markSummaries =
                examMarkRecordMapper.selectList(Wrappers.lambdaQuery(ExamMarkRecord.class)
                        .eq(ExamMarkRecord::getExamId, examId))
                        .stream()
                        .map(e -> MarkSummary.builder()
                                .studentName(StudentsIdNameMap.get(e.getStudentId()))
                                .cheatState(e.getState().equals(CheatState.CHEAT) ?"作弊": e.getState().equals(CheatState.SUSPICIOUS) ?"可疑":"正常")
                                .blankScore(e.getBlankScore())
                                .judgeScore(e.getJudgeScore())
                                .multipleScore(e.getMultipleScore())
                                .singleScore(e.getSingleScore())
                                .subjectiveScore(e.getSubjectiveScore())
                                .totalScore(e.getTotalScore())
                                .isSubmit(e.getIsSubmit() ? "已提交" : "未提交")
                                //.questions(studentQuestions.get(e.getStudentId()))
                                .build())
                        .collect(Collectors.toList());

        return new CopyOnWriteArrayList<>(markSummaries);
    }

    @Resource
    private QuestionBlankService questionBlankService;
    @Resource
    private QuestionJudgeService questionJudgeService;
    @Resource
    private QuestionSingleService questionSingleService;
    @Resource
    private QuestionMultipleService questionMultipleService;
    @Resource
    private QuestionSubjectiveService questionSubjectiveService;

    @Override
    public Response<Null> importQuestions(String teacherId, MultipartFile file) {
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(file.getInputStream()).build();
            // 使用功能必须不同的Listener
            ReadSheet blankSheet =
                    EasyExcel.readSheet(0).head(QuestionBlank.class).registerReadListener(new QuestionListener<>(questionBlankService,teacherId)).build();
            ReadSheet judgeSheet =
                    EasyExcel.readSheet(1).head(QuestionJudge.class).registerReadListener(new QuestionListener<>(questionJudgeService,teacherId)).build();
            ReadSheet singleSheet =
                    EasyExcel.readSheet(2).head(QuestionSingle.class).registerReadListener(new QuestionListener<>(questionSingleService,teacherId)).build();
            ReadSheet multipleSheet =
                    EasyExcel.readSheet(3).head(QuestionMultiple.class).registerReadListener(new QuestionListener<>(questionMultipleService,teacherId)).build();
            ReadSheet subjectiveSheet =
                    EasyExcel.readSheet(4).head(QuestionSubjective.class).registerReadListener(new QuestionListener<>(questionSubjectiveService,teacherId)).build();
            // 这里注意 一定要把 blankSheet judgeSheet singleSheet multipleSheet subjectiveSheet 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(blankSheet, judgeSheet, singleSheet, multipleSheet, subjectiveSheet);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(ResponseStatus.FAILURE,"失败",null);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
        return new Response<>(ResponseStatus.SUCCESS,"成功",null);
    }
}
