package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvFilePriceReader implements PricesReader {
    String path = new String();
    Map<String, List<Price>> result = new HashMap<>();
    static ArrayList<Price> priceList = new ArrayList<>();

    public static ArrayList<Price> getPriceList() {
        return priceList;
    }


    public Map<String, List<Price>> getResult() {
        return result;
    }

    @Override
    public Map<String, List<Price>> read(String path) throws IOException {
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
                        if (result.get(numOfPriceList) == null) {
                            priceList.add(new Price(product, startDate, endDate, value));
                            result.put(String.valueOf(numOfPriceList), priceList);
                        } else {
                            result.get(numOfPriceList).add(new Price(product, startDate, endDate, value));
                        }
                        }

                    } catch(CsvValidationException | IOException e){
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }
}

