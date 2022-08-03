package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

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
    Map<String,List<Price>> result = new HashMap<>();
    static ArrayList<Price>  priceList = new ArrayList<>();
    public static ArrayList<Price> getPriceList() {
        return priceList;
    }


    public Map<String, List<Price>> getResult() {
        return result;
    }

    @Override
    public Map<String, List<Price>> read(String path) {
        String[] lineInArray;



        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {

            while ((lineInArray = reader.readNext()) != null) {
                String stringBeginningDate = lineInArray[1];
                String stringEndDate = lineInArray[2];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate start = LocalDate.parse(stringBeginningDate, formatter);
                LocalDate end = LocalDate.parse(stringEndDate, formatter);
                priceList.add(new Price(lineInArray[0], start, end, new BigDecimal(lineInArray[3])));
                result.put(lineInArray[0],priceList);
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}


