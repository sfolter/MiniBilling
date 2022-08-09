package com.github.methodia.minibilling;


import java.io.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

public class ReadingsReader implements ReadingsReaderInterface {
    private String path;
  //  UsersReaders userReader = new UsersReaders(path);

    public ReadingsReader(String path) {
        this.path = path;
    }

    @Override
    public List<Reading> read() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "\\" + "readings.csv")))) {

            return br.lines()
                    .map(l -> l.split(","))
                    .map(this::createGraph).toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private Reading createGraph(String[] readingTokens) {

        String date = readingTokens[2];
        ZonedDateTime ZDTTime = ZonedDateTime.parse(date).withZoneSameInstant(ZoneId.of("GMT"));
        LocalDateTime dateWithZone = LocalDateTime.from(ZDTTime);

        BigDecimal price = new BigDecimal(readingTokens[3]);

        String referenceNumber = readingTokens[0];
        UsersReaders userReader = new UsersReaders(path);

        return new Reading(dateWithZone, price, userReader.read().get(referenceNumber));

    }

}