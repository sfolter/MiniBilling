package com.example.SpringBatchExample;

import com.example.SpringBatchExample.models.Invoice;
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



    public static void save(final Invoice invoice, final String outputPath, final LocalDateTime dateToReporting) {

        try {
            createFolder(outputPath, invoice);
            createJsonFile(invoice, outputPath, dateToReporting);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createJsonFile(final Invoice invoice, final String folderPath,
                                       final LocalDateTime parseReportingDate)
            throws IOException {
        final Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();
        final String json = gson.toJson(invoice);

        final String monthInCyrillic = parseReportingDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("bg"));
        final String month = monthInCyrillic.substring(0, 1).toUpperCase() + monthInCyrillic.substring(1);
        final String year = String.valueOf(parseReportingDate.getYear()).substring(2, 4);

        final String outputPath = folderPath + "\\" + invoice.getConsumer() + "-" + invoice.getReference();
        final String fileName = outputPath + "\\" + invoice.getDocNumber() + "-" + month + "-" + year + ".json";

        final FileWriter myWriter = new FileWriter(fileName);
        myWriter.write(json);
        myWriter.close();
    }

    private static void createFolder(final String outputDir, final Invoice invoice) {
        final String folderPath = outputDir + "\\" + invoice.getConsumer() + "-" + invoice.getReference();
        final File creatingFolder = new File(folderPath);
        final File directory = new File(String.valueOf(creatingFolder));
        directory.mkdirs();
    }

    private static final class LocalDateAdapter extends TypeAdapter<LocalDateTime> {

        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
            final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            final ZonedDateTime gmt = localDate.atZone(ZoneId.of("Z"));
            final String fm = gmt.format(formatter);
            jsonWriter.value(fm);
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            return
                    LocalDateTime.parse(jsonReader.nextString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

    }
}
