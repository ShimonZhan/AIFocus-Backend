package org.cmyk.aifocus.amqp.producer;

import lombok.extern.slf4j.Slf4j;
import org.cmyk.aifocus.amqp.message.MailMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class RabbitMailSender {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(MailMessage mailMessage) {
        CorrelationData correlationData = new CorrelationData(mailMessage.getMessageId());
        rabbitTemplate.convertAndSend("aifocus-exchange", "mail.abcd", mailMessage, correlationData);
    }

}
