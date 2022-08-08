package org.cmyk.aifocus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cmyk.aifocus.entity.MessageLog;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageLogService extends IService<MessageLog> {
    List<MessageLog> query4StatusAndTimeoutMessage();

    Boolean update4Resend(String id, LocalDateTime updateTime);

    Boolean changeMessageStatus(String id, Integer status, LocalDateTime updateTime);
}
