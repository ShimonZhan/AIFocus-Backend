package org.cmyk.aifocus.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam extends Model<Exam> {
    @TableId
    private String id;

    private String name;

    private String teacherId;

    private String classId;

    private String courseName;

    private Integer fullScore;

    private LocalDateTime createTime;

    private LocalDateTime startTime;

    private LocalDateTime finishTime;

    private Boolean isAutoMarkMessageSend;

    private Integer status;
}