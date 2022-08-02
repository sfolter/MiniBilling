package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

public class CSVReadingsReader implements ReadingsReader{
    String path;
    public CSVReadingsReader(String path) {
        this.path = path;
    }

    @Override
    public Collection<Reading> read() {




        String[] line;
        List<Reading> readingsList = new ArrayList<Reading>() ;

        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {

            while ((line = reader.readNext()) != null) {
                readingsList.add(new Reading(ZonedDateTime.parse(line[2]),new BigDecimal(line[3]), User.getRef(),line[1]));
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return readingsList;
    }
}
