package org.cmyk.aifocus.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerSheet extends Model<AnswerSheet> {

    @TableId
    private String id;

    private String examId;

    private String studentId;

    private String studentName;

    private String examPaperId;

    private Integer questionType;

    private String answer;

    private Integer score;

    private Boolean isMarked;
}
