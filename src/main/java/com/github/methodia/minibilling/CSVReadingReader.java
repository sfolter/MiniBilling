package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CSVReadingReader implements ReadingsReader {
    String path;

    public CSVReadingReader(String path) {
        this.path = path;
    }

    @Override
    public Collection<Reading> read() {

        String[] line;
        List<Reading> readingsList = new ArrayList<Reading>();
        try (CSVReader reader = new CSVReader(new java.io.FileReader(path + "\\readings.csv"))) {
            while ((line = reader.readNext()) != null) {
                CSVUserReader userReader = new CSVUserReader(path);
                Map<String, User> userMap = userReader.read();
                String time = line[2];
                ZonedDateTime parsedZonedDateTime = ZonedDateTime.parse(time, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                        .withZoneSameInstant(ZoneId.of("GMT"));
                readingsList.add(new Reading(parsedZonedDateTime, new BigDecimal(line[3]), userMap.get(line[0]), line[1]));
            }

        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return readingsList;
    }
}
