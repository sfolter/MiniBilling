package com.github.methodia.minibilling;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.*;
import java.util.*;


import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

public class ReadingsReader {
    public Map<String, List<Readings>> read() {
        Map<String, List<Readings>> listOfReports = new HashMap<>(); //For Marko -> key(referenceNumber)-> List(value)
        String line;

        try {

            BufferedReader br1 = new BufferedReader(new FileReader("src\\test\\resources\\sample1\\input\\readings.csv"));
            while ((line = br1.readLine()) != null) { //read all lines from b1

                String[] reports = line.split(",");

                String referenceNumber = reports[0];
                String product = reports[1];
                String date = reports[2];
                double indication = Double.parseDouble(reports[3]);

                DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
                ZonedDateTime instant = ZonedDateTime.parse(date, ISO_ZONED_DATE_TIME);
                DateTime dateTime = parser.parseDateTime(date); //ISO DATE -> yyyy-MM-dd'T'HH:mm:ssz

                List<Readings> list = new ArrayList<>();

                if (listOfReports.get(reports[0]) == null) {
                    list.add(new Readings(reports[0], reports[1], dateTime, indication));
                    listOfReports.put(reports[0], list);
                } else {
                    listOfReports.get(reports[0]).add(new Readings(referenceNumber, product, dateTime, indication));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return listOfReports; //hashMap
    }


}

