package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class PriceReader {
    final String pricePath ="src\\test\\resources\\sample1\\input\\prices-1.csv";
    public Map<String, Price> read(String csvFile) {

        Map<String, Price> informationForPrices = new HashMap<>(); //hashMap -> key(gas) -> Price.class
        String line = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(pricePath));
            while ((line = br.readLine()) != null) { //read all lines from br

                String[] price = line.split(",");

                String product = price[0];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate start = LocalDate.parse(price[1], formatter);
                LocalDate end = LocalDate.parse(price[2], formatter);

                double price1 = Double.parseDouble(price[3]);

                informationForPrices.put(product, new Price(product, start, end, price1));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return informationForPrices; //map
    }
}
