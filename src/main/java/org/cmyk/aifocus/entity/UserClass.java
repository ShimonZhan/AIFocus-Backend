package org.cmyk.aifocus.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserClass extends Model<UserClass> {
    @TableId
    private String id;

    private Integer UserType;

    private String userId;

    private String classId;

    private String className;
}