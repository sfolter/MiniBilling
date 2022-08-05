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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.*;

public class Main {

    public static void main(String[] args) {

//        Scanner scanner = new Scanner(System.in);
//        String resourceDirector = args[1];
//        String outputDirector = args[2];
//        String dateToReporting = args[0];

        String resourceDirector = "C:\\Users\\vikto\\IdeaProjects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample2\\input\\";
        String outputDirector = "C:\\Users\\vikto\\IdeaProjects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample2\\input\\";
        String dateToReporting = "21-03";
        DateTimeFormatter formatterBorderTime = new DateTimeFormatterBuilder()
                .appendPattern("yy-MM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        LocalDate borderTime = LocalDate.parse(dateToReporting, formatterBorderTime);

        //directories
        String resourceDirectory = "C:\\Users\\vikto\\IdeaProjects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";

        final UserReader userReader = new UserReader();
        final PriceReader pricesReader = new PriceReader();
        final ReadingReader readingReader = new ReadingReader();
        List<User> userList = userReader.readToList(resourceDirector);
        List<Reading> readingCollection = readingReader.read(resourceDirector);

        for (User user : userList) {

            List<Price> priceList = pricesReader.read(resourceDirector, user.getPriceListNumber());
            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readingCollection);
            Collection<Measurement> measurementCollection = measurementGenerator.generate();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurementCollection, priceList);
            Invoice invoice = invoiceGenerator.generate();
            System.out.println(invoice);
            try {
                savingFiles(outputDirector, borderTime, invoice, user);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void savingFiles(String outputPath, LocalDate borderTime, Invoice invoice, User user) throws ParseException, IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();
        String json = gson.toJson(invoice);


        String month = borderTime.getMonth().getDisplayName(TextStyle.FULL, new Locale("bg"));
        String month1 = month.substring(0, 1).toUpperCase() +month.substring(1);
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
}


