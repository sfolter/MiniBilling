package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MeasurementGeneratorTest {
    @Test
    void generateOneMeasurement() {

        User user = new User("Ivan Ivanov", "1", 1, Collections.emptyList());
        Collection<Reading> readings = new ArrayList<>();
        Reading readingTestOne = new Reading(ZonedDateTime.of(2021, 3, 6, 13, 23, 0, 0, ZoneId.of("GMT")),
                new BigDecimal("100"), user, "gas");
        Reading readingTestTwo = new Reading(ZonedDateTime.of(2021, 5, 6, 13, 23, 0, 0, ZoneId.of("GMT")),
                new BigDecimal("200"), user, "gas");
        readings.add(readingTestOne);
        readings.add(readingTestTwo);
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readings);
        Collection<Measurement> measurementCollection = measurementGenerator.generate();
        final Measurement singleMeasurement = measurementCollection.iterator().next();
        LocalDateTime start = singleMeasurement.getStart();
        LocalDateTime end = singleMeasurement.getEnd();
        BigDecimal value = singleMeasurement.getValue();

        Assertions.assertEquals(readingTestOne.getTime().toLocalDateTime(), start, "Start times don't match");
        Assertions.assertEquals(readingTestTwo.getTime().toLocalDateTime(), end, "End times don't match" );
        Assertions.assertEquals(readingTestTwo.getValue().subtract(readingTestOne.getValue()), value, "Quantity doesn't match");


    }

    @Test
    void generateTwoMeasurements(){
        User user = new User("Ivan Ivanov", "1", 1, Collections.emptyList());
        List<Reading> readings = new ArrayList<>();
        Reading readingTestOne = new Reading(ZonedDateTime.of(2021, 3, 6, 13, 23, 0, 0, ZoneId.of("GMT")),
                new BigDecimal("100"), user, "gas");
        Reading readingTestTwo = new Reading(ZonedDateTime.of(2021, 4, 6, 13, 23, 0, 0, ZoneId.of("GMT")),
                new BigDecimal("200"), user, "gas");
        Reading readingTestThree = new Reading(ZonedDateTime.of(2021, 5, 6, 13, 23, 0, 0, ZoneId.of("GMT")),
                new BigDecimal("300"), user, "gas");
        Reading readingTestFour = new Reading(ZonedDateTime.of(2021, 6, 6, 13, 23, 0, 0, ZoneId.of("GMT")),
                new BigDecimal("400"), user, "gas");
        readings.add(readingTestOne);
        readings.add(readingTestTwo);
        readings.add(readingTestThree);
        readings.add(readingTestFour);
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readings);
        List<Measurement> measurementCollections = measurementGenerator.generate().stream().toList();

        Assertions.assertEquals(readingTestOne.getTime(), measurementCollections.get(0).getStart().atZone(
                ZoneId.of("GMT")), "Measurement and reading start don't match");
        Assertions.assertEquals(readingTestTwo.getTime(), measurementCollections.get(0).getEnd().atZone(
                ZoneId.of("GMT")), "Measurement and reading end don't match");
        Assertions.assertEquals(new BigDecimal("100"), measurementCollections.get(0).getValue(), "Quantity doesn't match");

        Assertions.assertEquals(readingTestTwo.getTime(), measurementCollections.get(1).getStart().atZone(ZoneId.of("GMT")),"Measurement2 and reading start don't match");
        Assertions.assertEquals(readingTestThree.getTime(), measurementCollections.get(1).getEnd().atZone(ZoneId.of("GMT")),"Measurement2 and reading end don't match");
        Assertions.assertEquals(new BigDecimal("100"), measurementCollections.get(1).getValue(),"Quantity doesn't match");

        Assertions.assertEquals(readingTestThree.getTime(), measurementCollections.get(2).getStart().atZone(
                ZoneId.of("GMT")), "Measurement2 and reading start don't match");
        Assertions.assertEquals(readingTestFour.getTime(), measurementCollections.get(2).getEnd().atZone(
                ZoneId.of("GMT")), "Measurement2 and reading end don't match");
        Assertions.assertEquals(new BigDecimal("100"), measurementCollections.get(2).getValue(), "Quantity doesn't match");
    }
}
