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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class SaveInvoice {
    public static void savingFiles(String outputPath, String dateToReporting, Invoice invoice, User user) throws ParseException, IOException {

        LocalDate borderTime = parse(dateToReporting);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new SaveInvoice.LocalDateAdapter()).create();
        String json = gson.toJson(invoice);


        String month = borderTime.getMonth().getDisplayName(TextStyle.FULL, new Locale("bg"));
        String month1 = month.substring(0, 1).toUpperCase() + month.substring(1);
        int outputOfTheYear = borderTime.getYear() % 100;
        String folderPath = outputPath + "\\" + user.getName() + "-" + user.getRef();
        File creatingFolders = new File(folderPath);
        boolean bool2 = creatingFolders.mkdirs();

        String jsonFilePath = folderPath + "\\" + invoice.getDocumentNum() + "-" + month1 + "-" + outputOfTheYear + ".json";
        File creatingFiles = new File(jsonFilePath);
        creatingFiles.createNewFile();
        try (PrintWriter out = new PrintWriter(new FileWriter(jsonFilePath))) {
            out.write(json.toString());
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    private static final class LocalDateAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
            jsonWriter.value(localDate.toString());
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    public static LocalDate parse(String date) {
        DateTimeFormatter formatterBorderTime = new DateTimeFormatterBuilder()
                .appendPattern("yy-MM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 31)
                .toFormatter();
        LocalDate borderTime = LocalDate.parse(date, formatterBorderTime);
        return borderTime;
    }
}
