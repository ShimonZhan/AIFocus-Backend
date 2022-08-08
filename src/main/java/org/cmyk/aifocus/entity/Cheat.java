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
public class Cheat extends Model<Cheat> {
    @TableId
    private String id;

    private String studentId;

    private String examId;

    private String monitorPhoto;

    private LocalDateTime timeStamp;
}
