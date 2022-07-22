package com.github.methodia.minibilling;

import java.util.ArrayList;

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
//        final File usersFile= new File(billingDataDir+"users.csv");

        String directoryClients = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\users.csv";
        String directoryPrices = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv";
        String directoryReport="C:\\Users\\user\\IdeaProjects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv";
        ClientReader readerFile = new ClientReader();
        ArrayList<Client> arrayOfClients= readerFile.readClientsToList(directoryClients);
        for (int i = 0; i < arrayOfClients.size(); i++) {
            Client client =arrayOfClients.get(i);
            if (client.getName().equals("Marko Boikov Tsvetkov")) {
                System.out.println(client.getReferenseNumber());
            }
        }
        PricesReader pricesReader=new PricesReader();
        ArrayList<Prices> arrayOfPrices=pricesReader.readPricesToList(directoryPrices);
        for (int i = 0; i < arrayOfPrices.size(); i++) {
            Prices price=arrayOfPrices.get(i);
            System.out.println(price);
        }
        ReportReader reportReader=new ReportReader();
        ArrayList<Report> arrayOfReport=reportReader.readReportToList(directoryReport);
        for (int i = 0; i < arrayOfReport.size(); i++) {
            Report report=arrayOfReport.get(i);
            System.out.println(report);
        }


    }
}
