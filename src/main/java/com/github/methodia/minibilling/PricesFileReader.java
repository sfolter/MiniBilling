package com.github.methodia.minibilling;

import org.joda.time.tz.UTCProvider;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PricesFileReader implements PricesReader {
    String path;

    PricesFileReader(String path) throws IOException {
        this.path = path;
    }

    @Override
    public Map<Integer, List<Price>> read() {


        Map<Integer, List<Price>> result = new LinkedHashMap<>();
        File directoryPath = new File(path);
        String line = "";
        String contents[] = directoryPath.list();
        for (String fileName : contents) {
            if (fileName.startsWith("prices-")) {
                BufferedReader br = null;
                try (FileReader fileReader = new FileReader(path + "\\" + fileName);) {

                    br = new BufferedReader(fileReader);
                    int numberPricingList = getNumberPricingListFromFile(fileName);

                    while ((line= br.readLine()) != null) {
                        String[] pricesData = line.split(",");
                        String product = pricesData[0];
                        String stringBeginningDate = pricesData[1];
                        String stringEndDate = pricesData[2];

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate start = LocalDate.parse(stringBeginningDate, formatter);
                        LocalDate end = LocalDate.parse(stringEndDate, formatter);
                        Double price = Double.parseDouble(pricesData[3]);
                        List<Price> list = new ArrayList<>();
                        if (result.get(numberPricingList) == null) {
                            list.add(new Price(product, start, end, BigDecimal.valueOf(price)));
                            result.put(numberPricingList, list);
                        } else {
                            result.get(numberPricingList).add(new Price(product, start, end, BigDecimal.valueOf(price)));
                        }




                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return result;
    }
    /**Splitting the file name to get the number of pricing list*/
    private int getNumberPricingListFromFile(String fileName) {
        String[] arr = fileName.split("prices.");
        String[] arr2 = arr[1].split(".csv");
        int numberPricingList = Integer.parseInt(arr2[0]);
        return numberPricingList;
    }
}

