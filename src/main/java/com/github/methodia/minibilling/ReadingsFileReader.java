package com.github.methodia.minibilling;

import org.joda.time.tz.UTCProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ReadingsFileReader implements ReadingsReader {
    private String path;


    public ReadingsFileReader(String path) {
        this.path = path;
    }

    @Override
    public List<Reading> read() {
       String line = "";
        List<Reading> result = new ArrayList<>();
        UserFileReader userFileReader=new UserFileReader(path);
        try {
            userFileReader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(path+"\\readings.csv"));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String referentNumber = data[0];
                String product = data[1];
                String time = data[2];
                ZonedDateTime timeZDT = ZonedDateTime.parse(time).withZoneSameInstant(ZoneId.of("GMT"));
                LocalDateTime instant = LocalDateTime.from(timeZDT);
                BigDecimal price = BigDecimal.valueOf(Long.parseLong(data[3]));
                result.add(new Reading(instant,price,userFileReader.read().get(referentNumber)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}