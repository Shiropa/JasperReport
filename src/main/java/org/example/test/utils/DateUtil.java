package org.example.test.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateUtil {

    static String clientDateFormat = "dd/MM/yyyy";

    public static String getSimpleDateFormatInBn(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(clientDateFormat);
        return StringUtil.convertToBn(simpleDateFormat.format(date));
    }

    public static String getSimpleDateFormatInBn(LocalDate date) {
        if (date == null)
            return "";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(clientDateFormat);
        return StringUtil.convertToBn(dateTimeFormatter.format(date));
    }
}
