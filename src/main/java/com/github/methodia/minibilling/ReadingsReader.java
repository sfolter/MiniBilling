package com.github.methodia.minibilling;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReadingsReader implements ReadingsReaderInterface {
    private String path;


    public ReadingsReader(String path) {
        this.path = path;
    }

    @Override
    public List<Reading> read() {

        List<Reading> listOfReports = new ArrayList<>();

        UsersReaders userReader = new UsersReaders(path);//For Marko -> key(referenceNumber)-> List(value)

        String line;

        try (BufferedReader br1 = new BufferedReader(new FileReader(path + "\\" + "readings.csv"))) {

            while ((line = br1.readLine()) != null) { //read all lines from b1

                String[] reports = line.split(",");

                String date = reports[2];
                ZonedDateTime ZDTTime = ZonedDateTime.parse(date).withZoneSameInstant(ZoneId.of("GMT"));
                LocalDateTime date1 = LocalDateTime.from(ZDTTime);

                BigDecimal price1 = new BigDecimal(reports[3]);
                String referenceNumber = reports[0];

                listOfReports.add(new Reading(date1, price1, userReader.read().get(referenceNumber)));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listOfReports;

    }
}