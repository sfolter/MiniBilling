package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class CSVPricesReader implements PricesReader{
    String path;
    public CSVPricesReader(String path) {
        this.path = path;
    }
    Map<String, List<Price>> priceCollection = new HashMap<>();

    public Map<String, List<Price>> getPriceCollection() {
        return priceCollection;
    }

    static List<Price> prices =new LinkedList<>();
    public static List<Price> getPrices() {
        return prices;
    }
    @Override
    public Map<String, List<Price>> read() {

        String[] line;


        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {

            while ((line = reader.readNext()) != null) {
                String product = line[0];
                LocalDate startDate = LocalDate.parse(line[1]);
                LocalDate endDate = LocalDate.parse(line[2]);
                BigDecimal value = new BigDecimal(line[3]);
                if (priceCollection.get(product) == null){
                    prices.add(new Price(product, startDate, endDate, value));
                    priceCollection.put(product, prices);
                }else {
                    priceCollection.get(product).add(new Price(product,startDate,endDate,value));
                }
             }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return priceCollection;
    }
}
