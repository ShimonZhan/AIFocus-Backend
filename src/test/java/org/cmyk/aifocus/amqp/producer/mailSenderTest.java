package org.cmyk.aifocus.amqp.producer;

import org.cmyk.aifocus.service.MailService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class mailSenderTest {
    @Resource
    private MailService mailService;

    @Test
    @Ignore
    public void sendMail() {
        mailService.createMail("942890268@qq.com", "3123", LocalDateTime.now());
    }
}