package com.example.SpringBatchExample;

import com.example.SpringBatchExample.generators.MeasurementGenerator;
import com.example.SpringBatchExample.models.Price;
import com.example.SpringBatchExample.models.PriceList;
import com.example.SpringBatchExample.models.Reading;
import com.example.SpringBatchExample.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeasurementGeneratorTest {

    @Test
    void testForOneMeasurement() {

        final MeasurementGenerator measurementGenerator = new MeasurementGenerator();

        final User user = getUser();

        final List<Reading> readings = new ArrayList<>();
        final Reading reading = new Reading(ZonedDateTime.of(2020, 8, 16, 3, 20, 0, 0, ZoneId.of("Z").normalized()), 1,
                new BigDecimal("200"), user);
        final Reading reading1 = new Reading(ZonedDateTime.of(2020, 10, 16, 3, 20, 0, 0, ZoneId.of("Z").normalized()),
                1,
                new BigDecimal("300"), user);
        readings.add(reading);
        readings.add(reading1);

        final List<Measurement> measurementList = measurementGenerator.generate(user, readings);

        Assertions.assertEquals(reading.getDate(), measurementList.get(0).getStart().atZone(ZoneId.of("Z")),
                "Don`t match");
        Assertions.assertEquals(reading.getDate().plusMonths(2), measurementList.get(0).getEnd().atZone(ZoneId.of("Z")),
                "Don`t match");

        Assertions.assertEquals(reading1.getDate(),
                measurementList.get(0).getStart().plusMonths(2).atZone(ZoneId.of("Z")), "Don`t match");
        Assertions.assertEquals(reading1.getDate().plusMonths(2),
                measurementList.get(0).getEnd().plusMonths(2).atZone(ZoneId.of("Z")),
                "Don`t match");

        Assertions.assertEquals(new BigDecimal("100"), measurementList.get(0).getValue(), "Value is incorrect");
    }

    @Test
    void testForTwoMeasurements() {
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator();

        final User user = getUser();
        final Reading reading = new Reading(ZonedDateTime.of(2020, 8, 16, 3, 20, 0, 0, ZoneId.of("Z").normalized()), 1,
                new BigDecimal("200"), user);
        final Reading reading1 = new Reading(ZonedDateTime.of(2020, 10, 16, 3, 20, 0, 0, ZoneId.of("Z").normalized()),
                1,
                new BigDecimal("300"), user);
        final Reading reading2 = new Reading(ZonedDateTime.of(2020, 10, 16, 3, 20, 0, 0, ZoneId.of("Z").normalized()),
                1,
                new BigDecimal("600"), user);

        final List<Reading> readings = new ArrayList<>();
        readings.add(reading);
        readings.add(reading1);
        readings.add(reading2);

        final List<Measurement> measurementList = measurementGenerator.generate(user, readings);

        Assertions.assertEquals(reading.getDate(), measurementList.get(0).getStart().atZone(ZoneId.of("Z")),
                "Don`t match");
        Assertions.assertEquals(reading.getDate().plusMonths(2), measurementList.get(0).getEnd().atZone(ZoneId.of("Z")),
                "Don`t match");

        Assertions.assertEquals(reading1.getDate(),
                measurementList.get(0).getStart().plusMonths(2).atZone(ZoneId.of("Z")), "Don`t match");
        Assertions.assertEquals(reading1.getDate().plusMonths(2),
                measurementList.get(0).getEnd().plusMonths(2).atZone(ZoneId.of("Z")),
                "Don`t match");

        Assertions.assertEquals(reading2.getDate(),
                measurementList.get(0).getStart().plusMonths(2).atZone(ZoneId.of("Z")), "Don`t match");
        Assertions.assertEquals(reading2.getDate().plusMonths(2),
                measurementList.get(0).getEnd().plusMonths(2).atZone(ZoneId.of("Z")),
                "Don`t match");

        Assertions.assertEquals(new BigDecimal("100"), measurementList.get(0).getValue(), "Value is incorrect");
        Assertions.assertEquals(new BigDecimal("300"), measurementList.get(1).getValue(), "Value is incorrect");


    }

    @Test
    void emptyMeasurementReturnExceptions() {
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator();

        final List<Reading> readings = new ArrayList<>();

        final Executable executable = () -> measurementGenerator.generate(null, readings);
        Assertions.assertThrows(IllegalStateException.class, executable);
    }

    private static User getUser() {
        final List<Price> prices = new ArrayList<>();
        final Price price = new Price("gas", ZonedDateTime.of(2021, 1, 5, 0, 0, 0, 0, ZoneId.of("Z").normalized()),
                ZonedDateTime.of(2021, 2, 15, 0, 0, 0, 0, ZoneId.of("Z").normalized()), new BigDecimal("200"));
        prices.add(price);
        final PriceList priceList = new PriceList(1, prices);


        return new User("Marko", 2, 1, priceList);
    }
}

