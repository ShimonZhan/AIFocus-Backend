package org.cmyk.aifocus.utils;

import com.mysql.cj.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertUtil {
    public static Integer integerChange(Integer integer) {
        if (integer == -1)
            return null;
        else
            return integer;
    }

    public static String stringChange(String string) {
        if (StringUtils.isNullOrEmpty(string.trim()))
            return null;
        else
            return string;
    }

    public static LocalDateTime localDateTimeChange(String string) {
        if (StringUtils.isNullOrEmpty(string.trim()))
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(string, formatter);
    }
}
