package com.github.methodia.minibilling;


import java.io.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;


public class ReadingReader implements ReadingReaderInterface {

    private final String path;

    public ReadingReader(String path) {
        this.path = path;
    }

    @Override
    public Map<String, List<Reading>> read() {

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path + "\\" + "readings.csv")))) {

            return br.lines()
                    .map(l -> l.split(","))
                    .map(this::createReadings)
                    .collect(Collectors.groupingBy(reading -> reading.getUser().getRef()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Reading createReadings(String[] readingLine) {
        String date = readingLine[2];
        ZonedDateTime ZDTTime = ZonedDateTime.parse(date).withZoneSameInstant(ZoneId.of("GMT"));
        LocalDateTime dateWithZone = LocalDateTime.from(ZDTTime);

        BigDecimal price = new BigDecimal(readingLine[3]);

        String referenceNumber = readingLine[0];

        UserReader userReader = new UserReader(path);
        User userRefNumber = userReader.read().get(referenceNumber);
        return new Reading(dateWithZone, price, userRefNumber);
    }


}