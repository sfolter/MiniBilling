package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceReader implements PricesReader{
    public Map<String, List<Prices>> readPricesToMap(String directory) {
        String line = "";
        Map<String, List<Prices>> result = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(directory), "UTF-8"));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                //check kind of product


                String product=data[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate start = LocalDate.parse(data[1], formatter);
                LocalDate end = LocalDate.parse(data[2], formatter);
                double price=Double.parseDouble(data[3]);

                List<Prices> list = new ArrayList<>();

                if (result.get(product) == null) {
                    list.add(new Prices(product, start, end, price));
                    result.put(product, list);
                } else {
                    result.get(product).add(new Prices(product, start, end, price));
                }

            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public  List<Price> read(String directory) {
        String line = "";
       List<Price> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(directory), "UTF-8"));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                //check kind of product


                String product=data[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate start = LocalDate.parse(data[1], formatter);
                LocalDate end = LocalDate.parse(data[2], formatter);
                BigDecimal price= new BigDecimal(data[3]);

                    result.add(new Price(product, start, end, price));
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
