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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class FileMaker {

    public static void FileSaver(Invoice invoice, String outputPath, LocalDateTime dateToReporting) {

        try {
            createFolder(outputPath, invoice);
            createJsonFile(invoice, outputPath, dateToReporting);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createJsonFile(Invoice invoice, String folderPath, LocalDateTime parseReportingDate)
            throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new FileMaker.LocalDateAdapter()).create();
        String json = gson.toJson(invoice);

        String monthInCyrillic = parseReportingDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("bg"));
        String month = monthInCyrillic.substring(0, 1).toUpperCase() + monthInCyrillic.substring(1);
        String year = String.valueOf(parseReportingDate.getYear()).substring(2, 4);

        String outputPath = folderPath + "\\" + invoice.getConsumer() + "-" + invoice.getReference();
        String fileName = outputPath + "\\" + invoice.getDocNumber() + "-" + month + "-" + year + ".json";

        FileWriter myWriter = new FileWriter(fileName);
        myWriter.write(json);
        myWriter.close();
    }

    private static void createFolder(String outputDir, Invoice invoice) {
        String folderPath = outputDir + "\\" + invoice.getConsumer() + "-" + invoice.getReference();
        File creatingFolder = new File(folderPath);
        File directory = new File(String.valueOf(creatingFolder));
        directory.mkdirs();
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
