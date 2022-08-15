package com.github.methodia.minibilling;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String dateReporting = args[0];
        String inputDir = args[1];
        LocalDateTime parseReportingDate = getReportingDate(dateReporting);
        String outputDir = args[2];

        UserReaderInterface userReader = new UserReader(inputDir);
        ReadingReader readingReader = new ReadingReader(inputDir);
        List<Reading> readingList = readingReader.read();
        Map<String, User> userMap = userReader.read();

        for (User user : userMap.values()) {

            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readingList);
            Collection<Measurement> measurementCollection = measurementGenerator.generate();

            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurementCollection, user.getPrice());
            Invoice invoice = invoiceGenerator.generate(parseReportingDate);

            String folderPath = createFolder(outputDir, user);
            createJsonFile(parseReportingDate, invoice, folderPath);
        }


//        MeasurementGenerator mmGenerator = new MeasurementGenerator();
//        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
//        userMap.values().stream()
//                .map(user -> mmGenerator.generate(user, readingList))
//                .map(measurements -> invoiceGenerator.generate(measurements, parseReportingDate))
//                .forEach(invoice -> createJsonFile(parseReportingDate, outputDir, invoice));

    }

    private static LocalDateTime getReportingDate(String dateReporting) {
        final YearMonth yearMonth = YearMonth.parse(dateReporting, DateTimeFormatter.ofPattern("yy-MM"));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        ZonedDateTime time = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.of("Z"));
        return LocalDateTime.parse(String.valueOf(time), formatter);
    }


    private static void createJsonFile(LocalDateTime parseReportingDate, Invoice invoice, String folderPath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();
        String json = gson.toJson(invoice);

        String monthInCyrillic = parseReportingDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("bg"));
        String month = monthInCyrillic.substring(0, 1).toUpperCase() + monthInCyrillic.substring(1);
        String year = String.valueOf(parseReportingDate.getYear()).substring(2, 4);

        String fileName = folderPath + "\\" + invoice.getDocNumber() + "-" + month + "-" + year + ".json";

        FileWriter myWriter = new FileWriter(fileName);
        myWriter.write(json);
        myWriter.close();
    }

    private static String createFolder(String outputDir, User user) {
        String folderPath = outputDir + "\\" + user.getName() + "-" + user.getRef();
        File creatingFolder = new File(folderPath);
        File directory = new File(String.valueOf(creatingFolder));
        directory.mkdirs();
        return folderPath;
    }

    private static final class LocalDateAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            ZonedDateTime GMT = localDate.atZone(ZoneId.of("Z"));
            String fm = GMT.format(formatter);
            jsonWriter.value(fm);
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

    }
}