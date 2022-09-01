package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeasurementGeneratorTest {

    @Test
    void testForOneMeasurement() {

        MeasurementGenerator measurementGenerator = new MeasurementGenerator();

        User user = getUser();

        List<Reading> readings = new ArrayList<>();
        Reading reading = new Reading(LocalDateTime.of(2020, Month.AUGUST, 16, 3, 20), new BigDecimal("200"), user);
        Reading reading1 = new Reading(LocalDateTime.of(2020, Month.OCTOBER, 16, 3, 20), new BigDecimal("300"), user);
        readings.add(reading);
        readings.add(reading1);

        List<Measurement> measurementList = measurementGenerator.generate(user, readings);

        Assertions.assertEquals(reading.getTime(), measurementList.get(0).getStart(), "Don`t match");
        Assertions.assertEquals(reading.getTime().plusMonths(2), measurementList.get(0).getEnd(), "Don`t match");

        Assertions.assertEquals(reading1.getTime(), measurementList.get(0).getStart().plusMonths(2), "Don`t match");
        Assertions.assertEquals(reading1.getTime().plusMonths(2), measurementList.get(0).getEnd().plusMonths(2),
                "Don`t match");

        Assertions.assertEquals(new BigDecimal("100"), measurementList.get(0).getValue(), "Value is incorrect");
    }

    @Test
    void testForTwoMeasurements() {
        MeasurementGenerator measurementGenerator = new MeasurementGenerator();

        User user = getUser();

        Reading reading = new Reading(LocalDateTime.of(2020, Month.AUGUST, 16, 3, 20), new BigDecimal("200"), user);
        Reading reading1 = new Reading(LocalDateTime.of(2020, Month.OCTOBER, 16, 3, 20), new BigDecimal("300"), user);
        Reading reading2 = new Reading(LocalDateTime.of(2020, Month.OCTOBER, 16, 3, 20), new BigDecimal("600"), user);

        List<Reading> readings = new ArrayList<>();
        readings.add(reading);
        readings.add(reading1);
        readings.add(reading2);

        List<Measurement> measurementList = measurementGenerator.generate(user, readings);

        Assertions.assertEquals(reading.getTime(), measurementList.get(0).getStart(), "Don`t match");
        Assertions.assertEquals(reading.getTime().plusMonths(2), measurementList.get(0).getEnd(), "Don`t match");

        Assertions.assertEquals(reading1.getTime(), measurementList.get(0).getStart().plusMonths(2), "Don`t match");
        Assertions.assertEquals(reading1.getTime().plusMonths(2), measurementList.get(0).getEnd().plusMonths(2),
                "Don`t match");

        Assertions.assertEquals(reading2.getTime(), measurementList.get(0).getStart().plusMonths(2), "Don`t match");
        Assertions.assertEquals(reading2.getTime().plusMonths(2), measurementList.get(0).getEnd().plusMonths(2),
                "Don`t match");

        Assertions.assertEquals(new BigDecimal("100"), measurementList.get(0).getValue(), "Value is incorrect");
        Assertions.assertEquals(new BigDecimal("300"), measurementList.get(1).getValue(), "Value is incorrect");


    }
//    @Test
//    void emptyMeasurementReturnExceptions(){
//        Measurements measurements = new MeasurementGenerator();
//        User user = getUser();
//        List<Reading> readings = new ArrayList<>();
//
//        Assertions.assertThrows(IllegalStateException.class,
//                ()->{
//
//                }
//                );
//
//    }

    private User getUser() {
        List<Price> prices = new ArrayList<>();
        Price price = new Price("gas", LocalDate.of(2021, Month.JANUARY, 5),
                LocalDate.of(2021, Month.FEBRUARY, 15), new BigDecimal("200"));
        prices.add(price);

        User user = new User("Marko", "2", 1, prices);
        return user;
    }
}
