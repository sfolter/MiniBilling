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
    final static List<Reading> readingsList = new ArrayList<Reading>() ;

    public static List<Reading> getReadingsList() {
        return readingsList;
    }

    @Override
    public Collection<Reading> read(String path) {
        String[] line;

        try (CSVReader reader = new CSVReader(new java.io.FileReader(path + "\\readings.csv"))) {

            while ((line = reader.readNext()) != null) {
                Map<String, User> userMap = CsvFileUserReader.getUserMap();
//                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss z");
                String time= line[2];
                ZonedDateTime parsedZonedDateTime = ZonedDateTime.parse(time, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                        .withZoneSameInstant(ZoneOffset.UTC);

//                readingsList.add(new Reading(parsedZonedDateTime,new BigDecimal(line[3]), userMap.get(line[0]), line[1]));
//                ZonedDateTime zonedDateTime = ZonedDateTime.parse(line[2]);
//                String  time = line[2];
//                LocalDateTime instant = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
                readingsList.add(new Reading( userMap.get(line[0]),line[1],parsedZonedDateTime,new BigDecimal(line[3]) ));
            }

        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return readingsList;
    }
}
