package com.github.methodia.minibilling;

import com.google.gson.Gson;

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class Main {

    public static void main(String[] args) {
//        if (args.length <2) {
//            System.out.println("Doesn't work!");
//          return;
//        }
//         final String yearMouth = args[0];
//        final String billingDataDir=args[1];
//        final File usersFile= new File(billingDataDir+"clients.csv");

        //directories
        String resourceDirectory = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        String directoryClients = resourceDirectory + "users.csv";
        String directoryPrices = resourceDirectory + "prices-1.csv";
        String directoryReport = resourceDirectory + "readings.csv";

        final ClientReader clientReader = new ClientReader();
        final PriceReader pricesReader = new PriceReader();
        final ReportReader reportReader = new ReportReader();

        Bill bill = new Bill();
        ArrayList<Client> clients = clientReader.readClientsToList(directoryClients);
        Map<String, List<Report>> readings = reportReader.readReportForGasToMap(directoryReport);
        Map<String, List<Prices>> prices = pricesReader.readPricesToMap(directoryPrices);

        for (Client client : clients) {
            //create folder
            String folderName = resourceDirectory + client.getName() + "-" + client.getReferenseNumber();
            File theDir = new File(folderName);
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            //class connecting
            List<Report> readingsForUser = readings.get(client.getReferenseNumber());
            Report FirstReportInArray = readings.get(client.getReferenseNumber()).get(0);
            Report LastReportInArray = readings.get(client.getReferenseNumber()).get(readingsForUser.size() - 1);
            final String pricesReadingPath = resourceDirectory + "prices-" + client.getNumberOfPriceList() + ".csv";
            prices.get("gas");

            Line line = new Line();
            line.index = client.getNumberOfPriceList();
            line.quantity = LastReportInArray.getValue() - FirstReportInArray.getValue();
            line.lineStart = String.valueOf(FirstReportInArray.getData());//
            line.lineEnd = String.valueOf(LastReportInArray.getData());//
            line.product = readings.get(client.getReferenseNumber()).get(0).getProduct();
            line.price = prices.get("gas").get(0).getPrice();
            line.priceList = client.getNumberOfPriceList();
            line.amount = line.quantity * prices.get("gas").get(0).getPrice(); //

            bill.lines.add(line);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            bill.setDocumentDate(ZonedDateTime.now().format(formatter));
            bill.documentNumber = bill.getDocumentNumber();
            // System.out.println(client.getName());
            bill.consumer = client.getName();
            bill.reference = client.getReferenseNumber();
            double totalAmountCounter = 0;
            for (int i = 0; i < bill.lines.size(); i++) {

                totalAmountCounter += line.amount;
            }
            bill.totalAmount = totalAmountCounter;


            Gson save = new Gson();
            //json file write
            String json = save.toJson(bill);
            //name of json file
            System.out.println(json);
            //make json file
            String fileJson = client.getName() + ".json";
            try {
                File myObj = new File(folderName + "\\", fileJson);
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    // System.out.println("File already exists.");
                    FileWriter myWriter = new FileWriter(folderName + "\\" + fileJson);
                    myWriter.write(json);
                    myWriter.close();
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            bill = new Bill();
        }


    }
}


