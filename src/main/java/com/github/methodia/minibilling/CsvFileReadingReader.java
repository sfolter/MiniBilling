package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CsvFileReadingReader implements ReadingsReader {
    final static List<Reading> readingsList = new ArrayList<Reading>() ;

    public static List<Reading> getReadingsList() {
        return readingsList;
    }

    @Override
    public Collection<Reading> read(String path) {
        String[] line;


        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {

            while ((line = reader.readNext()) != null) {
                Map<String, User> userMap = CsvFileUserReader.getUserMap();
                readingsList.add(new Reading( userMap.get(line[0]),line[1],ZonedDateTime.parse(line[2]),new BigDecimal(line[3]) ));
            }

        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return readingsList;
    }
}
