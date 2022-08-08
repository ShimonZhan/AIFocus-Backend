package org.cmyk.aifocus.amqp.producer;

import lombok.extern.slf4j.Slf4j;
import org.cmyk.aifocus.amqp.message.MarkMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class RabbitMarkSender {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(MarkMessage markMessage) {
        CorrelationData correlationData = new CorrelationData(markMessage.getMessageId());
        rabbitTemplate.convertAndSend("aifocus-exchange", "mark.abcd", markMessage, correlationData);
    }
}
