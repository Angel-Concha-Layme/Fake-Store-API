package com.fakestore.api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parseDateTime(String dateTimeStr){
        if(dateTimeStr==null){
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, FORMATTER);
    }

    public static boolean isValidDataRange(LocalDateTime start, LocalDateTime end){
        if(start==null || end==null){
            return false;
        }
        return !start.isAfter(end);
    }
}
