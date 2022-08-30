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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        String apiKey = "xSjvqqK3AgQPI3PPKGCiDrC2q06e41xt";
        CurrencyConverter currencyConverter = new CurrencyConverter(apiKey);

        String dateReportingTo = "21-03";
        LocalDateTime dateReportingToLDT = convertingBorderTimeIntoLDT(dateReportingTo);
        String inputPath = "C:\\Users\\user\\Desktop\\29.07\\MiniBilling\\src\\test\\resources\\sample2\\input";
        String outputPath = "C:\\Users\\user\\Desktop\\29.07\\MiniBilling\\src\\test\\resources\\sample2\\output";

        ReadingsFileReader readingsFR = new ReadingsFileReader(inputPath);
        readingsFR.read();
        Collection<Reading> allReadings = readingsFR.read().stream()
                .sorted(Comparator.comparing(Reading::getTime))
                .toList();

        MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator(currencyConverter);
        VatPercentageGenerator vatPercentageGenerator = new VatPercentageGenerator();

        // Looping through users and sort them by their referent number
        UserFileReader userFR = new UserFileReader(inputPath);
        List<User> users = userFR.read().stream().sorted(Comparator.comparing(User::getRef)).toList();
        //            Looping through every user and check their Measurements, and based on them and price, the algorithm below
        //            creates invoices and based on price periods, create individual lines if there is a change of prices.
        for (User user : users) {
            List<Reading> userReadings = allReadings.stream()
                    .filter(reading -> reading.getUser().getRef()
                            .equals(user.getRef())).toList();
            Collection<Measurement> userMeasurements = measurementGenerator.generate(user, userReadings);
            Invoice invoice = invoiceGenerator.generate(user, userMeasurements, dateReportingToLDT,
                    vatPercentageGenerator.percentageGenerate(new BigDecimal("60"), new BigDecimal("40")));
            saveToFile(invoice, outputPath, user);

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

    private static void saveToFile(Invoice invoice, String outputPath, User user) throws IOException {

        //Parsing Invoice class into Json format using GSON library
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();
        String json = gson.toJson(invoice);

        //Sorting InvoiceLine list as we would like to get the last invoice end date

        List<InvoiceLine> invoiceLinesList = invoice.getLines().stream()
                .sorted(Comparator.comparing(InvoiceLine::getEnd).reversed()).toList();
        if (!invoiceLinesList.isEmpty()) {
            LocalDate lastInvoiceDate = invoiceLinesList.get(0).getEnd().toLocalDate();
            String monthToBulgarian = getMonthOfLastInvoiceToBulgarian(lastInvoiceDate);
            int outputOfTheYear = lastInvoiceDate.getYear() % 100;

            //Creating folder in the following path
            String folderPath = outputPath + "\\" + user.getName() + "-" + user.getRef();
            createFolder(folderPath);


            String jsonFilePath =
                    folderPath + "\\" + invoice.getDocNumber() + "-" + monthToBulgarian + "-" + outputOfTheYear
                            + ".json";
            creatingJsonFIle(json, jsonFilePath);

        } else {
            System.out.println("There is no invoices");
        }

    }

    /**
     * Checking if JsonFile by the exists with the format documentNumber-Month(translated to bulgarian language),
     * and the last two numbers of the year on Last reporting date, in case not,
     * the following algorithm will create the JsonFiles in the format below.
     */
    private static void creatingJsonFIle(String json, String jsonFilePath) throws IOException {
        File creatingFiles = new File(jsonFilePath);
        creatingFiles.createNewFile();
        try (PrintWriter out = new PrintWriter(new FileWriter(jsonFilePath))) {
            out.write(json);
        } catch (DateTimeParseException e) {
            throw new IOException(e);
        }
    }

    /**
     * Checking if folders by the following format - username-referent number, in case the folders doesn't exist
     * the following algorithm will create the folders in the format below.
     */
    private static void createFolder(String folderPath) {
        File creatingFolders = new File(folderPath);
        boolean bool2 = creatingFolders.mkdirs();
    }

    /**
     * Converting the border time we would like to report as it is in format "yy-MM,
     * into LocalDateTime as the zone is
     * GMT
     */
    private static LocalDateTime convertingBorderTimeIntoLDT(String borderDateString) {
        final YearMonth yearMonth = YearMonth.parse(borderDateString, DateTimeFormatter.ofPattern("yy-MM"));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        ZonedDateTime borderTimeZDT = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.of("Z"));
        return LocalDateTime.parse(String.valueOf(borderTimeZDT), formatter);
    }


    /**
     * Converting the Month of last Invoice to Bulgarian language String
     */
    private static String getMonthOfLastInvoiceToBulgarian(LocalDate lastInvoiceDate) {
        String lastInvoiceDateInBG = lastInvoiceDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("Bg"));
        return lastInvoiceDateInBG.substring(0, 1).toUpperCase() + lastInvoiceDateInBG.substring(1);
    }
}



