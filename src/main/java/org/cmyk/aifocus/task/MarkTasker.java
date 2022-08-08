package org.cmyk.aifocus.task;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.cmyk.aifocus.amqp.message.MarkMessage;
import org.cmyk.aifocus.amqp.producer.RabbitMarkSender;
import org.cmyk.aifocus.constants.ExamStatus;
import org.cmyk.aifocus.constants.MessageStatus;
import org.cmyk.aifocus.entity.Exam;
import org.cmyk.aifocus.entity.ExamMarkRecord;
import org.cmyk.aifocus.entity.MessageLog;
import org.cmyk.aifocus.service.ExamMarkRecordService;
import org.cmyk.aifocus.service.ExamService;
import org.cmyk.aifocus.service.MessageLogService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MarkTasker {

    @Resource
    private ExamService examService;

    @Resource
    private ExamMarkRecordService examMarkRecordServiced;

    @Resource
    private MessageLogService messageLogService;

    @Resource
    private RabbitMarkSender rabbitMarkSender;

    @Scheduled(initialDelay = 3000, fixedDelay = 20000)
    public void sendMark() {
        // 选择需要自动批改的考试
        List<Exam> exams = examService.list(Wrappers.lambdaQuery(Exam.class)
                .eq(Exam::getIsAutoMarkMessageSend, false)
                .eq(Exam::getStatus, ExamStatus.FINISH)
                .le(Exam::getFinishTime, LocalDateTime.now().minusMinutes(3)));

        if (exams.size() > 0) {
            exams.forEach(e -> e.setIsAutoMarkMessageSend(true));
            examService.updateBatchById(exams);

            List<MessageLog> messageLogs = new ArrayList<>();
            List<MarkMessage> markMessages = new ArrayList<>();

            // 利用JAVA8流式处理生成消息
            exams.stream().map(Exam::getId).forEach(examId -> {
                List<MessageLog> logs = examMarkRecordServiced.list(Wrappers.lambdaQuery(ExamMarkRecord.class)
                        .eq(ExamMarkRecord::getExamId, examId).select((x -> x.getProperty().equals("studentId"))))
                        .stream()
                        .map(x -> {
                            String messageId = "mark-" + examId + "$" + x.getStudentId();
                            MarkMessage markMessage = new MarkMessage(messageId, examId, x.getStudentId());
                            markMessages.add(markMessage);
                            return MessageLog.builder()
                                    .id(messageId)
                                    .status(MessageStatus.SENDING)
                                    .message(new Gson().toJson(markMessage))
                                    .nextRetry(LocalDateTime.now().plusMinutes(MessageStatus.TIMEOUT))
                                    .createTime(LocalDateTime.now())
                                    .updateTime(LocalDateTime.now())
                                    .build();
                        })
                        .collect(Collectors.toList());
                messageLogs.addAll(logs);
            });

            // 存储消息到消息重试表
            messageLogService.saveBatch(messageLogs);
            // 发送消息到消息队列
            markMessages.forEach(markMessage -> rabbitMarkSender.send(markMessage));
        }
    }
}
