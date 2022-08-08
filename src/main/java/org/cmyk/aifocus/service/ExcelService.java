package org.cmyk.aifocus.service;

import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.entity.excel.MarkSummary;
import org.cmyk.aifocus.utils.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: ZhanX
 * @Date: 2021-03-31 11:16:39
 */
public interface ExcelService {
    CopyOnWriteArrayList<MarkSummary> exportMarkSummary(String examId);

    Response<Null> importQuestions(String teacherId, MultipartFile file);
}
