package com.github.methodia.minibilling;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.*;
import java.util.*;


import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

public class CSVReportsReader {


    public Map<String, List<Readings>> read() throws IOException, ParseException {
        Map<String, List<Readings>> listOfReports = new HashMap<>();

        String line = "";

        try {

            BufferedReader br1 = new BufferedReader(new FileReader("C:\\Users\\user\\MiniBilling1\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv"));
            while ((line = br1.readLine()) != null) {

                String[] reports = line.split(",");

                List<Readings> list = new ArrayList<>();
                int indication = Integer.parseInt(reports[3]);

                String jtdate = reports[2];
                DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
                ZonedDateTime instant = ZonedDateTime.parse(jtdate, ISO_ZONED_DATE_TIME);
                DateTime dateTime = parser.parseDateTime(jtdate);


                list.add(new Readings(reports[0], reports[1], dateTime, indication));
                listOfReports.put(reports[0], list);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // return listOfUsers;
        return listOfReports;
    }


}

