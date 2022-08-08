package org.cmyk.aifocus.amqp.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MailMessage implements Serializable {
    private String messageId;

    private String to;

    private String code;

    private LocalDateTime expireTime;
}
