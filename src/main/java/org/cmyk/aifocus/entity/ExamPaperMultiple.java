package org.cmyk.aifocus.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamPaperMultiple extends Model<ExamPaperMultiple> {
    @TableId
    private String id;

    private String examId;

    private String questionId;

    private String content;

    private String a;

    private String b;

    private String c;

    private String d;

    private Integer answer;

    private Integer score;
}