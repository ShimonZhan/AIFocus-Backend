package org.cmyk.aifocus.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.apache.poi.util.IOUtils;
import org.cmyk.aifocus.entity.excel.MarkSummary;
import org.cmyk.aifocus.service.ExamService;
import org.cmyk.aifocus.service.ExcelService;
import org.cmyk.aifocus.utils.ExcelUtils;
import org.cmyk.aifocus.utils.Response;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @Author: ZhanX
 * @Date: 2021-03-30 20:43:11
 */
@RestController
@RequestMapping("excel")
@Slf4j
@Tag(name = "Excel模块")
public class ExcelController {

    @Resource
    private ExcelService excelService;

    @Resource
    private ExamService examService;

    @GetMapping("/exportMarkSummary/{examId}")
    public void downloadExcel(HttpServletResponse response, @PathVariable String examId) {
        String examName = examService.getExam(examId).getContent().getName();

        try {
            ExcelUtils.writeExcel(examName + "的成绩汇总", MarkSummary.class, response, excelService.exportMarkSummary(examId));
        } catch (Exception e) {
            log.error("导出excel表格失败:", e);
        }
    }

    /**
     * 下载Excel模板
     */
    @GetMapping("downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String fileName = URLEncoder.encode("导入题目模版", "UTF-8");

            org.springframework.core.io.Resource resource = new DefaultResourceLoader().getResource("classpath:导入题目模版.xlsx");

            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");

            inputStream = resource.getInputStream();
            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            log.error("下载模版失败:", e);
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                    servletOutputStream = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
            } catch (Exception e) {
                log.error("下载模版失败:", e);
            }
        }
    }

    @PostMapping(value = "importQuestions/{teacherId}")
    public Response<Null> importQuestions(@PathVariable String teacherId, @RequestPart MultipartFile file) {
        return excelService.importQuestions(teacherId, file);
    }
}
