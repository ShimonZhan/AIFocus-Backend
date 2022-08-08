package org.cmyk.aifocus.service;

import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.entity.AnswerSheet;
import org.cmyk.aifocus.utils.ExamResult;
import org.cmyk.aifocus.utils.Response;

import java.util.List;
import java.util.concurrent.Future;

public interface MarkService {

    Response<Null> submitPaper(List<AnswerSheet> answerSheets);

    Future<Boolean> markPaper(String examId, String studentId);

    Response<ExamResult> getStudentExamResult(String examId, String studentId);

    Response<Null> deletePaper(String examId, String studentId);

    Response<List<AnswerSheet>> getManualMarkAnswerSheet(String examId, Boolean isAll);

    Response<Null> manualMark(String answerSheetId, Integer score);

    Response<List<ExamResult>> getExamResult(String examId);
}
