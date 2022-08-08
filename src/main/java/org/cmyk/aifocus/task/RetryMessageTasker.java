package org.cmyk.aifocus.task;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.cmyk.aifocus.amqp.message.MailMessage;
import org.cmyk.aifocus.amqp.message.MarkMessage;
import org.cmyk.aifocus.amqp.producer.RabbitMailSender;
import org.cmyk.aifocus.amqp.producer.RabbitMarkSender;
import org.cmyk.aifocus.constants.MessageStatus;
import org.cmyk.aifocus.entity.MessageLog;
import org.cmyk.aifocus.service.MessageLogService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class RetryMessageTasker {
    @Resource
    private RabbitMailSender rabbitMailSender;

    @Resource
    private RabbitMarkSender rabbitMarkSender;

    @Resource
    private MessageLogService messageLogService;

    @Scheduled(initialDelay = 3000, fixedDelay = 20000)
    public void reSend() {
        log.debug("----定时重发任务开始----");
        List<MessageLog> list = messageLogService.query4StatusAndTimeoutMessage();
        for (MessageLog messageLog : list) {
            // fail
            if (messageLog.getTryCount() >= 3) {
                messageLogService.changeMessageStatus(messageLog.getId(), MessageStatus.SEND_FAILURE, LocalDateTime.now());
            } else {
                // resend
                messageLogService.update4Resend(messageLog.getId(), LocalDateTime.now());
                if (messageLog.getId().startsWith("mail")) {
                    MailMessage reSendMessage = new Gson().fromJson(messageLog.getMessage(), MailMessage.class);
                    try {
                        rabbitMailSender.send(reSendMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("---mail异常2--");
                    }
                } else if (messageLog.getId().startsWith("mark")) {
                    MarkMessage reSendMessage = new Gson().fromJson(messageLog.getMessage(), MarkMessage.class);
                    try {
                        rabbitMarkSender.send(reSendMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("---mark异常2--");
                    }
                }

            }
        }
    }
}
