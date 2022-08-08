package org.cmyk.aifocus.amqp.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MarkMessage implements Serializable {
    private String messageId;

    private String examId;

    private String studentId;
}
