package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVUserReader implements UsersReader {
    static Map<String, User> userMap = new LinkedHashMap<>();
    static List<User> userList = new ArrayList<>();

    public static List<User> getUserList() {
        return userList;
    }

    public static Map<String, User> getUserMap() {
        return userMap;
    }


    @Override
    public List<User> read(String path) {

        String[] line;
        int counter = 0;
        try (CSVReader reader = new CSVReader(new java.io.FileReader(path + "\\users.csv"))) {
            while ((line = reader.readNext()) != null) {
                CSVPricesReader price = new CSVPricesReader();
                List<Price> priceList = price.read(Integer.parseInt(line[2]), path);

                userList.add(new User(line[0], line[1],priceList, Integer.parseInt(line[2])));
                userMap.put(line[1], userList.get(counter));
                counter++;

            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}