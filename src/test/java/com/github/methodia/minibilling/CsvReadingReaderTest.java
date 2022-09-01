package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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

public class CsvReadingReaderTest {

    @Test
    public void readReading() {
        final String path = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\input\\";
        final ArrayList<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 5, 7), LocalDate.of(2022, 6, 8), new BigDecimal("1.5")));
        final Map<String, User> users = new HashMap<>();
        users.put("1", new User("Marko", "1", 1, priceList));
        final CsvReadingReader csvReadingReader = new CsvReadingReader(path, users);
        final Collection<Reading> readings = csvReadingReader.read();
        final List<Reading> checkReadingsTo = new ArrayList<>();
        checkReadingsTo.add(
                new Reading(ZonedDateTime.of(2021, 1, 1, 11, 00, 00, 00, ZoneId.of("GMT")), new BigDecimal("1444"),
                        users.get("0"), "gas"));
        checkReadingsTo.add(
                new Reading(ZonedDateTime.of(2021, 1, 1, 12, 00, 00, 00, ZoneId.of("GMT")), new BigDecimal("1480"),
                        users.get("0"), "gas"));
        checkReadingsTo.add(
                new Reading(ZonedDateTime.of(2021, 1, 1, 17, 40, 00, 00, ZoneId.of("GMT")), new BigDecimal("1302"),
                        users.get("0"), "gas"));
        checkReadingsTo.add(
                new Reading(ZonedDateTime.of(2021, 3, 11, 10, 00, 00, 00, ZoneId.of("GMT")), new BigDecimal("1738"),
                        users.get("0"), "gas"));
        checkReadingsTo.add(
                new Reading(ZonedDateTime.of(2021, 3, 17, 10, 00, 00, 00, ZoneId.of("GMT")), new BigDecimal("1567"),
                        users.get("0"), "gas"));
        checkReadingsTo.add(
                new Reading(ZonedDateTime.of(2021, 3, 25, 16, 40, 00, 00, ZoneId.of("GMT")), new BigDecimal("1596"),
                        users.get("0"), "gas"));

        Assertions.assertEquals(checkReadingsTo.size(), readings.size(), "Readings size does not match");
    }
}
