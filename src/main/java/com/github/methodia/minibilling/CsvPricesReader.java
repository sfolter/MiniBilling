package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class CsvPricesReader implements PricesReader {
    private final String path;

    private final int priceListNumber;

    public CsvPricesReader(String path, int priceListNumber) {
        this.path = path;
        this.priceListNumber = priceListNumber;
    }

    String[] line;

    @Override
    public List<Price> read() {
        List<Price> prices = new LinkedList<>();
        String directory = path + "prices-" + priceListNumber + ".csv";
        try (CSVReader reader = new CSVReader(new java.io.FileReader(directory))) {
            while ((line = reader.readNext()) != null) {
                String product = line[0];
                LocalDate startDate = LocalDate.parse(line[1]);
                LocalDate endDate = LocalDate.parse(line[2]);
                BigDecimal value = new BigDecimal(line[3]);
                prices.add(new Price(product, startDate, endDate, value));
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return prices;
    }
}