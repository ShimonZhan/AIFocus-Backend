package org.cmyk.aifocus;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.cmyk.aifocus.entity.*;
import org.cmyk.aifocus.entity.excel.MarkSummary;
import org.cmyk.aifocus.service.ExcelService;
import org.cmyk.aifocus.service.excel.*;
import org.cmyk.aifocus.utils.QuestionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZhanX
 * @Date: 2021-03-30 23:00:02
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"notificationQueue.name=test"})
public class Excel {
    @Resource
    private ExcelService excelService;

    /**
     * 最简单的写
     * <p>1. 创建excel对应的实体对象 {@link MarkSummary}
     * <p>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        String fileName = System.getProperty("user.dir") +"/simpleWrite" + System.currentTimeMillis() + ".xlsx";
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

        EasyExcel.write(fileName, MarkSummary.class)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("sheet1")
                .doWrite(excelService.exportMarkSummary("1b2647cbef313bc18811feb94b8f5080"));
    }

    private List<MarkSummary> data() {
        List<MarkSummary> list = new ArrayList<>();
        list.add(MarkSummary.builder()
                .studentName("xiaoming")
                .blankScore(10)
                .judgeScore(22)
                .singleScore(10)
                .multipleScore(22)
                .subjectiveScore(233)
                .totalScore(100)
                .isSubmit("已提交")
                .cheatState("正常")
                .build());
        return list;
    }

    @Test
    public void writeTemplate() {
        String fileName = System.getProperty("user.dir") +"/导入题目模版.xlsx";

        ExcelWriter excelWriter = null;

        try {
            excelWriter = EasyExcel.write(fileName).build();
            excelWriter
                    .write(new ArrayList<QuestionBlank>(),EasyExcel.writerSheet(0,"填空题").head(QuestionBlank.class).build())
                    .write(new ArrayList<QuestionJudge>(),EasyExcel.writerSheet(1,"判断题").head(QuestionJudge.class).build())
                    .write(new ArrayList<QuestionSingle>(),EasyExcel.writerSheet(2,"单选题").head(QuestionSingle.class).build())
                    .write(new ArrayList<QuestionMultiple>(),EasyExcel.writerSheet(3,"多选题").head(QuestionMultiple.class).build())
                    .write(new ArrayList<QuestionSubjective>(),EasyExcel.writerSheet(4,"主观题").head(QuestionSubjective.class).build());
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Resource
    private QuestionBlankService questionBlankService;
    @Resource
    private QuestionJudgeService questionJudgeService;
    @Resource
    private QuestionSingleService questionSingleService;
    @Resource
    private QuestionMultipleService questionMultipleService;
    @Resource
    private QuestionSubjectiveService questionSubjectiveService;

    /**
     * 读多个或者全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * 3. 直接读即可
     */
    @Test
    @SneakyThrows
    public void repeatedRead() {
        String fileName = "/Users/zhanxinming/IdeaProjects/aifocus-back/importTest.xlsx";

        String teacherId = "zhanx_is_god";

        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName).build();
            // 使用功能必须不同的Listener
            ReadSheet blankSheet =
                    EasyExcel.readSheet(0).head(QuestionBlank.class).registerReadListener(new QuestionListener<>(questionBlankService,teacherId)).build();
            ReadSheet judgeSheet =
                    EasyExcel.readSheet(1).head(QuestionJudge.class).registerReadListener(new QuestionListener<>(questionJudgeService,teacherId)).build();
            ReadSheet singleSheet =
                    EasyExcel.readSheet(2).head(QuestionSingle.class).registerReadListener(new QuestionListener<>(questionSingleService,teacherId)).build();
            ReadSheet multipleSheet =
                    EasyExcel.readSheet(3).head(QuestionMultiple.class).registerReadListener(new QuestionListener<>(questionMultipleService,teacherId)).build();
            ReadSheet subjectiveSheet =
                    EasyExcel.readSheet(4).head(QuestionSubjective.class).registerReadListener(new QuestionListener<>(questionSubjectiveService,teacherId)).build();
            // 这里注意 一定要把 blankSheet judgeSheet singleSheet multipleSheet subjectiveSheet 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(blankSheet, judgeSheet, singleSheet, multipleSheet, subjectiveSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

}
