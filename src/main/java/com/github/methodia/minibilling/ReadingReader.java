package com.github.methodia.minibilling;


import java.io.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;


public class ReadingReader implements ReadingReaderInterface {
    private final String path;

    public ReadingReader(String path) {
        this.path = path;
    }

    @Override
    public HashMap<String, List<Reading>> read() {
        HashMap<String, List<Reading>> readings = new HashMap<>();

        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "\\" + "readings.csv")))) {
            while ((line = br.readLine()) != null) {
                String[] readingLine = line.split(",");

                String date = readingLine[2];
                ZonedDateTime ZDTTime = ZonedDateTime.parse(date).withZoneSameInstant(ZoneId.of("GMT"));
                LocalDateTime dateWithZone = LocalDateTime.from(ZDTTime);

                BigDecimal price = new BigDecimal(readingLine[3]);

                String referenceNumber = readingLine[0];

                UserReader userReader = new UserReader(path);
                User userRefNumber = userReader.read().get(referenceNumber);

                List<Reading> list = new ArrayList<>();
                if (readings.get(readingLine[0]) == null) {
                    list.add(new Reading(dateWithZone, price, userRefNumber));
                    readings.put(referenceNumber, list);
                } else {

                    readings.get(referenceNumber).add(new Reading(dateWithZone, price, userRefNumber));

                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return readings;

    }

}