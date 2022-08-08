package org.cmyk.aifocus.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: ZhanX
 * @Date: 2021-03-30 20:33:34
 */
@Data
@Builder
public class MarkSummary {
    @ExcelProperty(value = "姓名", index = 0)
    @ColumnWidth(15)
    private String studentName;

    @ColumnWidth(15)
    @ExcelProperty(value = "填空题得分", index = 1)
    private Integer blankScore;

    @ColumnWidth(15)
    @ExcelProperty(value = "判断题得分", index = 2)
    private Integer judgeScore;

    @ColumnWidth(15)
    @ExcelProperty(value = "单选题得分", index = 3)
    private Integer singleScore;

    @ColumnWidth(15)
    @ExcelProperty(value = "多选题得分", index = 4)
    private Integer multipleScore;

    @ColumnWidth(15)
    @ExcelProperty(value = "主观题得分", index = 5)
    private Integer subjectiveScore;

    @ExcelProperty(value = "总分", index = 6)
    private Integer totalScore;

    @ColumnWidth(15)
    @ExcelProperty(value = "是否提交", index = 7)
    private String isSubmit;

    @ColumnWidth(15)
    @ExcelProperty(value = "作弊状态", index = 8)
    private String cheatState;
}
