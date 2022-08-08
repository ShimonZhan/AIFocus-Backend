package org.cmyk.aifocus.task;

import lombok.extern.slf4j.Slf4j;
import org.cmyk.aifocus.controller.MyWebSocket;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class HeartBeatTasker {
    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void cleanTimeOut() throws IOException {
        ConcurrentHashMap<String, MyWebSocket> sessionStore = MyWebSocket.getSessionStore();
        for (Map.Entry<String, MyWebSocket> entry : sessionStore.entrySet()) {
            Duration duration = Duration.between(entry.getValue().getLastConnectTime(), LocalDateTime.now());
            //log.debug(entry.getKey() +" " + duration.getSeconds());
            if (duration.getSeconds() >= 15L) {
                log.info("webSocket超时清理 " + entry.getKey());
                entry.getValue().getSession().close();
                sessionStore.remove(entry.getKey());
            }
        }
    }
}
