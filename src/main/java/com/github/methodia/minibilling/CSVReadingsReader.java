package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CSVReadingsReader implements ReadingsReader{
    String path;
    public CSVReadingsReader(String path) {
        this.path = path;
    }

    static List<Reading> readingsList = new ArrayList<Reading>() ;

    public static List<Reading> getReadingsList() {
        return readingsList;
    }

    @Override
    public Collection<Reading> read() {

        String[] line;

        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {

            while ((line = reader.readNext()) != null) {
                Map<String, User> userMap = CSVUserReader.getUserMap();
//                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss z");
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(line[2]);
//                String  time = line[2];
//                LocalDateTime instant = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
                readingsList.add(new Reading(zonedDateTime,new BigDecimal(line[3]), userMap.get(line[0]), line[1]));
            }

        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return readingsList;
    }
}
