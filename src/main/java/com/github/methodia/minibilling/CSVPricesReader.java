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

    @Override
    public Map<String, Collection<Price>> read() {
        String[] line;
        Map<String, Collection<Price>> priceCollection = new HashMap<>();

        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {

            while ((line = reader.readNext()) != null) {
                String product = line[0];
                LocalDate startDate = LocalDate.parse(line[1]);
                LocalDate endDate = LocalDate.parse(line[2]);
                BigDecimal value = new BigDecimal(line[3]);
                Collection<Price> prices =new LinkedList<>();
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
