package com.github.methodia.minibilling;

import java.io.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;


public class ReadingFileReader implements ReadingsReader {
     private final  Map<String, User>users;

    public ReadingFileReader(Map<String, User> users) {
        this.users = users;
    }

    @Override
    public List<Reading> read(String directory) {

        String path = directory + "readings.csv";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {

            return br.lines()
                    .map(l -> l.split(","))
                    .map(a -> createReading(a, directory)).toList();

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    private Reading createReading(String[] dataReading, String directory) {

        String referentNumber = dataReading[0];
        ZonedDateTime date = Formatter.parseReading(dataReading[2]);
        BigDecimal value = BigDecimal.valueOf(Long.parseLong(dataReading[3]));

        return new Reading(referentNumber, date, value, users.get(referentNumber));
    }
}


