package org.cmyk.aifocus.amqp.comsumer;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.cmyk.aifocus.amqp.message.NotificationMessage;
import org.cmyk.aifocus.controller.MyWebSocket;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class RabbitNotificationReceiver {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${notificationQueue.name}", durable = "true", autoDelete = "true"),
                    exchange = @Exchange(name = "aifocus-notification-exchange", durable = "true", type = ExchangeTypes.FANOUT),
                    key = "notification.*"
            )
    )
    @RabbitHandler
    public void onMessage(@Payload NotificationMessage message,
                          @Headers Map<String, Object> headers,
                          Channel channel) throws Exception {
        log.debug("消费者Notification 消费消息：" + new Gson().toJson(message));
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        MyWebSocket.sendMessage(message);
        channel.basicAck(deliverTag, false);
    }
}
