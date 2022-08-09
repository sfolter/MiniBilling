package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class CSVPricesReader implements PricesReader {

    final String path = new String();
    static int priceCount = 0;
    final static Map<String, List<Price>> result = new HashMap<>();
    final static ArrayList<Price> priceList = new ArrayList<>();

    public static final  ArrayList<Price> getPriceList() {
        return priceList;
    }


    public static Map<String, List<Price>> getResult() {
        return result;
    }
   public static Integer getCountPrice(){
        return priceCount/3;
   }

    @Override
    public List<Price> read(int priceListNum, String path) throws IOException {
        String[] line;

        //String directory=path+"prices-1"+ priceListNum +".csv";
        String directory=path+"prices-1.csv";

        try (CSVReader reader = new CSVReader(new java.io.FileReader(directory))) {
            while ((line = reader.readNext()) != null) {
                String product = line[0];
                LocalDate startDate = LocalDate.parse(line[1]);
                LocalDate endDate = LocalDate.parse(line[2]);
                BigDecimal value = new BigDecimal(line[3]);

                priceList.add(new Price(product, startDate, endDate, value));
                result.put(String.valueOf(priceListNum), priceList);
                priceCount++;
            }

        } catch(CsvValidationException | IOException e){
            throw new RuntimeException(e);
        }
        return priceList;
    }
}

