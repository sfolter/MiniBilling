package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;
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

        try (CSVReader reader = new CSVReader(new java.io.FileReader(path + "\\readings.csv"))) {

            while ((line = reader.readNext()) != null) {
                Map<String, User> userMap = CSVUserReader.getUserMap();
//                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss z");
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(line[2]);
//                String  time = line[2];
//                LocalDateTime instant = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
                String  time = line[2];
                //DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                 //DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
                 //OffsetDateTime osdt1 = OffsetDateTime.parse("2020-04-15T23:00:01+0000", dtf2);

                ZonedDateTime parsedZonedDateTime = ZonedDateTime.parse(time, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                        .withZoneSameInstant(ZoneOffset.UTC);
                //System.out.println(parsedZonedDateTime);
                readingsList.add(new Reading(parsedZonedDateTime,new BigDecimal(line[3]), userMap.get(line[0]), line[1]));
            }

        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return readingsList;
    }
}
