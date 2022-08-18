package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvUserReader implements UsersReader {
    private final String path;

    public CsvUserReader(String path) {
        this.path = path;
    }

    String[] line;
    int counter = 0;

    @Override
    public Map<String, User> read() {
        Map<String, User> userMap = new LinkedHashMap<>();
        List<User> userList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new java.io.FileReader(path + "\\users.csv"))) {
            while ((line = reader.readNext()) != null) {
                CsvPricesReader price = new CsvPricesReader(path, Integer.parseInt(line[2]));
                List<Price> priceList = price.read();
                userList.add(new User(line[0], line[1], Integer.parseInt(line[2]), priceList));
                userMap.put(line[1], userList.get(counter));
                counter++;
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return userMap;
    }
}
