package org.cmyk.aifocus.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.cmyk.aifocus.amqp.message.NotificationMessage;
import org.cmyk.aifocus.amqp.producer.RabbitNotificationSender;
import org.cmyk.aifocus.constants.NotificationType;
import org.cmyk.aifocus.constants.UserRole;
import org.cmyk.aifocus.dao.ExamMapper;
import org.cmyk.aifocus.entity.Exam;
import org.cmyk.aifocus.entity.User;
import org.cmyk.aifocus.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint(value = "/ws/{userId}")
public class MyWebSocket {
    private static final ConcurrentHashMap<String, MyWebSocket> sessionStore = new ConcurrentHashMap<>();
    private static UserService userService;
    private static ExamMapper examMapper;
    private static RabbitNotificationSender rabbitNotificationSender;
    private Session session;
    private String userId;
    private LocalDateTime lastConnectTime;

    public static ConcurrentHashMap<String, MyWebSocket> getSessionStore() {
        return sessionStore;
    }

    public static void sendMessage(NotificationMessage message) throws IOException {
        MyWebSocket endpoint = MyWebSocket.sessionStore.get(message.getToUserId());
        if (endpoint != null) {
//            log.info("========================================== Start ==========================================");
            String messageJson = new Gson().toJson(message);
            log.info("已找到websocketSession: " + message.getToUserId() + ", 发送消息：" + messageJson);
            endpoint.session.getBasicRemote().sendText(messageJson);
            //pushToMQ(message);
//            log.info("=========================================== End ===========================================");
        } else {
            log.debug("未找到websocketSession：" + message.getToUserId());
        }
    }

    public Session getSession() {
        return session;
    }

    public LocalDateTime getLastConnectTime() {
        return lastConnectTime;
    }

    @Resource
    public void setRabbitNotificationSender(RabbitNotificationSender rabbitNotificationSender) {
        MyWebSocket.rabbitNotificationSender = rabbitNotificationSender;
    }

    @Resource
    public void setUserService(UserService userService) {
        MyWebSocket.userService = userService;
    }

    @Resource
    public void setExamMapper(ExamMapper examMapper) {
        MyWebSocket.examMapper = examMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
//        log.info("========================================== Start ==========================================");
        log.info("{} 上线了", userId);
//        log.info("=========================================== End ===========================================");
        this.session = session;
        this.userId = userId;
        sessionStore.put(userId, this);
        this.lastConnectTime = LocalDateTime.now();
        User user = userService.getById(userId);
        user.setIsOnline(true);
        user.updateById();
        if (!user.getType().equals(UserRole.STUDENT)) return;
        List<Exam> exams = examMapper.selectExamsInExamStatus(userId);
        for (Exam exam : exams) {
            NotificationMessage message = NotificationMessage.builder()
                    .messageId(IdWorker.get32UUID())
                    .fromUserId(userId)
                    .toUserId(exam.getTeacherId())
                    .examId(exam.getId())
                    .content("")
                    .notificationType(NotificationType.STUDENT_ONLINE)
                    .build();
            pushToMQ(message);
        }
    }

    @OnClose
    public void onClose() {
//        log.info("========================================== Start ==========================================");
        log.info("{} 下线了", userId);
//        log.info("=========================================== End ===========================================");
        sessionStore.remove(this.userId);
        User user = userService.getById(userId);
        user.setIsOnline(false);
        user.updateById();
        if (!user.getType().equals(UserRole.STUDENT)) return;
        List<Exam> exams = examMapper.selectExamsInExamStatus(userId);
        for (Exam exam : exams) {
            NotificationMessage message = NotificationMessage.builder()
                    .messageId(IdWorker.get32UUID())
                    .fromUserId(userId)
                    .toUserId(exam.getTeacherId())
                    .content("")
                    .examId(exam.getId())
                    .notificationType(NotificationType.STUDENT_OFFLINE)
                    .build();
            pushToMQ(message);
        }
    }

    @OnMessage
    public void onMessage(String message) {
        log.debug("接收到信息：" + message);
        this.lastConnectTime = LocalDateTime.now();
        if (message.equals("HeartBeat")) {
            log.debug(userId + " heartbeat");
            return;
        }
        NotificationMessage notificationMessage = new Gson().fromJson(message, NotificationMessage.class);
        notificationMessage.setMessageId(IdWorker.get32UUID());
        pushToMQ(notificationMessage);
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    private void pushToMQ(NotificationMessage message) {
        rabbitNotificationSender.send(message);
    }
}
