package com.github.methodia.minibilling.saveinvoice;

import com.github.methodia.minibilling.entity.Invoice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;

public class SaveInvoiceInJson implements SaveInvoice {

    final String outputPath;
    final LocalDate borderDate;

    public SaveInvoiceInJson(final String outputPath, final LocalDate borderDate) {
        this.outputPath = outputPath;
        this.borderDate = borderDate;
    }

    @Override
    public void save(final Invoice invoice) {
        final Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new SaveInvoiceInJson.LocalDateAdapter()).create();
        final String json = gson.toJson(invoice);

        final String month = borderDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("bg"));
        final String month1 = month.substring(0, 1).toUpperCase() + month.substring(1);
        final int outputOfTheYear = borderDate.getYear() % 100;

        final String folderPath = outputPath + "\\" + invoice.getConsumer() + "-" + invoice.getReference();
        createFolder(folderPath);

        final String jsonFilePath =
                folderPath + "\\" + invoice.getDocumentNumber() + "-" + month1 + "-" + outputOfTheYear + ".json";
        creatingJsonFIle(json, jsonFilePath);

    }

    private static void createFolder(final String folderPath) {
        final File creatingFolders = new File(folderPath);
        final boolean bool2 = creatingFolders.mkdirs();
    }

    private static void creatingJsonFIle(final String json, final String jsonFilePath) {
        final File creatingFiles = new File(jsonFilePath);
        try (final PrintWriter out = new PrintWriter(new FileWriter(jsonFilePath))) {
            final boolean newFile = creatingFiles.createNewFile();
            out.write(json);
        } catch (DateTimeParseException | IOException e) {
            throw new RuntimeException();
        }
    }


    private static final class LocalDateAdapter extends TypeAdapter<LocalDateTime> {

        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
            final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            final ZonedDateTime gmt = localDate.atZone(ZoneId.of("Z"));
            final String formattedLD = gmt.format(formatter);
            jsonWriter.value(formattedLD);
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }
}
