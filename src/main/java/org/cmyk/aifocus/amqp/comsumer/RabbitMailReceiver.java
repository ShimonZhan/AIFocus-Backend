package org.cmyk.aifocus.amqp.comsumer;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.cmyk.aifocus.amqp.message.MailMessage;
import org.cmyk.aifocus.constants.MessageStatus;
import org.cmyk.aifocus.entity.User;
import org.cmyk.aifocus.service.MailService;
import org.cmyk.aifocus.service.MessageLogService;
import org.cmyk.aifocus.service.UserService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@Slf4j
public class RabbitMailReceiver {

    @Resource
    private MailService mailService;

    @Resource
    private UserService userService;

    @Resource
    private MessageLogService messageLogService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "mail-queue", durable = "true"),
                    exchange = @Exchange(name = "aifocus-exchange", durable = "true", type = ExchangeTypes.TOPIC),
                    key = "mail.*"
            )
    )
    @RabbitHandler
    public void onMailMessage(@Payload MailMessage mailMessage,
                              @Headers Map<String, Object> headers,
                              Channel channel) throws Exception {
//        log.debug("---------收到消息--------");
        log.debug("消费者mail  " + new Gson().toJson(mailMessage));
        log.info("消费者mail发送邮件到" + mailMessage.getTo(), ",验证码:" + mailMessage.getCode());

        // 幂等
        User user = userService.getOne(Wrappers.lambdaQuery(User.class)
                .eq(User::getEmail, mailMessage.getTo())
                .eq(User::getCheckCode, mailMessage.getCode()));
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        if (user == null) {
            log.debug("no-user");
            channel.basicAck(deliverTag, false);
            return;
        }
        if (user.getCheckCodeExpireTime() == mailMessage.getExpireTime()) {
            log.debug(mailMessage.getMessageId() + "already deal return");
            channel.basicAck(deliverTag, false);
            return;
        }

        // 业务
        boolean success = mailService.sendMail(mailMessage.getTo(), mailMessage.getCode());
        if (success) {
            messageLogService.changeMessageStatus(mailMessage.getMessageId(), MessageStatus.SEND_SUCCESS, LocalDateTime.now());
            channel.basicAck(deliverTag, false);
        } else {
            channel.basicNack(deliverTag, false, true);
        }
    }
}
