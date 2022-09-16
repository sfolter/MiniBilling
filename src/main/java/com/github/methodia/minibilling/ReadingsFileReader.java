package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.Reading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReadingsFileReader {

    final private String path;


    public ReadingsFileReader(String path) {
        this.path = path;
    }


    public List<Reading> read() {
        String line;
        List<Reading> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "\\readings.csv"));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String referentNumber = data[0];
                String product = data[1];
                String time = data[2];
                ZonedDateTime timeZDT = ZonedDateTime.parse(time);
                BigDecimal price = new BigDecimal(data[3]);

                result.add(new Reading(referentNumber, product, timeZDT, price));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}