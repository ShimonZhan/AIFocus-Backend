package org.cmyk.aifocus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.constants.ExamStatus;
import org.cmyk.aifocus.constants.ResponseStatus;
import org.cmyk.aifocus.entity.*;
import org.cmyk.aifocus.service.ExamService;
import org.cmyk.aifocus.service.QuestionService;
import org.cmyk.aifocus.utils.ConvertUtil;
import org.cmyk.aifocus.utils.ExamPaper;
import org.cmyk.aifocus.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Tag(name = "ExamController 考试模块")
@RestController
public class ExamController {

    @Resource
    private ExamService examService;

    @Resource
    private QuestionService questionService;

    @Operation(summary = "创建考试")
    @PostMapping("/createExam")
    public Response<String> createExam(@RequestParam String name,
                                       @RequestParam String classId,
                                       @RequestParam String teacherId,
                                       @RequestParam String courseName,
                                       @RequestParam String startTime,
                                       @RequestParam String finishTime) {
        Exam exam = Exam.builder()
                .id(null)
                .name(name)
                .classId(classId)
                .teacherId(teacherId)
                .courseName(courseName)
                .fullScore(0)
                .startTime(ConvertUtil.localDateTimeChange(startTime))
                .finishTime(ConvertUtil.localDateTimeChange(finishTime))
                .isAutoMarkMessageSend(false)
                .status(ExamStatus.DRAFT)
                .createTime(LocalDateTime.now())
                .build();

        return examService.createExam(exam);
    }

    @Operation(summary = "更改考试状态")
    @PutMapping("/changeExamStatus/{examId}")
    public Response<Null> changeExamStatus(@PathVariable String examId,
                                           @RequestParam Integer status) {
        return examService.changeExamStatus(examId, status);
    }

    @Operation(summary = "查看学生参与的考试")
    @GetMapping("/getStudentExams/{studentId}/{current}/{pageSize}")
    public Response<Page<Exam>> getStudentExams(@PathVariable String studentId,
                                                @PathVariable Long current,
                                                @PathVariable Long pageSize) {
        return examService.getStudentExams(new Page<>(current, pageSize), studentId);
    }

    @Operation(summary = "查看老师参与的考试")
    @GetMapping("/getTeacherExams/{teacherId}/{current}/{pageSize}")
    public Response<Page<Exam>> getTeacherExams(@PathVariable String teacherId,
                                                @PathVariable Long current,
                                                @PathVariable Long pageSize) {
        return examService.getTeacherExams(new Page<>(current, pageSize), teacherId);
    }

    @Operation(summary = "查看考试")
    @GetMapping("/getExam/{examId}")
    public Response<Exam> getExam(@PathVariable String examId) {
        return examService.getExam(examId);
    }

    @Operation(summary = "更新考试")
    @PutMapping("/updateExam/{examId}")
    public Response<Null> updateExam(@PathVariable String examId,
                                     @RequestParam String name,
                                     @RequestParam String startTime,
                                     @RequestParam String finishTime) {
        Exam exam = Exam.builder()
                .id(examId)
                .name(ConvertUtil.stringChange(name))
                .startTime(ConvertUtil.localDateTimeChange(startTime))
                .finishTime(ConvertUtil.localDateTimeChange(finishTime))
                .build();
        return examService.updateExam(exam);
    }

    @Operation(summary = "添加题目到试卷", description = "返回值为试卷中题目id")
    @PostMapping("/addQuestion2Exam")
    Response<String> addQuestion2Exam(@RequestParam String examId,
                                      @RequestParam String questionId,
                                      @RequestParam Integer score) {
        QuestionJudge questionJudge = questionService.getQuestion(questionId, QuestionJudge.class).getContent();
        QuestionSingle questionSingle = questionService.getQuestion(questionId, QuestionSingle.class).getContent();
        QuestionMultiple questionMultiple = questionService.getQuestion(questionId, QuestionMultiple.class).getContent();
        QuestionBlank questionBlank = questionService.getQuestion(questionId, QuestionBlank.class).getContent();
        QuestionSubjective questionSubjective = questionService.getQuestion(questionId, QuestionSubjective.class).getContent();

        if (questionJudge != null) {
            return examService.addQuestion2Exam(examId, questionJudge, score);
        } else if (questionSingle != null) {
            return examService.addQuestion2Exam(examId, questionSingle, score);
        } else if (questionMultiple != null) {
            return examService.addQuestion2Exam(examId, questionMultiple, score);
        } else if (questionBlank != null) {
            return examService.addQuestion2Exam(examId, questionBlank, score);
        } else if (questionSubjective != null) {
            return examService.addQuestion2Exam(examId, questionSubjective, score);
        } else {
            return new Response<>(ResponseStatus.FAILURE, "题目不存在于题库", null);
        }
    }

    @Operation(summary = "更新试卷题目分数")
    @PutMapping("/updateQuestionScore/{examPaperId}")
    Response<Null> updateQuestionScore(@PathVariable String examPaperId,
                                       @RequestParam Integer score) {
        return examService.updateQuestionScore(examPaperId, score);
    }

    @Operation(summary = "删除试卷中的题目")
    @DeleteMapping("/deleteQuestionFromExam/{examPaperId}")
    Response<Null> deleteQuestionFromExam(@PathVariable String examPaperId) {
        return examService.deleteQuestionFromExam(examPaperId);
    }

    @Operation(summary = "获取试卷")
    @GetMapping("/getExamPaper/{examId}/{hasAnswer}")
    Response<ExamPaper> getExamPaper(@PathVariable String examId,
                                     @PathVariable Boolean hasAnswer) {
        return examService.getExamPaper(examId, hasAnswer);
    }

    @Operation(summary = "删除考试")
    @DeleteMapping("/deleteExam/{examId}")
    public Response<Null> deleteExam(@PathVariable String examId) {
        return examService.deleteExam(examId);
    }
}
