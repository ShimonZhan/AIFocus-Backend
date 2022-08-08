package org.cmyk.aifocus.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamMarkRecord extends Model<ExamMarkRecord> {
    @TableId
    private String id;

    private String studentId;

    private String examId;

    private Integer blankScore;

    private Integer judgeScore;

    private Integer multipleScore;

    private Integer singleScore;

    private Integer subjectiveScore;

    private Integer totalScore;

    private Boolean isMarked;

    private Boolean isSubmit;

    private Integer state;
}
