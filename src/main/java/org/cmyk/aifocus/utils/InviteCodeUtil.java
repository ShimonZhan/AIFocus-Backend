package org.cmyk.aifocus.utils;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.netty.util.internal.StringUtil;
import org.cmyk.aifocus.entity.Clazz;
import org.cmyk.aifocus.service.ClassService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InviteCodeUtil {
    public static LocalDateTime changeOrDefaultExpireTime(String inviteCodeExpireTime) {
        LocalDateTime expireTime;
        if (StringUtil.isNullOrEmpty(inviteCodeExpireTime.trim())) {
            expireTime = LocalDateTime.now().plusDays(7);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            expireTime = LocalDateTime.parse(inviteCodeExpireTime, formatter);
        }
        return expireTime;
    }

    public static String changeOrDefaultInviteCode(String inviteCode) {
        ClassService classService = SpringUtil.getBean(ClassService.class);
        if (StringUtil.isNullOrEmpty(inviteCode.trim())) {
            String code = getInviteCode();
            while (classService.getOne(Wrappers.lambdaQuery(Clazz.class)
                    .eq(Clazz::getInviteCode, code)) != null) {
                code = getInviteCode();
            }
            return code;
        } else {
            if (classService.getOne(Wrappers.lambdaQuery(Clazz.class)
                    .eq(Clazz::getInviteCode, inviteCode)) == null) {
                return inviteCode;
            } else {
                return null;
            }
        }
    }

    private static String getInviteCode() {
        StringBuilder randomCode = new StringBuilder();
        String model = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] m = model.toCharArray();
        for (int j = 0; j < 6; j++) {
            char c = m[(int) (Math.random() * 36)];
            if (randomCode.toString().contains(String.valueOf(c))) {
                j--;
                continue;
            }
            randomCode.append(c);
        }
        return randomCode.toString();
    }
}
