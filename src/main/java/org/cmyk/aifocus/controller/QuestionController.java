package org.cmyk.aifocus.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.constants.QuestionType;
import org.cmyk.aifocus.entity.*;
import org.cmyk.aifocus.service.QuestionService;
import org.cmyk.aifocus.utils.ConvertUtil;
import org.cmyk.aifocus.utils.Questions;
import org.cmyk.aifocus.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Tag(name = "QuestionController 题目模块")
@RestController
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @Operation(summary = "新建填空题")
    @PostMapping("/createBlankQuestion")
    public Response<String> createBlankQuestion(@RequestParam String teacherId,
                                                @RequestParam String content,
                                                @RequestParam String answer) {
        String uuid = IdWorker.get32UUID();
        QuestionBlank questionBlank = QuestionBlank.builder()
                .id(uuid)
                .teacherId(teacherId)
                .content(content)
                .answer(answer)
                .build();

        return questionService.createQuestion(questionBlank, teacherId, uuid);
    }

    @Operation(summary = "新建主观题")
    @PostMapping("/createSubjectiveQuestion")
    public Response<String> createSubjectiveQuestion(@RequestParam String teacherId,
                                                     @RequestParam String content,
                                                     @RequestParam String answer) {
        String uuid = IdWorker.get32UUID();
        QuestionSubjective questionSubjective = QuestionSubjective.builder()
                .id(uuid)
                .teacherId(teacherId)
                .content(content)
                .answer(answer)
                .build();

        return questionService.createQuestion(questionSubjective, teacherId, uuid);
    }

    @Operation(summary = "新建判断题")
    @PostMapping("/createJudgeQuestion")
    public Response<String> createJudgeQuestion(@RequestParam String teacherId,
                                                @RequestParam String content,
                                                @RequestParam Integer answer) {
        String uuid = IdWorker.get32UUID();
        QuestionJudge questionJudge = QuestionJudge.builder()
                .id(uuid)
                .teacherId(teacherId)
                .content(content)
                .answer(answer)
                .build();

        return questionService.createQuestion(questionJudge, teacherId, uuid);
    }

    @Operation(summary = "新建单选题")
    @PostMapping("/createSingleQuestion")
    public Response<String> createSingleQuestion(@RequestParam String teacherId,
                                                 @RequestParam String content,
                                                 @RequestParam String a,
                                                 @RequestParam String b,
                                                 @RequestParam String c,
                                                 @RequestParam String d,
                                                 @RequestParam Integer answer) {
        String uuid = IdWorker.get32UUID();
        QuestionSingle questionSingle = QuestionSingle.builder()
                .id(uuid)
                .teacherId(teacherId)
                .content(content)
                .a(a)
                .b(b)
                .c(c)
                .d(d)
                .answer(answer)
                .build();

        return questionService.createQuestion(questionSingle, teacherId, uuid);
    }

    @Operation(summary = "新建多选题")
    @PostMapping("/createMultipleQuestion")
    public Response<String> createMultipleQuestion(@RequestParam String teacherId,
                                                   @RequestParam String content,
                                                   @RequestParam String a,
                                                   @RequestParam String b,
                                                   @RequestParam String c,
                                                   @RequestParam String d,
                                                   @RequestParam Integer answer) {
        String uuid = IdWorker.get32UUID();
        QuestionMultiple questionMultiple = QuestionMultiple.builder()
                .id(uuid)
                .teacherId(teacherId)
                .content(content)
                .a(a)
                .b(b)
                .c(c)
                .d(d)
                .answer(answer)
                .build();

        return questionService.createQuestion(questionMultiple, teacherId, uuid);
    }

    @Operation(summary = "更新填空题")
    @PutMapping("/updateBlankQuestion/{questionId}")
    public Response<Null> updateBlankQuestion(@PathVariable String questionId,
                                              @RequestParam String content,
                                              @RequestParam String answer) {
        QuestionBlank questionBlank = QuestionBlank.builder()
                .id(questionId)
                .content(ConvertUtil.stringChange(content))
                .answer(ConvertUtil.stringChange(answer))
                .build();
        return questionService.updateQuestion(questionBlank);
    }

    @Operation(summary = "更新主观题")
    @PutMapping("/updateSubjectiveQuestion/{questionId}")
    public Response<Null> updateSubjectiveQuestion(@PathVariable String questionId,
                                                   @RequestParam String content,
                                                   @RequestParam String answer) {
        QuestionSubjective questionSubjective = QuestionSubjective.builder()
                .id(questionId)
                .content(ConvertUtil.stringChange(content))
                .answer(ConvertUtil.stringChange(answer))
                .build();
        return questionService.updateQuestion(questionSubjective);
    }

    @Operation(summary = "更新判断题")
    @PutMapping("/updateJudgeQuestion/{questionId}")
    public Response<Null> updateJudgeQuestion(@PathVariable String questionId,
                                              @RequestParam String content,
                                              @RequestParam Integer answer) {
        QuestionJudge questionJudge = QuestionJudge.builder()
                .id(questionId)
                .content(ConvertUtil.stringChange(content))
                .answer(ConvertUtil.integerChange(answer))
                .build();
        return questionService.updateQuestion(questionJudge);
    }

    @Operation(summary = "更新单选题")
    @PutMapping("/updateSingleQuestion/{questionId}")
    public Response<Null> updateSingleQuestion(@PathVariable String questionId,
                                               @RequestParam String content,
                                               @RequestParam String a,
                                               @RequestParam String b,
                                               @RequestParam String c,
                                               @RequestParam String d,
                                               @RequestParam Integer answer) {
        QuestionSingle questionSingle = QuestionSingle.builder()
                .id(ConvertUtil.stringChange(questionId))
                .content(ConvertUtil.stringChange(content))
                .a(ConvertUtil.stringChange(a))
                .b(ConvertUtil.stringChange(b))
                .c(ConvertUtil.stringChange(c))
                .d(ConvertUtil.stringChange(d))
                .answer(ConvertUtil.integerChange(answer))
                .build();
        return questionService.updateQuestion(questionSingle);
    }

    @Operation(summary = "更新多选题")
    @PutMapping("/updateMultipleQuestion/{questionId}")
    public Response<Null> updateMultipleQuestion(@PathVariable String questionId,
                                                 @RequestParam String content,
                                                 @RequestParam String a,
                                                 @RequestParam String b,
                                                 @RequestParam String c,
                                                 @RequestParam String d,
                                                 @RequestParam Integer answer) {
        QuestionMultiple questionMultiple = QuestionMultiple.builder()
                .id(ConvertUtil.stringChange(questionId))
                .content(ConvertUtil.stringChange(content))
                .a(ConvertUtil.stringChange(a))
                .b(ConvertUtil.stringChange(b))
                .c(ConvertUtil.stringChange(c))
                .d(ConvertUtil.stringChange(d))
                .answer(ConvertUtil.integerChange(answer))
                .build();
        return questionService.updateQuestion(questionMultiple);
    }


    @SneakyThrows
    @Operation(summary = "查看一个问题")
    @GetMapping("/getQuestion/{questionType}/{questionId}")
    public Response<Object> getQuestion(@PathVariable Integer questionType,
                                        @PathVariable String questionId) {

        Class type = null;
        if (QuestionType.BLANK.equals(questionType)) {
            type = QuestionBlank.class;
        } else if (QuestionType.SINGLE.equals(questionType)) {
            type = QuestionSingle.class;
        } else if (QuestionType.MULTIPLE.equals(questionType)) {
            type = QuestionMultiple.class;
        } else if (QuestionType.JUDGE.equals(questionType)) {
            type = QuestionJudge.class;
        } else if (QuestionType.SUBJECTIVE.equals(questionType)) {
            type = QuestionSubjective.class;
        }
        return questionService.getQuestion(questionId, type);
    }

    @Operation(summary = "查看老师题库")
    @GetMapping("/getQuestions/{teacherId}")
    public Response<Questions> getQuestions(@PathVariable String teacherId) {
        return questionService.getQuestions(teacherId);
    }

    @Operation(summary = "删除问题")
    @DeleteMapping("/deleteQuestion/{questionId}")
    public Response<Null> deleteQuestion(@PathVariable String questionId) {
        return questionService.deleteQuestion(questionId);
    }
}
