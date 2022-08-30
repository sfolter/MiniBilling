package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvUserReader implements UsersReader {

    private final String path;

    public CsvUserReader(final String path) {
        this.path = path;
    }

    @Override
    public Map<String, User> read() {
        final Map<String, User> userMap = new LinkedHashMap<>();
        final List<User> userList = new ArrayList<>();
        String[] line;
        int counter = 0;
        try (final CSVReader reader = new CSVReader(new FileReader(path + "\\users.csv"))) {
            while (null != (line = reader.readNext())) {
                final CsvPricesReader price = new CsvPricesReader(path, Integer.parseInt(line[2]));
                final List<Price> priceList = price.read();
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
