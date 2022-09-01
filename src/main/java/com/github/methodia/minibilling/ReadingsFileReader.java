package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReadingsFileReader implements ReadingsReader {

    final private String path;


    public ReadingsFileReader(String path) {
        this.path = path;
    }

    @Override
    public List<Reading> read() {
        String line;
        List<Reading> result = new ArrayList<>();
        UserFileReader userFileReader = new UserFileReader(path);
        try {
            userFileReader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "\\readings.csv"));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String referentNumber = data[0];
                String time = data[2];
                ZonedDateTime timeZDT = ZonedDateTime.parse(time).withZoneSameInstant(ZoneId.of("GMT"));
                LocalDateTime instant = LocalDateTime.from(timeZDT);
                BigDecimal price = new BigDecimal(data[3]);

                User user = userFileReader.read().stream().filter(user1 -> user1.ref()
                        .equals(referentNumber)).findFirst().orElse(null);

                result.add(new Reading(instant, price, user));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}