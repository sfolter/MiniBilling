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
    final String path = new String();

    final static Map<String, List<Price>> result = new HashMap<>();
    final   ArrayList<Price> priceList = new ArrayList<>();

    public final  ArrayList<Price> getPriceList() {
        return priceList;
    }


    public static Map<String, List<Price>> getResult() {
        return result;
    }

    @Override
    public List<Price> read(int priceListNum, String path) throws IOException {
        String[] line;

        String directory=path+"prices-"+ priceListNum +".csv";

                try (CSVReader reader = new CSVReader(new java.io.FileReader(directory))) {
                    while ((line = reader.readNext()) != null) {
                        String product = line[0];
                        LocalDate startDate = LocalDate.parse(line[1]);
                        LocalDate endDate = LocalDate.parse(line[2]);
                        BigDecimal value = new BigDecimal(line[3]);

                            priceList.add(new Price(product, startDate, endDate, value));
                            result.put(String.valueOf(priceListNum), priceList);

                        }

                    } catch(CsvValidationException | IOException e){
                    throw new RuntimeException(e);
                }
        return priceList;
    }
}

