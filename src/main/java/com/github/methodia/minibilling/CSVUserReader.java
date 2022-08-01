package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVUserReader implements UsersReader{
    final String path;

    public CSVUserReader(String path) {
        this.path = path;
    }

    @Override
    public List<User> read() {
        String[] line;
        List<User> userList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {

            while ((line = reader.readNext()) != null) {
//                userList.add(new User(line[0], line[1], line[2]));
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}
