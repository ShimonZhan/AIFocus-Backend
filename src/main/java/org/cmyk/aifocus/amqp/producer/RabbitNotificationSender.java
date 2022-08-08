package org.cmyk.aifocus.amqp.producer;

import lombok.extern.slf4j.Slf4j;
import org.cmyk.aifocus.amqp.message.NotificationMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class RabbitNotificationSender {
    @Resource
    private RabbitTemplate rabbitTemplate;


    public void send(NotificationMessage notificationMessage) {
        CorrelationData correlationData = new CorrelationData(notificationMessage.getMessageId());
        rabbitTemplate.convertAndSend("aifocus-notification-exchange", "notification.abcd", notificationMessage, correlationData);
    }
}
