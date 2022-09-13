package com.github.methodia.minibilling;

import com.github.methodia.minibilling.connection.DataBaseConnect;


import com.github.methodia.minibilling.dataSave.JsonFileCreator;
import com.github.methodia.minibilling.jdbcQueries.ReadingsDataBaseRead;
import com.github.methodia.minibilling.jdbcQueries.UserDataBaseRead;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException {
        String dateReportingTo = args[0];
        LocalDateTime dateReportingToLDT = convertingBorderTimeIntoLDT(dateReportingTo);
        String inputPath = args[1];
        String outputPath = args[2];
        Connection dataBaseConnector=DataBaseConnect.createConnection("postgres","123321123","postgres");
        List<User> userList = UserDataBaseRead.userReader(dataBaseConnector);
        List<Reading> readings = ReadingsDataBaseRead.readingsReader(dataBaseConnector, userList);
        //        ReadingsFileReader readingsFR = new ReadingsFileReader(inputPath);
//        readingsFR.read();

//        Collection<Reading> allReadings = readingsFR.read().stream()
//                .sorted(Comparator.comparing(Reading::time))
//                .toList();


        MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        CurrencyConverter currencyConverter = new CurrencyRate();
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator(currencyConverter);

        // Looping through users and sort them by their referent number
        //UserFileReader userFR = new UserFileReader(inputPath);
        //List<User> users = userFR.read().stream().sorted(Comparator.comparing(User::getRef)).toList();
        //            Looping through every user and check their Measurements, and based on them and price, the algorithm below
        //            creates invoices and based on price periods, create individual lines if there is a change of prices.
        List<VatPercentages> vatPercentages=new ArrayList<>();
        vatPercentages.add(new VatPercentages(new BigDecimal("60"),new BigDecimal("20")));
        vatPercentages.add(new VatPercentages(new BigDecimal("40"),new BigDecimal("10")));

        for (User user : userList) {
            List<Reading> userReadings=readings.stream()
                    .filter(reading -> reading.getUser().getRef().equals(user.getRef())).toList();
            // List<Reading> userReadings = allReadings.stream()
//                    .filter(reading -> reading.user().getRef()
//                            .equals(user.getRef())).toList();
            Collection<Measurement> userMeasurements = measurementGenerator.generate(user, userReadings);

            Invoice invoice = invoiceGenerator.generate(user, userMeasurements, dateReportingToLDT,
                    vatPercentages);

            new JsonFileCreator(invoice, outputPath, user).save();

        }
    }



//    private static void saveToFile(Invoice invoice, String outputPath, User user) throws IOException {
//        //Parsing Invoice class into Json format using GSON library
//        Gson gson = new GsonBuilder().setPrettyPrinting()
//                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();
//        String json = gson.toJson(invoice);
//
//        //Sorting InvoiceLine list as we would like to get the last invoice end date
//        List<InvoiceLine> invoiceLinesList = invoice.getLines().stream()
//                .sorted(Comparator.comparing(InvoiceLine::getEnd).reversed()).toList();
//        if (!invoiceLinesList.isEmpty()) {
//            LocalDate lastInvoiceDate = invoiceLinesList.get(0).getEnd().toLocalDate();
//            String monthToBulgarian = getMonthOfLastInvoiceToBulgarian(lastInvoiceDate);
//            int outputOfTheYear = lastInvoiceDate.getYear() % 100;
//
//            //Creating folder in the following path
//            String folderPath = outputPath + "\\" + user.getName() + "-" + user.getRef();
//            createFolder(folderPath);
//
//
//            String jsonFilePath =
//                    folderPath + "\\" + invoice.getDocNumber() + "-" + monthToBulgarian + "-" + outputOfTheYear
//                            + ".json";
//            creatingJsonFIle(json, jsonFilePath);
//
//        } else {
//            System.out.println("There is no invoices");
//        }
//
//    }
//
//    /**
//     * Checking if JsonFile by the exists with the format documentNumber-Month(translated to bulgarian language),
//     * and the last two numbers of the year on Last reporting date, in case not,
//     * the following algorithm will create the JsonFiles in the format below.
//     */
//    private static void creatingJsonFIle(String json, String jsonFilePath) throws IOException {
//        File creatingFiles = new File(jsonFilePath);
//        boolean newFile = creatingFiles.createNewFile();
//        try (PrintWriter out = new PrintWriter(new FileWriter(jsonFilePath))) {
//            out.write(json);
//        } catch (DateTimeParseException e) {
//            throw new IOException(e);
//        }
//    }
//
//    /**
//     * Checking if folders by the following format - username-referent number, in case the folders doesn't exist
//     * the following algorithm will create the folders in the format below.
//     */
//    private static void createFolder(String folderPath) {
//        File creatingFolders = new File(folderPath);
//        boolean mkdirs = creatingFolders.mkdirs();
//
//    }

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

}



