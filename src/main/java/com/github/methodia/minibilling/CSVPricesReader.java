package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class CSVPricesReader implements PricesReader {
    String path;
    User user;

    public CSVPricesReader(User user, String path) {
        this.user = user;
        this.path = path;
    }

    static Map<Integer, List<Price>> priceCollection = new HashMap<>();

    public static Map<Integer, List<Price>> getPriceCollection() {
        return priceCollection;
    }

    static List<Price> prices = new LinkedList<>();

    public static List<Price> getPrices() {
        return prices;
    }

    @Override
    public Map<Integer, List<Price>> read() {
        String[] line;
        int priceListNumber = user.getPriceList();
        String directory=path+"prices-"+ user.getPriceList() +".csv";

        try (CSVReader reader = new CSVReader(new java.io.FileReader(directory))) {
            while ((line = reader.readNext()) != null) {
                String product = line[0];
                LocalDate startDate = LocalDate.parse(line[1]);
                LocalDate endDate = LocalDate.parse(line[2]);
                BigDecimal value = new BigDecimal(line[3]);
                if (priceCollection.get(priceListNumber) == null) {
                    prices.add(new Price(product, startDate, endDate, value));
                    priceCollection.put((user.getPriceList()), prices);
                } else {
                    priceCollection.get(priceListNumber).add(new Price(product, startDate, endDate, value));
                }
            }

        } catch(CsvValidationException | IOException e){
            throw new RuntimeException(e);
        }
        return priceCollection;
    }
}