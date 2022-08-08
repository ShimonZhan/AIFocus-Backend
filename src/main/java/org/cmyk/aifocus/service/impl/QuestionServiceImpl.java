package org.cmyk.aifocus.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.constants.ResponseStatus;
import org.cmyk.aifocus.dao.*;
import org.cmyk.aifocus.entity.*;
import org.cmyk.aifocus.service.QuestionService;
import org.cmyk.aifocus.service.UserService;
import org.cmyk.aifocus.utils.Questions;
import org.cmyk.aifocus.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private UserService userService;

    @Resource
    private QuestionBlankMapper questionBlankMapper;

    @Resource
    private QuestionSingleMapper questionSingleMapper;

    @Resource
    private QuestionJudgeMapper questionJudgeMapper;

    @Resource
    private QuestionMultipleMapper questionMultipleMapper;

    @Resource
    private QuestionSubjectiveMapper questionSubjectiveMapper;


    @Override
    public <T extends Model<T>> Response<String> createQuestion(T question, String teacherId, String uuid) {
        // 老师权限校验
        if (userService.getById(teacherId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "老师不存在", null);
        }
        question.insert();
        return new Response<>(ResponseStatus.SUCCESS, "", uuid);
    }

    @Override
    public <T extends Model<T>> Response<T> getQuestion(String questionId, Class<T> questionType) {
        T question = null;
        if (questionType.equals(QuestionJudge.class)) {
            question = (T) questionJudgeMapper.selectById(questionId);
        } else if (questionType.equals(QuestionBlank.class)) {
            question = (T) questionBlankMapper.selectById(questionId);
        } else if (questionType.equals(QuestionSingle.class)) {
            question = (T) questionSingleMapper.selectById(questionId);
        } else if (questionType.equals(QuestionSubjective.class)) {
            question = (T) questionSubjectiveMapper.selectById(questionId);
        } else if (questionType.equals(QuestionMultiple.class)) {
            question = (T) questionMultipleMapper.selectById(questionId);
        }
        return question == null ?
                new Response<>(ResponseStatus.FAILURE, "问题不存在", null) :
                new Response<>(ResponseStatus.SUCCESS, "", question);
    }

    @Override
    public Response<Questions> getQuestions(String teacherId) {
        if (userService.getById(teacherId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "老师不存在", null);
        }

        Questions questions = Questions.builder()
                .questionBlanks(questionBlankMapper.selectList(Wrappers.lambdaQuery(QuestionBlank.class)
                        .eq(QuestionBlank::getTeacherId, teacherId)))
                .questionJudges(questionJudgeMapper.selectList(Wrappers.lambdaQuery(QuestionJudge.class)
                        .eq(QuestionJudge::getTeacherId, teacherId)))
                .questionMultiples(questionMultipleMapper.selectList(Wrappers.lambdaQuery(QuestionMultiple.class)
                        .eq(QuestionMultiple::getTeacherId, teacherId)))
                .questionSingles(questionSingleMapper.selectList(Wrappers.lambdaQuery(QuestionSingle.class)
                        .eq(QuestionSingle::getTeacherId, teacherId)))
                .questionSubjectives(questionSubjectiveMapper.selectList(Wrappers.lambdaQuery(QuestionSubjective.class)
                        .eq(QuestionSubjective::getTeacherId, teacherId)))
                .build();

        return new Response<>(ResponseStatus.SUCCESS, "", questions);
    }

    @Override
    public <T extends Model<T>> Response<Null> updateQuestion(T question) {
        return question.updateById() ?
                new Response<>(ResponseStatus.SUCCESS, "", null) :
                new Response<>(ResponseStatus.FAILURE, "问题不存在", null);
    }

    @Override
    public Response<Null> deleteQuestion(String questionId) {
        return questionMultipleMapper.deleteById(questionId) +
                questionSubjectiveMapper.deleteById(questionId) +
                questionSingleMapper.deleteById(questionId) +
                questionBlankMapper.deleteById(questionId) +
                questionJudgeMapper.deleteById(questionId) > 0 ?
                new Response<>(ResponseStatus.SUCCESS, "", null) :
                new Response<>(ResponseStatus.FAILURE, "问题不存在", null);
    }
}
