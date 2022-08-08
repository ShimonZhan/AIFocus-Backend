package org.cmyk.aifocus.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamPaperBlank extends Model<ExamPaperBlank> {
    @TableId
    private String id;

    private String examId;

    private String questionId;

    private String content;

    private String answer;

    private Integer score;
}