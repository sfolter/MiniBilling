package com.github.methodia.minibilling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.lang.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String outputPath = scanner.nextLine();
        //    String dateToReporting=scanner.nextLine();
        //    DateTimeFormatter endDateFormatter=DateTimeFormatter.ofPattern("yy-MM" );
        //  LocalDate localDate=LocalDate.parse(dateToReporting,endDateFormatter);
        Path resourceDirectory = Paths.get("src", "test", "resources", "sample1", "input");
        final String readingsPath = resourceDirectory + "\\" + "readings.csv";

        String reportTime = "";
        final UsersFileReading usersFileRead = new UsersFileReading();
        final PricesFileReader pricesFileRead = new PricesFileReader();
        final ReadingsFileReader readingsRead = new ReadingsFileReader(readingsPath);

        ArrayList<User> users = usersFileRead.parseToArrayList();
        Map<String, List<Readings>> readings = readingsRead.parseToMap();
        int i=1;
        for (User user : users) {

            OutputClass outputClass = new OutputClass();
            List<Readings> readingsForUser = readings.get(user.getReferentNumber());


            final String pricesReadingPath = resourceDirectory + "\\" + "prices-" + user.getNumberPricingList() + ".csv";
            Map<String, Prices> prices=pricesFileRead.parseToArrayList(pricesReadingPath);


            //todo namapani gi tuk, veche imash vsichko koeto ti trqbwa -> usera, negovite readings, negovite cenovi list
            Readings firstReadingForUser = readingsForUser.get(0);
            Readings lastReadingForUser = readingsForUser.get(readingsForUser.size() - 1);
            Prices price= prices.get(firstReadingForUser.getProduct());
            double quantity = lastReadingForUser.getMetrics() - firstReadingForUser.getMetrics();
            lastReadingForUser.getMetrics();
            firstReadingForUser.getMetrics();
            prices.get(readings.get("gas"));
            double totalAmount=quantity*price.getPrice();

                        Lines line = new Lines();
                        line.index =1 ;
                        line.quantity = quantity;
                        line.lineStart = String.valueOf(firstReadingForUser.getDate());
                        line.lineEnd = String.valueOf(lastReadingForUser.getDate());
                        line.product = firstReadingForUser.getProduct();
                        line.price = price.getPrice();
                        line.priceList = user.getNumberPricingList();
                        line.amount = quantity * price.getPrice();
                        double totalAmountCounter = 0;
                        outputClass.lines.add(line);



                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        outputClass.documentDate = ZonedDateTime.now().format(formatter);
                        int documentNumber=i*10000;
                        outputClass.documentNumber =String.valueOf(documentNumber);
                        outputClass.consumer = user.getName();
                        outputClass.reference = user.getReferentNumber();
                        outputClass.totalAmount = line.amount;
                        reportTime = String.valueOf(lastReadingForUser.getDate());



            //todo kato e gotovo pravish edin output class (example below), mapvash gotovoto info kum nego
            //finish with outplustClass.jsonify() or toJson() depending on what library you are using
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(outputClass);
            String folderPath = outputPath + "\\" + user.getName() + "-" + user.getReferentNumber();
            File creatingFolders = new File(folderPath);
            boolean bool2 = creatingFolders.mkdirs();

            Date jud = new SimpleDateFormat("yy-MM-dd").parse(reportTime);
            String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
            String[] splitDate = month.split("\\s+");
            String monthInCyrilic = splitDate[1];

            String jsonFilePath = folderPath + "\\" + outputClass.documentNumber + "-" + monthInCyrilic + ".json";
            File creatingFiles = new File(jsonFilePath);
            creatingFiles.createNewFile();
            try (PrintWriter out = new PrintWriter(new FileWriter(jsonFilePath))) {
                out.write(json.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            outputClass = new OutputClass();
            i++;
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

