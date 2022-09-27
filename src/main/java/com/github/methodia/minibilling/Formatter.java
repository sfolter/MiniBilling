package com.github.methodia.minibilling;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static com.github.methodia.minibilling.Main.ZONE_ID;

public final class Formatter {

    private Formatter() {
    }

    public static LocalDate parseBorder(final String date) {
        final DateTimeFormatter formatterBorderTime = new DateTimeFormatterBuilder()
                .appendPattern("yy-MM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 31)
                .toFormatter();
        return LocalDate.parse(date, formatterBorderTime);

    }

    public static ZonedDateTime parseReading(final String date) {
        return ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public static ZonedDateTime parsePriceStart(final String date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate localDateStart = LocalDate.parse(date, formatter);
        final int yearStart = localDateStart.getYear();
        final int monthValueStart = localDateStart.getMonthValue();
        final int dayOfMonthStart = localDateStart.getDayOfMonth();
        return ZonedDateTime.of(yearStart, monthValueStart, dayOfMonthStart,
                0, 0, 0, 0, ZoneId.of(ZONE_ID));
    }

    public static ZonedDateTime parsePriceEnd(final String date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate localDateEnd = LocalDate.parse(date, formatter);
        final int yearEnd = localDateEnd.getYear();
        final int monthValueEnd = localDateEnd.getMonthValue();
        final int dayOfMonthEnd = localDateEnd.getDayOfMonth();
        return ZonedDateTime.of(yearEnd, monthValueEnd, dayOfMonthEnd,
                23, 59, 59, 0, ZoneId.of(ZONE_ID));
    }
}
