package com.github.methodia.minibilling;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner=new Scanner(System.in);
        String outputPath=scanner.nextLine();
    //    String dateToReporting=scanner.nextLine();
    //    DateTimeFormatter endDateFormatter=DateTimeFormatter.ofPattern("yy-MM" );
      //  LocalDate localDate=LocalDate.parse(dateToReporting,endDateFormatter);
        Path resourceDirectory = Paths.get("src", "test", "resources", "sample1", "input");
        final String readingsPath = resourceDirectory + "\\" + "readings.csv";

        String reportTime="";
        final UsersFileReading usersFileRead = new UsersFileReading();
        final PricesFileReader pricesFileRead = new PricesFileReader();
        final ReadingsFileReader readingsRead = new ReadingsFileReader(readingsPath);

        ArrayList<User> users = usersFileRead.parseToArrayList();
        Map<String, List<Readings>> readings = readingsRead.parseToMap();


        for (User user : users) {
            OutputClass outputClass = new OutputClass();
            List<Readings> readingsForUser = readings.get(user.getReferentNumber());
            final String pricesReadingPath = resourceDirectory + "\\" + "prices-" + user.getNumberPricingList() + ".csv";
            List<Prices> prices=pricesFileRead.parseToArrayList(pricesReadingPath);

            //todo namapani gi tuk, veche imash vsichko koeto ti trqbwa -> usera, negovite readings, negovite cenovi list

            for (Readings reading : readingsForUser) {
                for (Prices price :prices){
                    if(reading.getProduct().equals(price.getProduct())){

                        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        outputClass.documentDate=ZonedDateTime.now().format(formatter);
                        outputClass.documentNumber=user.getReferentNumber();
                        outputClass.consumer=user.getName();
                        outputClass.reference=user.getReferentNumber();
                        outputClass.totalAmount=reading.getMetrics()*price.getPrice();
                        Lines line=new Lines();
                        line.index=user.getNumberPricingList();
                        line.quantity=reading.getMetrics();
                        line.lineStart= String.valueOf(price.getStartingDate());
                        line.lineEnd= String.valueOf(price.getEndDate());
                        line.product=reading.getProduct();
                        line.price=price.getPrice();
                        line.priceList=user.getNumberPricingList();
                        line.amount=reading.getMetrics()*price.getPrice();

                        outputClass.lines.add(line);

                        reportTime=String.valueOf(reading.getDate());
                    }
                }

            }
            //todo kato e gotovo pravish edin output class (example below), mapvash gotovoto info kum nego
            //finish with outplustClass.jsonify() or toJson() depending on what library you are using
            Gson gson = new Gson();
            String json = gson.toJson(outputClass);
            String folderPath=outputPath+"\\"+user.getName() + "-" + user.getReferentNumber();
            File creatingFolders = new File(folderPath);
            boolean bool2 = creatingFolders.mkdirs();
            File creatingFiles= new File(folderPath+"\\"+user.getName()+"-"+user.getReferentNumber());
            creatingFiles.createNewFile();
            Date jud = new SimpleDateFormat("yy-MM-dd").parse(reportTime);
            String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
            System.out.println(month);
         //   Форматът на името за текстовия файл, за фактурата е: {пореден-номер}-{месец}-{година}.json

//            Path jsonFilePath = Paths.get(user.getName() + "-" + user.getReferentNumber());
//            File yourFile = new File(user.getName()+"-"+user.getReferentNumber());
//            yourFile.createNewFile(); // if file already exists will do nothing
//            FileOutputStream oFile = new FileOutputStream(yourFile, false);

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

