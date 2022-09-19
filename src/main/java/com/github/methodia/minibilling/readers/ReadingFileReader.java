package com.github.methodia.minibilling.readers;

import com.github.methodia.minibilling.Formatter;
import com.github.methodia.minibilling.entity.Reading;
import com.github.methodia.minibilling.entity.User;
import com.github.methodia.minibilling.readers.ReadingsReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ReadingFileReader implements ReadingsReader {

    private final Map<String, User> users;
    private final String directory;

    public ReadingFileReader(final Map<String, User> users, final String directory) {
        this.users = users;
        this.directory = directory;
    }

    @Override
    public Map<String, List<Reading>> read() {

        try (final BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(directory + "\\" + "readings.csv")))) {

            return br.lines()
                    .map(l -> l.split(","))
                    .map(this::createReading)
                    .collect(Collectors.groupingBy(reading -> reading.getUser().getRef()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Reading createReading(final String[] dataReading) {

        final String referentNumber = dataReading[0];
        final ZonedDateTime date = Formatter.parseReading(dataReading[2]);
        final BigDecimal value = BigDecimal.valueOf(Long.parseLong(dataReading[3]));

        return new Reading(referentNumber, date, value, users.get(referentNumber));
    }
}


