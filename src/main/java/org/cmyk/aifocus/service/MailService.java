package org.cmyk.aifocus.service;

import java.time.LocalDateTime;

public interface MailService {

    boolean sendMail(String to, String code);

    void createMail(String to, String code, LocalDateTime expireTime);
}
