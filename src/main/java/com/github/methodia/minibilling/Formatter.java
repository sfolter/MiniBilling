package com.github.methodia.minibilling;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static com.github.methodia.minibilling.Main.ZONE_ID;

public class Formatter {

    public static LocalDate parseBorder(String date) {
        DateTimeFormatter formatterBorderTime = new DateTimeFormatterBuilder()
                .appendPattern("yy-MM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 31)
                .toFormatter();
        return LocalDate.parse(date, formatterBorderTime);

    }

    public static ZonedDateTime parseReading(String date) {
        return ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME).withZoneSameInstant(ZoneId.of(ZONE_ID));
    }

    public static ZonedDateTime parsePriceStart(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDateStart = LocalDate.parse(date, formatter);
        int yearStart = localDateStart.getYear();
        int monthValueStart = localDateStart.getMonthValue();
        int dayOfMonthStart = localDateStart.getDayOfMonth();
        return ZonedDateTime.of(yearStart, monthValueStart, dayOfMonthStart,
                0, 0, 0, 0, ZoneId.of(ZONE_ID));
    }

    public static ZonedDateTime parsePriceEnd(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDateEnd = LocalDate.parse(date, formatter);
        int yearEnd = localDateEnd.getYear();
        int monthValueEnd = localDateEnd.getMonthValue();
        int dayOfMonthEnd = localDateEnd.getDayOfMonth();
        return ZonedDateTime.of(yearEnd, monthValueEnd, dayOfMonthEnd,
                23, 59, 59, 0, ZoneId.of(ZONE_ID));
    }
}
