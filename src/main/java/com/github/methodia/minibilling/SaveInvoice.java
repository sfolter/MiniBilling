package com.github.methodia.minibilling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;

public class SaveInvoice {
    public static void saveToFile(Invoice invoice, User user, String outputPath, String dateToReporting) throws ParseException, IOException {

        LocalDate borderTime = Formatter.parseBorder(dateToReporting);

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new SaveInvoice.LocalDateAdapter()).create();
        String json = gson.toJson(invoice);

        String month = borderTime.getMonth().getDisplayName(TextStyle.FULL, new Locale("bg"));
        String month1 = month.substring(0, 1).toUpperCase() + month.substring(1);
        int outputOfTheYear = borderTime.getYear() % 100;

        String folderPath = outputPath + "\\" + user.getName() + "-" + user.getRef();
        createFolder(folderPath);

        String jsonFilePath = folderPath + "\\" + invoice.getDocumentNumber() + "-" + month1 + "-" + outputOfTheYear + ".json";
        creatingJsonFIle(json, jsonFilePath);
    }

    private static void createFolder(String folderPath) {
        File creatingFolders = new File(folderPath);
        boolean bool2 = creatingFolders.mkdirs();
    }

    private static void creatingJsonFIle(String json, String jsonFilePath) {
        File creatingFiles = new File(jsonFilePath);
        try (PrintWriter out = new PrintWriter(new FileWriter(jsonFilePath))) {
            boolean newFile = creatingFiles.createNewFile();
            out.write(json);
        } catch (DateTimeParseException | IOException e) {
            throw new RuntimeException();
        }
    }

    private static final class LocalDateAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            ZonedDateTime gmt = localDate.atZone(ZoneId.of("Z"));
            String formattedLD = gmt.format(formatter);
            jsonWriter.value(formattedLD);
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }
}
