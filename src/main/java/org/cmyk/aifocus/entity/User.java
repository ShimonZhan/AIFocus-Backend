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
public class User extends Model<User> {
    @TableId
    private String id;

    private Integer type;

    private String name;

    private String email;

    private String password;

    private String phone;

    private String avater;

    private String checkCode;

    private LocalDateTime checkCodeExpireTime;

    private Integer status;

    private Boolean isOnline;
}