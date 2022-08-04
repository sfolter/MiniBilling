package com.github.methodia.minibilling;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReadingsReader implements ReadingsReaderInterface {
    @Override
    public List<Reading> read() {

        List<Reading> listOfReports = new ArrayList<>();

        UsersReaders userReader = new UsersReaders();//For Marko -> key(referenceNumber)-> List(value)

        String line;

        try (BufferedReader br1 = new BufferedReader(new FileReader("src\\test\\resources\\sample1\\input\\readings.csv"))) {

            while ((line = br1.readLine()) != null) { //read all lines from b1

                String[] reports = line.split(",");

                String referenceNumber = reports[0];
                String date = reports[2];
                BigDecimal price1 = new BigDecimal(reports[3]);
                LocalDateTime date1 = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);

                listOfReports.add(new Reading(date1, price1, userReader.read().get(referenceNumber)));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfReports;

    }
}