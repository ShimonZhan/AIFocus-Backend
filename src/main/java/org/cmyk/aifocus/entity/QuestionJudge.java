package org.cmyk.aifocus.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import org.cmyk.aifocus.utils.ExcelExtra;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionJudge extends Model<QuestionJudge> implements ExcelExtra {
    @TableId
    @ExcelIgnore
    private String id;

    @ExcelIgnore
    private String teacherId;

    @ExcelProperty(value = "题目", index = 0)
    private String content;

    @ExcelProperty(value = "答案(0错，1对)", index = 1)
    private Integer answer;

    @Override
    public void updateTeacherId(String teacherId) {
        this.setTeacherId(teacherId);
    }
}
