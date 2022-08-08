package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static com.github.methodia.minibilling.Main.ZONE_ID;

public class Formatter {

    private String date;

    public Formatter(String date) {
        this.date = date;
    }
    public static LocalDate parseBorder(String date) {
        DateTimeFormatter formatterBorderTime = new DateTimeFormatterBuilder()
                .appendPattern("yy-MM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 31)
                .toFormatter();
        LocalDate borderTime = LocalDate.parse(date, formatterBorderTime);
        return borderTime;
    }
    public static ZonedDateTime parseReading(String date){
        ZonedDateTime readingDate = ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME).withZoneSameInstant(ZoneId.of(ZONE_ID));
        return readingDate;
    }
    public static ZonedDateTime parsePriceStart(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDateStart = LocalDate.parse(date, formatter);
        int yearStart = localDateStart.getYear();
        int monthValueStart = localDateStart.getMonthValue();
        int dayOfMonthStart = localDateStart.getDayOfMonth();
        ZonedDateTime start = ZonedDateTime.of(yearStart, monthValueStart, dayOfMonthStart,
                0, 0, 0, 0, ZoneId.of(ZONE_ID));
        return start;
    }
    public static ZonedDateTime parsePriceEnd(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDateEnd = LocalDate.parse(date, formatter);
        int yearEnd = localDateEnd.getYear();
        int monthValueEnd = localDateEnd.getMonthValue();
        int dayOfMonthEnd = localDateEnd.getDayOfMonth();
        ZonedDateTime end = ZonedDateTime.of(yearEnd, monthValueEnd, dayOfMonthEnd,
                23, 59, 59, 0, ZoneId.of(ZONE_ID));
        return end;
    }
}
