package org.cmyk.aifocus.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamMarkSummary extends Model<ExamMarkSummary> {
    @TableId
    private String id;

    private String examId;

    private Integer totalStudentNumber;

    private Integer markStudentNumber;
}