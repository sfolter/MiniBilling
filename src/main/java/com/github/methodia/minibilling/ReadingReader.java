package com.github.methodia.minibilling;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.github.methodia.minibilling.Main.ZONE_ID;

public class ReadingReader implements ReadingsReader {

    @Override
    public List<Reading> read(String directory, String borderTime) {
        String line = "";
        List<Reading> result = new ArrayList<>();
        UserReader userReader = new UserReader();
        try {
            BufferedReader br = new BufferedReader(new FileReader(directory + "\\readings.csv"));

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String referentNumber = data[0];
                ZonedDateTime date = Formatter.parseReading(data[2]);
                BigDecimal value = BigDecimal.valueOf(Long.parseLong(data[3]));

                Map<String, User> users = userReader.read(directory);

                LocalDate borderDate = Formatter.parseBorder(borderTime);
                if (date.toLocalDate().isBefore(borderDate)) {
                    result.add(new Reading(date, value, users.get(referentNumber)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}


