package com.github.methodia.minibilling;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {

        Path resourceDirectory = Paths.get("src", "test", "resources", "sample1", "input");
        final String readingsPath = resourceDirectory + "\\" + "readings.csv";


        final UsersFileReading usersFileRead = new UsersFileReading();
        final PricesFileReader pricesFileRead = new PricesFileReader();
        final ReadingsFileReader readingsRead = new ReadingsFileReader(readingsPath);

        ArrayList<User> users = usersFileRead.parseToMap();
        Map<String, List<Readings>> readings = readingsRead.readToArrayList();


        for (User user : users) {
            OutputClass outputClass = new OutputClass();
            List<Readings> readingsForUser = readings.get(user.referentNumber);
            final String pricesReadingPath = resourceDirectory + "\\" + "prices-" + user.getNumberPricingList() + ".csv";
            List<Prices> prices=pricesFileRead.readToArrayList(pricesReadingPath);
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

