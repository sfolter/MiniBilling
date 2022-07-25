package com.github.methodia.minibilling;

import com.google.gson.Gson;

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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

        String resourceDirectory="C:\\Users\\user\\IdeaProjects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        String directoryClients = resourceDirectory + "users.csv";
        String directoryPrices = resourceDirectory +"prices-1.csv";
        String directoryReport=resourceDirectory +"readings.csv";
//        ClientReader readerFile = new ClientReader();
//        ArrayList<Client> arrayOfClients= readerFile.readClientsToList(directoryClients);
//        for (int i = 0; i < arrayOfClients.size(); i++) {
//            Client client =arrayOfClients.get(i);
//            if (client.getName().equals("Marko Boikov Tsvetkov")) {
//                System.out.println(client.getReferenseNumber());
//            }
//        }
//        PricesReader pricesReader=new PricesReader();
//        ArrayList<Prices> arrayOfPrices=pricesReader.readPricesToList(directoryPrices);
//        for (int i = 0; i < arrayOfPrices.size(); i++) {
//            Prices price=arrayOfPrices.get(i);
//            System.out.println(price);
//        }
//        ReportReader reportReader=new ReportReader();
//        ArrayList<Report> arrayOfReport=reportReader.readReportToList(directoryReport);
//        for (int i = 0; i < arrayOfReport.size(); i++) {
//            Report report=arrayOfReport.get(i);
//            System.out.println(report);
//        }
//          ClientPair clientPair= new ClientPair();
//
//
//        for (int i = 0; i < arrayOfClients.size(); i++) {
//            clientPair.makeReferenceReportMap(arrayOfReport).get(arrayOfClients.get(i).getReferenseNumber());
//            System.out.println(arrayOfClients.get(i).getName()+clientPair.makeReferenceReportMap(arrayOfReport).get(arrayOfClients.get(i).getReferenseNumber()));
//        }
        final ClientReader usersFileRead = new ClientReader();
        final PricesReader pricesFileRead = new PricesReader();
        final ReportReader readingsRead = new ReportReader();

        ArrayList<Client> clients = usersFileRead.readClientsToList(directoryClients);
        Map<String, List<Report>> readings = readingsRead.readReportToList(directoryReport);


        for (Client client : clients) {
            String folderName=resourceDirectory+client.getName()+"-"+client.getReferenseNumber();
            File theDir = new File(folderName);
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            OutputClass outputClass = new OutputClass();
            List<Report> readingsForUser = readings.get(client.getReferenseNumber());
            final String pricesReadingPath = resourceDirectory  + "prices-" + client.getNumberOfPriceList() + ".csv";
            List<Prices> prices=pricesFileRead.readPricesToList(directoryPrices);
            //todo namapani gi tuk, veche imash vsichko koeto ti trqbwa -> usera, negovite readings, negovite cenovi list

            Gson save= new Gson();
            String json = save.toJson(outputClass);
            String fileJson=client.getName()+ ".json";
            try {
                File myObj = new File(fileJson);
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            try (PrintWriter out = new PrintWriter(new FileWriter(folderName)+"\\"+fileJson)) {

                out.write(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
             for (Report reading : readingsForUser) {
                for (Prices price :prices){
                    if(reading.getProduct().equals(price.getProduct())){
                        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        outputClass.documentDate= ZonedDateTime.now().format(formatter);
                        outputClass.documentNumber=client.getReferenseNumber();
                        outputClass.consumer=client.getName();
                        outputClass.reference=client.getReferenseNumber();
                        outputClass.totalAmount=reading.getValue()*price.getPrice();
                        Lines line=new Lines();
                        line.index=client.getNumberOfPriceList();
                        line.quantity=reading.getValue();
                        line.lineStart= String.valueOf(price.getStart());
                        line.lineEnd= String.valueOf(price.getEnd());
                        line.product=reading.getProduct();
                        line.price=price.getPrice();
                        line.priceList=client.getNumberOfPriceList();
                        line.amount=reading.getValue()*price.getPrice();

                        outputClass.lines.add(line);

                    }
                }

            }
            //todo kato e gotovo pravish edin output class (example below), mapvash gotovoto info kum nego
            //finish with outplustClass.jsonify() or toJson() depending on what library you are using

        }


    }

    private static class OutputClass {
        String documentDate;
        String documentNumber;
        String consumer;
        String reference;
        Double totalAmount;

        List<Lines> lines = new ArrayList<>();

    }

    private static class Lines {
        private double index;
        private double quantity;
        private String lineStart;
        private String lineEnd;
        private String product;
        private Double price;
        private int priceList;
        private Double amount;
    }
}
