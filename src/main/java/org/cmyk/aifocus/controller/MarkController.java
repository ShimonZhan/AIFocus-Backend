package org.cmyk.aifocus.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.constants.ResponseStatus;
import org.cmyk.aifocus.entity.AnswerSheet;
import org.cmyk.aifocus.entity.User;
import org.cmyk.aifocus.service.MarkService;
import org.cmyk.aifocus.service.UserService;
import org.cmyk.aifocus.utils.ExamResult;
import org.cmyk.aifocus.utils.Response;
import org.cmyk.aifocus.utils.SubmitPaper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "MarkController 批改模块")
@RestController
public class MarkController {
    @Resource
    private MarkService markService;

    @Resource
    private UserService userService;

    @Operation(summary = "学生提交试卷", description = "content-type: application/json")
    @PostMapping("/submitPaper/{examId}/{studentId}")
    public Response<Null> submitPaper(@PathVariable String examId,
                                      @PathVariable String studentId,
                                      @RequestBody List<SubmitPaper> submitPaper) {
        User student = userService.getById(studentId);
        if (student == null) {
            return new Response<>(ResponseStatus.FAILURE, "该学生不存在", null);
        }
        List<AnswerSheet> answerSheets = submitPaper.stream()
                .map(e -> AnswerSheet.builder()
                        .id(IdWorker.get32UUID())
                        .examId(examId)
                        .studentId(studentId)
                        .studentName(student.getName())
                        .examPaperId(e.getExamPaperId())
                        .questionType(e.getQuestionType())
                        .answer(e.getAnswer())
                        .score(0)
                        .isMarked(false)
                        .build())
                .collect(Collectors.toList());
        return markService.submitPaper(answerSheets);
    }

    @Operation(summary = "学生查看考试结果")
    @GetMapping("/getStudentExamResult/{examId}/{studentId}")
    public Response<ExamResult> getStudentExamResult(@PathVariable String examId,
                                                     @PathVariable String studentId) {
        return markService.getStudentExamResult(examId, studentId);
    }

    @Operation(summary = "老师查看考试结果")
    @GetMapping("/getExamResult/{examId}")
    public Response<List<ExamResult>> getExamResult(@PathVariable String examId) {
        return markService.getExamResult(examId);
    }

    @Operation(summary = "删除学生提交的试卷")
    @DeleteMapping("/deletePaper/{examId}/{studentId}")
    public Response<Null> deletePaper(@PathVariable String examId,
                                      @PathVariable String studentId) {
        return markService.deletePaper(examId, studentId);
    }

    @Operation(summary = "得到考试需要手动批改的答题卡")
    @GetMapping("/getManualMarkAnswerSheet/{examId}/{isAll}")
    public Response<List<AnswerSheet>> getManualMarkAnswerSheet(@PathVariable String examId, @PathVariable Boolean isAll) {
        return markService.getManualMarkAnswerSheet(examId, isAll);
    }

    @Operation(summary = "手动批改")
    @PostMapping("/manualMark/{answerSheetId}")
    public Response<Null> manualMark(@PathVariable String answerSheetId, @RequestParam Integer score) {
        return markService.manualMark(answerSheetId, score);
    }
}
