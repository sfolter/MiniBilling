package com.github.methodia.minibilling;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        //WELCOME TO MINI BILLING!
        //csv file to read


        String csvFileUsers = "C:\\Users\\user\\MiniBilling1\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv";
         String csvFileReports = "C:\\Users\\user\\MiniBilling1\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv";
         String csvFilePrices ="C:\\Users\\user\\MiniBilling1\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv";

         CSVUserReader csvUserReader = new CSVUserReader();
       CSVReportsReader csvReportsReader = new CSVReportsReader();
        CSVPriceReader csvPriceReader = new CSVPriceReader();

        //   csvReader.read(csvFile);
        csvUserReader.read(csvFileUsers);
       csvReportsReader.read(csvFileReports);
        csvPriceReader.read(csvFilePrices);


        for (int i = 0; i < csvUserReader.read(csvFileUsers).size(); i++) {
            Users user = csvUserReader.read(csvFileUsers).get(i);
            System.out.println(user);
        }
        for (int i = 0; i < csvReportsReader.read(csvFileReports).size(); i++) {
            Reports reports = csvReportsReader.read(csvFileReports).get(i);
            System.out.println(reports);
        }
        for (int i = 0; i < csvPriceReader.read(csvFilePrices).size(); i++) {
            Prices price = csvPriceReader.read(csvFilePrices).get(i);
            System.out.println(price);
        }








//if(args.length < 2){
//    System.out.println("");
//    return;
//
//}
//final String monthYear = args[0];
//final String billingDate = args[1];
//final  File usersFIle = new File(billingDate + "users.csv");
//if(!usersFIle.exists()){
//    System.out.println();
//    return;
//}
    }

}
