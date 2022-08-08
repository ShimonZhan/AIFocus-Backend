package org.cmyk.aifocus.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zxm
 * @date 2020/11/17 20:41
 */
@Slf4j
public class ExcelUtils {
    public static void writeExcel(String fileName , Class head,
                                  HttpServletResponse response, CopyOnWriteArrayList list) {
        try {
            ServletOutputStream outputStream = getOutputStream(fileName,response);
            ExcelWriterBuilder writeBook = EasyExcel.write(outputStream, head);
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            //设置背景颜色
            headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            //设置头字体
            WriteFont headWriteFont = new WriteFont();
            headWriteFont.setFontHeightInPoints((short)13);
            headWriteFont.setBold(true);
            headWriteCellStyle.setWriteFont(headWriteFont);
            //设置头居中
            headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            //内容策略
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            //设置水平居中
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

            HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

            ExcelWriterSheetBuilder sheet = writeBook.sheet(fileName);
            sheet.registerWriteHandler(horizontalCellStyleStrategy)
                .doWrite(list);
        } catch (Exception e) {
            //log.error("导出excel表格失败:", e);
        }
    }

    /**
     * 导出文件时为Writer生成OutputStream.
     *
     * @param fileName 文件名
     * @param response response
     * @return ""
     */
    public static ServletOutputStream getOutputStream(String fileName,
                                                       HttpServletResponse response) throws Exception {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //设置响应的类型
            response.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
            //设置响应的编码格式
            response.setCharacterEncoding("utf8");
            //设置响应头
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            return response.getOutputStream();
        } catch (IOException e) {
            log.error("导出excel表格失败:", e);
            throw new Exception("导出excel表格失败!", e);
        }
    }
}
