package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CsvFileReadingReader implements ReadingsReader {
    @Override
    public Collection<Reading> read(Map<String, User> userMap, String path) {
        String[] line;
        final List<Reading> readingsList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new java.io.FileReader(path + "\\readings.csv"))) {

            while ((line = reader.readNext()) != null) {
                String time = line[2];
                ZonedDateTime parsedZonedDateTime = ZonedDateTime.parse(time, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                        .withZoneSameInstant(ZoneOffset.UTC);

                readingsList.add(new Reading(userMap.get(line[0]), line[1], parsedZonedDateTime, new BigDecimal(line[3])));
            }
            return readingsList;

        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
