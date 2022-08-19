package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
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
    public Collection<Reading> read(final Map<String, User> userMap, final String path) {
        String[] line;
        final List<Reading> readingsList = new ArrayList<>();
        try (final CSVReader reader = new CSVReader(new FileReader(path + "\\readings.csv"))) {

            while (null != (line = reader.readNext())) {
                final String time = line[2];
                final ZonedDateTime parsedZonedDateTime = ZonedDateTime.parse(time, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                        .withZoneSameInstant(ZoneOffset.UTC);

                readingsList.add(new Reading(userMap.get(line[0]), line[1], parsedZonedDateTime, new BigDecimal(line[3])));
            }
            return readingsList;

        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
