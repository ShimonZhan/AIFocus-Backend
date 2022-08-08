package org.cmyk.aifocus.service;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.utils.Questions;
import org.cmyk.aifocus.utils.Response;

public interface QuestionService {

    <T extends Model<T>> Response<String> createQuestion(T question, String teacherId, String uuid);

    <T extends Model<T>> Response<T> getQuestion(String questionId, Class<T> questionType);

    Response<Questions> getQuestions(String teacherId);

    <T extends Model<T>> Response<Null> updateQuestion(T question);

    Response<Null> deleteQuestion(String questionId);
}
