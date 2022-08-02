package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class CSVUserReader implements UsersReader{
    final String path;
    public CSVUserReader(String path) {
        this.path = path;
    }
//    CSVPricesReader pricesReader = new CSVPricesReader("C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv");
//    List<Price> priceArrayList =  pricesReader.getPrices();
//
   static Map<String, List<User>> userMap = new LinkedHashMap<>();
    static List<User> userList = new ArrayList<>();

    public static List<User> getUserList() {
        return userList;
    }

    public static Map<String, List<User>> getUserMap() {
        return userMap;
    }

    @Override
    public List<User> read() {
        List<Price> priceList = CSVPricesReader.getPrices();
        String[] line;


        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {
            while ((line = reader.readNext()) != null) {
//                ArrayList<Price> prices = new ArrayList<>();
//                prices.add(line[3]);
                userList.add(new User(line[0], line[1], priceList));
                userMap.put(line[0], userList );
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}
