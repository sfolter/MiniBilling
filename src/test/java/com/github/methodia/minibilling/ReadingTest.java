package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReadingTest {

    @Test
    public void readingGetters() {
        final List<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 2, 2), new BigDecimal("2")));
        final User user = new User("Gosho", "2", 2, priceList);
        final Reading reading = new Reading(ZonedDateTime.of(2022, 2, 2, 2, 2, 2, 2, ZoneId.of("GMT")),
                new BigDecimal("2.50"), user, "gas");

        Assertions.assertEquals("gas", reading.getProduct(), "Reading getProduct method returns wrong information");
        Assertions.assertEquals(new BigDecimal("2.50"), reading.getValue(), "Value did not match the expected");
        Assertions.assertEquals(ZonedDateTime.of(2022, 2, 2, 2, 2, 2, 2, ZoneId.of("GMT")), reading.getTime(),
                "Time is not correct");
        Assertions.assertEquals(user, reading.getUser(), "User is not correct");
    }
}
