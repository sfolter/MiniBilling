package com.github.methodia.minibilling;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

public class CSVReportsReader {


    public List<Reports> read(String csvFile) throws IOException, ParseException {

        List<Reports> listOfReports = new ArrayList<>();
        String line = "";

        try {

            BufferedReader br1 = new BufferedReader(new FileReader("C:\\Users\\user\\MiniBilling1\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv"));
            while ((line = br1.readLine()) != null) {

                String[] reports = line.split(",");


                int indication = Integer.parseInt(reports[3]);

                String jtdate = reports[2];
                DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
                ZonedDateTime instant = ZonedDateTime.parse(jtdate, ISO_ZONED_DATE_TIME);
                DateTime dateTime = parser.parseDateTime(jtdate);


                listOfReports.add(new Reports(reports[0], reports[1], dateTime, indication));


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // return listOfUsers;
        return listOfReports;
    }


    private Date datebl;
}

