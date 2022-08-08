package org.cmyk.aifocus.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cmyk.aifocus.constants.MessageStatus;
import org.cmyk.aifocus.dao.MessageLogMapper;
import org.cmyk.aifocus.entity.MessageLog;
import org.cmyk.aifocus.service.MessageLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MessageLogServiceImpl extends ServiceImpl<MessageLogMapper, MessageLog> implements MessageLogService {
    @Override
    public List<MessageLog> query4StatusAndTimeoutMessage() {
        return list(Wrappers.lambdaQuery(MessageLog.class)
                .eq(MessageLog::getStatus, MessageStatus.SENDING)
                .lt(MessageLog::getNextRetry, LocalDateTime.now()));
    }

    @Override
    public Boolean update4Resend(String id, LocalDateTime updateTime) {
        MessageLog messageLog = getById(id);
        if (messageLog == null) {
            return false;
        }
        messageLog.setTryCount(messageLog.getTryCount() + 1);
        messageLog.setUpdateTime(updateTime);
        return messageLog.updateById();
    }

    @Override
    public Boolean changeMessageStatus(String id, Integer status, LocalDateTime updateTime) {
        MessageLog messageLog = getById(id);
        if (messageLog == null) {
            return false;
        }
        messageLog.setStatus(status);
        messageLog.setUpdateTime(updateTime);
        return messageLog.updateById();
    }

}
