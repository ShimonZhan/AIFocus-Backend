package org.cmyk.aifocus.service;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.entity.Cheat;
import org.cmyk.aifocus.entity.Exam;
import org.cmyk.aifocus.utils.ExamPaper;
import org.cmyk.aifocus.utils.Response;

import java.time.LocalDateTime;
import java.util.List;

public interface ExamService extends IService<Exam> {

    Response<String> createExam(Exam exam);

    Response<Null> updateExam(Exam exam);

    Response<Null> deleteExam(String examId);

    Response<Page<Exam>> getStudentExams(Page<Exam> page, String studentId);

    Response<Exam> getExam(String examId);

    <T extends Model<T>> Response<String> addQuestion2Exam(String examId, T question, Integer score);

    Response<Null> updateQuestionScore(String examPaperId, Integer score);

    Response<Null> deleteQuestionFromExam(String examPaperId);

    Response<ExamPaper> getExamPaper(String examId, Boolean hasAnswer);

    Response<Page<Exam>> getTeacherExams(Page<Exam> page, String teacherId);

    Response<Null> changeExamStatus(String examId, Integer status);

    Response<Null> recordCheat(String studentId, String examId, String monitorPhoto, LocalDateTime timeStamp);

    Response<Null> changeStudentExamState(String studentId, String examId, Integer state);

    Response<List<Cheat>> getStudentExamMonitorPhotos(String studentId, String examId);
}
