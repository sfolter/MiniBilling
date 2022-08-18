package com.github.methodia.minibilling;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String dateReporting = args[0];
        String inputDir = args[1];
        LocalDateTime parseReportingDate = getReportingDate(dateReporting);
        String outputDir = args[2];

        UserReaderInterface userReader = new UserReader(inputDir);
        ReadingReaderInterface readingReader = new ReadingReader(inputDir);
        Map<String, User> userMap = userReader.read();

        MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        Map<String, List<Reading>> readingsList = readingReader.read();

        userMap.values().stream()
                .map(user -> measurementGenerator.generate(user, readingsList.get(user.getRef())))
                .map(measurements -> invoiceGenerator.generate(parseReportingDate, measurements))
                .forEach(invoice -> FileMaker.FileSaver(invoice, outputDir, parseReportingDate));

    }

    private static LocalDateTime getReportingDate(String dateReporting) {
        final YearMonth yearMonth = YearMonth.parse(dateReporting, DateTimeFormatter.ofPattern("yy-MM"));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        ZonedDateTime time = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.of("Z"));
        return LocalDateTime.parse(String.valueOf(time), formatter);
    }


}