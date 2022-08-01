package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class CSVReadingsReader implements ReadingsReader{
    String path;
    public CSVReadingsReader(String path) {
        this.path = path;
    }
//new Reading(LocalDateTime.parse(line[0]), new BigDecimal(line[1]), new User(line[2]))
    @Override
    public Collection<Reading> read() {
        User user = null;

        Map<String, List<User>> userMap = new LinkedHashMap<>();
        userMap.put(user.getRef(), new User());
        String[] line;
        List<Reading> readingsList = new ArrayList<Reading>() {
        };

        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {

            while ((line = reader.readNext()) != null) {
                readingsList.add(new Reading(LocalDateTime.parse(line[1]),new BigDecimal(line[3]), (User) userMap.get(line[0]),line[2]));
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return readingsList;
    }
}
