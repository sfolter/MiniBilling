package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVUserReader implements UsersReader {
    final String path;

    public CSVUserReader(String path) {
        this.path = path;
    }

    static Map<String, User> userMap = new LinkedHashMap<>();

    public static Map<String, User> getUserMap() {
        return userMap;
    }

    @Override
    public List<User> read() {

        String[] line;
        List<User> userList = new ArrayList<>();
        int counter = 0;
        try (CSVReader reader = new CSVReader(new java.io.FileReader(path + "\\users.csv"))) {
            while ((line = reader.readNext()) != null) {
                CSVPricesReader price = new CSVPricesReader(path, Integer.parseInt(line[2]));
                List<Price> priceList = price.read();
                userList.add(new User(line[0], line[1], Integer.parseInt(line[2]), priceList));
                userMap.put(line[1], userList.get(counter));
                counter++;

            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}
