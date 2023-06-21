package com.alisa.amazon.clone.backend.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@UtilityClass
public class DateTimeUtils {
    public static String convertDateTimeToString(LocalDateTime localDateTime, String pattern) {
        return Optional.ofNullable(localDateTime)
                .map(date -> date.format(DateTimeFormatter.ofPattern(pattern)))
                .orElse(null);
    }

    public static LocalDateTime convertDateStringToLocalDateTime(String dateTime, String pattern) {
        return Optional.ofNullable(dateTime)
                .map(date -> LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern)))
                .orElse(null);
    }
}
