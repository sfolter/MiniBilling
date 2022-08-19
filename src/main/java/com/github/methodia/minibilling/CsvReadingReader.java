package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class CsvReadingReader implements ReadingsReader {

    private final String path;

    private final Map<String, User> userMap;

    public CsvReadingReader(final String path, final Map<String, User> userMap) {
        this.path = path;
        this.userMap = userMap;
    }

    String[] line;

    @Override
    public Collection<Reading> read() {
        final List<Reading> readingsList = new ArrayList<>();
        try (final CSVReader reader = new CSVReader(new FileReader(path + "\\readings.csv"))) {
            while (null != (line = reader.readNext())) {
                final String time = line[2];
                final ZonedDateTime parsedZonedDateTime = ZonedDateTime.parse(time,
                                DateTimeFormatter.ISO_ZONED_DATE_TIME)
                        .withZoneSameInstant(ZoneId.of("GMT"));
                readingsList.add(
                        new Reading(parsedZonedDateTime, new BigDecimal(line[3]), userMap.get(line[0]), line[1]));
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return readingsList;
    }
}
