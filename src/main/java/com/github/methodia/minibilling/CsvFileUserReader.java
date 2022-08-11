package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class CsvFileUserReader implements UsersReader {

    @Override
    public TreeMap<String, User> read(String path) {
        String[] line;
        CsvFilePriceReader price = new CsvFilePriceReader();
        final TreeMap<String, User> userMap = new TreeMap<>();
        final List<User> userList = new ArrayList<>();
        int counter = 0;
        try (CSVReader reader = new CSVReader(new java.io.FileReader(path + "\\users.csv"))) {
            while ((line = reader.readNext()) != null) {
                List<Price> priceList = price.read(Integer.parseInt(line[2]), path);
                userList.add(new User(line[0], line[1], priceList, Integer.parseInt(line[2])));
                userMap.put(line[1], userList.get(counter));
                counter++;
            }
            return userMap;
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
