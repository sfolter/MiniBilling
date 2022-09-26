package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.Reading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReadingsFileReader {

    private final String path;


    public ReadingsFileReader(final String path) {
        this.path = path;
    }


    public List<Reading> read() {
        String line;
        final List<Reading> result = new ArrayList<>();
        try {
            final BufferedReader br = new BufferedReader(new FileReader(path + "\\readings.csv"));
            while (null != (line = br.readLine())) {
                final String[] data = line.split(",");
                final String referentNumber = data[0];
                final String product = data[1];
                final String time = data[2];
                final ZonedDateTime timeZDT = ZonedDateTime.parse(time);
                final BigDecimal price = new BigDecimal(data[3]);

                result.add(new Reading(referentNumber, product, timeZDT, price));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}