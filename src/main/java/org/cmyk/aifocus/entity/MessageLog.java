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
public class MessageLog extends Model<MessageLog> {
    @TableId
    private String id;

    private String message;

    private Integer tryCount;

    private Integer status;

    private LocalDateTime nextRetry;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
