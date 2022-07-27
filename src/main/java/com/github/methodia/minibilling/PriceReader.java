package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceReader {
    public Map<String,Price>read(String csvFile) throws IOException {

        Map<String, Price> informationForPrices = new HashMap<>();
        String line = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("src\\test\\resources\\sample1\\input\\prices-1.csv"));
            while ((line = br.readLine()) != null) {
                String[] price = line.split(",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate start = LocalDate.parse(price[1], formatter);
                LocalDate end = LocalDate.parse(price[2], formatter);
                List<Price> list = new ArrayList<>();
                String product=price[0];
                informationForPrices.put(product, new Price(product,start,end,Double.parseDouble(price[3])));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return informationForPrices;
    }
}
