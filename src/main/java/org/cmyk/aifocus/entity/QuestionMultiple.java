package org.cmyk.aifocus.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import org.cmyk.aifocus.utils.ExcelAnswerCodeConverter;
import org.cmyk.aifocus.utils.ExcelExtra;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionMultiple extends Model<QuestionMultiple> implements ExcelExtra {
    @TableId
    @ExcelIgnore
    private String id;

    @ExcelIgnore
    private String teacherId;

    @ExcelProperty(value = "题目", index = 0)
    private String content;

    @ExcelProperty(value = "A", index = 1)
    private String a;

    @ExcelProperty(value = "B", index = 2)
    private String b;

    @ExcelProperty(value = "C", index = 3)
    private String c;

    @ExcelProperty(value = "D", index = 4)
    private String d;

    @ExcelProperty(value = "答案(大写ABCD)", index = 5, converter = ExcelAnswerCodeConverter.class)
    private Integer answer;

    @Override
    public void updateTeacherId(String teacherId) {
        this.setTeacherId(teacherId);
    }
}
