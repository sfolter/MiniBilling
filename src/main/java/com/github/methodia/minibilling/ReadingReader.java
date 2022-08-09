package com.github.methodia.minibilling;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.methodia.minibilling.Main.ZONE_ID;

public class ReadingReader implements ReadingsReader {

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
        UserReader userReader = new UserReader();
        Map<String, User> users = userReader.read(directory);

        return new Reading(date, value, users.get(referentNumber));
    }
}


