package com.github.methodia.minibilling;

import java.io.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class ReadingFileReader implements ReadingsReader {
    private final Map<String, User> users;
    private final String directory;

    public ReadingFileReader(Map<String, User> users, String directory) {
        this.users = users;
        this.directory = directory;
    }

    @Override
    public Map<String, List<Reading>> read() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(directory + "\\" + "readings.csv")))) {

            return br.lines()
                    .map(l -> l.split(","))
                    .map(this::createReading)
                    .collect(Collectors.groupingBy(reading -> reading.getUser().getRef()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Reading createReading(String[] dataReading) {

        String referentNumber = dataReading[0];
        ZonedDateTime date = Formatter.parseReading(dataReading[2]);
        BigDecimal value = BigDecimal.valueOf(Long.parseLong(dataReading[3]));

        return new Reading(referentNumber, date, value, users.get(referentNumber));
    }
}


