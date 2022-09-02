package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReadingsReaderTest {

    @Test
    public void readReading() {
        final String path = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        final ArrayList<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 5, 7), LocalDate.of(2022, 6, 8), new BigDecimal("1.6")));
        final Map<String, User> users = new HashMap<>();
        users.put("2", new User("Asen", "3", 1, priceList));
        final CSVReadingsReader csvReadingReader = new CSVReadingsReader(path, users);
        final Collection<Reading> readings = csvReadingReader.read();
        final List<Reading> checkReadingsTo = new ArrayList<>();
        checkReadingsTo.add(
                new Reading(ZonedDateTime.of(2021, 1, 1, 11, 00, 00, 00, ZoneId.of("GMT")), new BigDecimal("1444"),
                        users.get("0"), "gas"));
        checkReadingsTo.add(
                new Reading(ZonedDateTime.of(2021, 2, 11, 12, 00, 00, 00, ZoneId.of("GMT")), new BigDecimal("1480"),
                        users.get("0"), "gas"));
        checkReadingsTo.add(
                new Reading(ZonedDateTime.of(2021, 3, 18, 17, 40, 00, 00, ZoneId.of("GMT")), new BigDecimal("1302"),
                        users.get("0"), "gas"));
        checkReadingsTo.add(
                new Reading(ZonedDateTime.of(2021, 4, 11, 10, 00, 00, 00, ZoneId.of("GMT")), new BigDecimal("1738"),
                        users.get("0"), "gas"));
        checkReadingsTo.add(
                new Reading(ZonedDateTime.of(2021, 5, 17, 10, 00, 00, 00, ZoneId.of("GMT")), new BigDecimal("1567"),
                        users.get("0"), "gas"));
        checkReadingsTo.add(
                new Reading(ZonedDateTime.of(2021, 6, 25, 16, 40, 00, 00, ZoneId.of("GMT")), new BigDecimal("1596"),
                        users.get("0"), "gas"));

        Assertions.assertEquals(checkReadingsTo.size(), readings.size(), "Readings size doesn't match");
    }
}
