package org.cmyk.aifocus.amqp.comsumer;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.cmyk.aifocus.amqp.message.MarkMessage;
import org.cmyk.aifocus.constants.MessageStatus;
import org.cmyk.aifocus.entity.ExamMarkRecord;
import org.cmyk.aifocus.service.ExamMarkRecordService;
import org.cmyk.aifocus.service.MarkService;
import org.cmyk.aifocus.service.MessageLogService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.Future;

@Component
@Slf4j
public class RabbitMarkReceiver {

    @Resource
    private MarkService markService;

    @Resource
    private ExamMarkRecordService examMarkRecordService;

    @Resource
    private MessageLogService messageLogService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "mark-queue", durable = "true"),
                    exchange = @Exchange(name = "aifocus-exchange", durable = "true", type = ExchangeTypes.TOPIC),
                    key = "mark.*"
            )
    )
    @RabbitHandler
    public void onMarkMessage(@Payload MarkMessage markMessage,
                              @Headers Map<String, Object> headers,
                              Channel channel) throws Exception {
//        log.debug("---------收到消息--------");
        log.debug("消费者mark  " + new Gson().toJson(markMessage));
//        log.info("========================================== Start ==========================================");
        log.info("消费者mark批改试卷 examId: " + markMessage.getExamId(), ", studentId" + markMessage.getStudentId());
//        log.info("=========================================== End ===========================================");
        // 幂等
        ExamMarkRecord record = examMarkRecordService.getOne(Wrappers.lambdaQuery(ExamMarkRecord.class)
                .eq(ExamMarkRecord::getExamId, markMessage.getExamId())
                .eq(ExamMarkRecord::getStudentId, markMessage.getStudentId()));
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        if (record == null) {
            channel.basicAck(deliverTag, false);
            return;
        }
        if (record.getIsMarked()) {
            channel.basicAck(deliverTag, false);
            return;
        }

        // 业务
        Future<Boolean> success = markService.markPaper(markMessage.getExamId(), markMessage.getStudentId());
        if (success.get()) {
            messageLogService.changeMessageStatus(markMessage.getMessageId(), MessageStatus.SEND_SUCCESS, LocalDateTime.now());
            channel.basicAck(deliverTag, false);
        } else {
            channel.basicNack(deliverTag, false, true);
        }

    }
}
