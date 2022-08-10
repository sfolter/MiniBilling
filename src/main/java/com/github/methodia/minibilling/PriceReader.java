package com.github.methodia.minibilling;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class PriceReader implements PricesReader {
    String path;

    public PriceReader(String path) {
        this.path = path;
    }

    @Override
    public Map<Integer, List<Price>> read() {

        File directory = new File(path);
        String[] filesList = directory.list();

        Map<Integer, List<Price>> informationForPrices = new LinkedHashMap<>();

        assert filesList != null;
        for (String file : filesList) {

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "\\" + file)))) {
                if (file.contains("prices-")) {

                    List<Price> prices = br.lines()
                            .map(l -> l.split(","))
                            .map(this::createPrice).toList();

                    String[] arr2 = file.split("[\\\\a-z-.]+");
                    int numPricingList = Integer.parseInt(arr2[1]);

                    informationForPrices.put(numPricingList, prices);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return informationForPrices;

    }
    private Price createPrice(String[] price) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String product = price[0];
        LocalDate startDate = LocalDate.parse(price[1], formatter);
        LocalDate endDate = LocalDate.parse(price[2], formatter);
        double price1 = Double.parseDouble(price[3]);

        return new Price(product, startDate, endDate, BigDecimal.valueOf(price1));
    }
}



