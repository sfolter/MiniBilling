package com.github.methodia.minibilling;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReadingReader implements ReadingsReader {

    @Override
    public Collection<Reading> read(String directory) {
        String line = "";
        List<Reading> result = new ArrayList<>();
        UserReader userReader=new UserReader();
        try {
            BufferedReader br = new BufferedReader(new FileReader(directory + "\\readings.csv"));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String referentNumber = data[0];
                String time = data[2];

                LocalDateTime date = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
                BigDecimal value = BigDecimal.valueOf(Long.parseLong(data[3]));
                List<User> users=userReader.read(directory);
                for (User user:users) {
                    if (user.getRef().equals(referentNumber)) {
                        result.add(new Reading(date, value, user));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}


