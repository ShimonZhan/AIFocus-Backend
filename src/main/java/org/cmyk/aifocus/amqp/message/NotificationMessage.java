package org.cmyk.aifocus.amqp.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class NotificationMessage implements Serializable {
    private String messageId;

    private String fromUserId;

    private String toUserId;

    private String examId;

    private Integer notificationType;

    private String content;
}
