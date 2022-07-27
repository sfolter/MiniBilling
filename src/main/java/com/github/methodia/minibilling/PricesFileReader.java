package com.github.methodia.minibilling;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PricesFileReader implements FileReading {

    public  Map<String,List<Prices>>parseToArrayList(String path) throws ParseException {
        String line = "";
        Map<String,List<Prices>> result = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] pricesData = line.split(",");
                String product=pricesData[0];
                String stringBeginningDate = pricesData[1];
                String stringEndDate = pricesData[2];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate start = LocalDate.parse(stringBeginningDate, formatter);
                LocalDate end = LocalDate.parse(stringEndDate, formatter);
                Double price = Double.parseDouble(pricesData[3]);
                List<Prices> list = new ArrayList<>();
                if (result.get(product) == null) {
                    list.add(new Prices(product, start,end, price));
                    result.put(product, list);
                } else{
                    result.get(product).add(new Prices( product,start,end, price));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
