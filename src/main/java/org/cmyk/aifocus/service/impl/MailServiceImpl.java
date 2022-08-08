package org.cmyk.aifocus.service.impl;

import com.google.gson.Gson;
import org.cmyk.aifocus.amqp.message.MailMessage;
import org.cmyk.aifocus.amqp.producer.RabbitMailSender;
import org.cmyk.aifocus.constants.MessageStatus;
import org.cmyk.aifocus.entity.MessageLog;
import org.cmyk.aifocus.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Service
@Transactional
public class MailServiceImpl implements MailService {
    @Resource
    private RabbitMailSender rabbitMailSender;

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendMail(String to, String code) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("AIFocus--账号验证");
            helper.setText("<p style=\"margin:0;padding:0;font-size:14px;line-height:30px;color:#333;font-family:arial,sans-serif;font-weight:bold\">" +
                    "亲爱的用户：</p>" +
                    "<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif\">" +
                    "<p style=\"margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif\">" +
                    "您好！感谢您使用AIFocus服务，您的账号正在进行邮箱验证，本次请求的验证码为：</p>" +
                    "<b style=\"font-size:18px;color:#f90\">" + code + "</b>" +
                    "<span style=\"margin:0;padding:0;margin-left:10px;line-height:30px;font-size:14px;color:#979797;font-family:'宋体',arial,sans-serif\">" +
                    "(为了保障您帐号的安全性，请在2小时内完成验证。)</span></p>", true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void createMail(String to, String code, LocalDateTime expireTime) {
        String messageId = "mail-" + to + "$" + expireTime.toString();
        MailMessage mailMessage = new MailMessage(messageId, to, code, expireTime);
        MessageLog.builder().
                id(messageId)
                .status(MessageStatus.SENDING)
                .message(new Gson().toJson(mailMessage))
                .nextRetry(LocalDateTime.now().plusMinutes(MessageStatus.TIMEOUT))
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build()
                .insert();
        rabbitMailSender.send(mailMessage);
    }
}
