package com.github.methodia.minibilling;

import com.google.gson.Gson;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String outputPath = scanner.nextLine();
        String dateToReporting = scanner.nextLine();

        DateTimeFormatter formatterBorderTime = new DateTimeFormatterBuilder()
                .appendPattern("yy-MM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        LocalDate borderTime = LocalDate.parse(dateToReporting, formatterBorderTime);

        //directories
        String resourceDirectory = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        String directoryClients = resourceDirectory + "users.csv";
        String directoryReport = resourceDirectory + "readings.csv";
        String directoryPrices = resourceDirectory + "prices-1.csv";


        final ClientReader clientReader = new ClientReader();
        final PriceReader pricesReader = new PriceReader();
        final ReportReader reportReader = new ReportReader();
        Bill bill = new Bill();

        ArrayList<Client> clients = clientReader.readClientsToList(directoryClients);
        Map<String, List<Report>> readings = reportReader.readReportForGasToMap(directoryReport);

        String reportTime = "";

        for (Client client : clients) {
            //class connecting
            List<Report> readingsForUser = readings.get(client.getReferenseNumber());
            Report firstReportInArray = readingsForUser.get(0);
            Report lastReportInArray = readingsForUser.get(readingsForUser.size() - 1);


            int ReadingForUserDateDay = lastReportInArray.getData().getDayOfMonth();
            Month ReadingForUserDateMonth = lastReportInArray.getData().getMonth();
            int ReadingForUserDateYear = lastReportInArray.getData().getYear();
            LocalDate ReadingForUserInLocalDate = LocalDate.of(ReadingForUserDateYear, ReadingForUserDateMonth, ReadingForUserDateDay);
            String pricesReadingPath = resourceDirectory + "prices-" + client.getNumberOfPriceList() + ".csv";
            Map<String, List<Prices>> prices = pricesReader.readPricesToMap(pricesReadingPath);
            List<Prices> priceForUser = prices.get("gas");
            if (ReadingForUserInLocalDate.isBefore(borderTime)) {

                Line line = new Line();
                line.index = client.getNumberOfPriceList();
                line.quantity = lastReportInArray.getValue() - firstReportInArray.getValue();
                line.lineStart = String.valueOf(firstReportInArray.getData());//
                line.lineEnd = String.valueOf(lastReportInArray.getData());//
                line.product = readings.get(client.getReferenseNumber()).get(0).getProduct();
                line.price = prices.get("gas").get(0).getPrice();
                line.priceList = client.getNumberOfPriceList();
                line.amount = line.quantity * prices.get("gas").get(0).getPrice(); //

                bill.lines.add(line);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                bill.setDocumentDate(ZonedDateTime.now().format(formatter));
                bill.documentNumber = Bill.getDocumentNumber();
                bill.consumer = client.getName();
                bill.reference = client.getReferenseNumber();
                double totalAmountCounter = 0;
                for (int j = 0; j < bill.lines.size(); j++) {
                    totalAmountCounter += line.amount;
                }
                bill.totalAmount = totalAmountCounter;

            }
            reportTime = String.valueOf(lastReportInArray.getData());


            Gson save = new Gson();
            String json = save.toJson(bill);
            try {
                Date jud = new SimpleDateFormat("yy-MM-dd").parse(reportTime);
                String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
                String[] splitDate = month.split("\\s+");
                String monthInCyrilic = splitDate[1];
                int outputOfTheYear = ReadingForUserDateYear % 100;

                String folderPath = resourceDirectory + client.getName() + "-" + client.getReferenseNumber();
                String jsonFilePath = folderPath + "\\" + bill.documentNumber + "-" + monthInCyrilic + "-" + outputOfTheYear + ".json";

                //make folder
                File theDir = new File(folderPath);
                theDir.mkdirs();

                //make json
                FileWriter myWriter = new FileWriter(jsonFilePath);
                myWriter.write(json);
                myWriter.close();

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            bill = new Bill();
        }
    }
}


