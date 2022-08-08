package org.cmyk.aifocus.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@TableName("class")
@Builder
public class Clazz extends Model<Clazz> {
    @TableId
    private String id;

    private String className;

    private String courseName;

    private String inviteCode;

    private LocalDateTime inviteCodeExpireTime;

    private Boolean isVisible;

    private LocalDateTime createTime;

    private String avater;
}