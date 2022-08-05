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

    public CSVPricesReader(String path) {
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
        File directoryPath = new File(path);
        String contents[] = directoryPath.list();
        for (String fileName : contents) {
            if (fileName.startsWith("prices-")) {
                try (CSVReader reader = new CSVReader(new java.io.FileReader(path + "\\" + fileName))) {
                    String[] arr = fileName.split("prices.");
                    String[] arr2 = arr[1].split(".csv");
                    int numOfPriceList = Integer.parseInt(arr2[0]);
                    while ((line = reader.readNext()) != null) {
                        String product = line[0];
                        LocalDate startDate = LocalDate.parse(line[1]);
                        LocalDate endDate = LocalDate.parse(line[2]);
                        BigDecimal value = new BigDecimal(line[3]);
                        if (priceCollection.get(numOfPriceList) == null) {
                            prices.add(new Price(product, startDate, endDate, value));
                            priceCollection.put(numOfPriceList, prices);
                        } else {
                            priceCollection.get(numOfPriceList).add(new Price(product, startDate, endDate, value));
                        }
                    }
                } catch (CsvValidationException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return priceCollection;
    }
}