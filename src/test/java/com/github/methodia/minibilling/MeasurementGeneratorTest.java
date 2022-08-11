package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class MeasurementGeneratorTest {
    @Test
    void generateOneMeasurement() {

        User test = new User("Test Testov", "ref", 1, Collections.emptyList());
        List<Reading> readings = new ArrayList<>();
        Reading reading1 = new Reading("ref", ZonedDateTime.of(2021, 3, 6, 13, 23, 0, 0, ZoneId.of("GMT")), new BigDecimal("100"), test);
        Reading reading2 = new Reading("ref", ZonedDateTime.of(2021, 5, 6, 13, 23, 0, 0, ZoneId.of("GMT")), new BigDecimal("200"), test);
        readings.add(reading1);
        readings.add(reading2);
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator(test, readings);
        Collection<Measurement> measurementCollections = measurementGenerator.generate();
        final Measurement singleMeasurement = measurementCollections.iterator().next();
        ZonedDateTime measurementStart = singleMeasurement.getStart();
        ZonedDateTime measurementEnd = singleMeasurement.getEnd();
        BigDecimal value = singleMeasurement.getValue();
        Assertions.assertEquals(reading1.getTime(), measurementStart,
                "Start time of measurement doesn't match with time of reading");
        Assertions.assertEquals(reading2.getTime(), measurementEnd,
                "End time of measurement doesn't match with time of reading");
        Assertions.assertEquals(new BigDecimal("100"), value,
                "Quantity doesn't match.");
    }

    @Test
    void generateTwoMeasurements() {
        User test = new User("Test Testov", "ref", 1, Collections.emptyList());
        List<Reading> readings = new ArrayList<>();
        Reading reading1 = new Reading("ref", ZonedDateTime.of(2021, 3, 6, 13, 23, 0, 0, ZoneId.of("GMT")), new BigDecimal("100"), test);
        Reading reading2 = new Reading("ref", ZonedDateTime.of(2021, 5, 6, 13, 23, 0, 0, ZoneId.of("GMT")), new BigDecimal("200"), test);
        Reading reading3 = new Reading("ref", ZonedDateTime.of(2021, 7, 6, 13, 23, 0, 0, ZoneId.of("GMT")), new BigDecimal("270"), test);
        Reading reading4 = new Reading("ref", ZonedDateTime.of(2021, 9, 6, 13, 23, 0, 0, ZoneId.of("GMT")), new BigDecimal("300"), test);
        readings.add(reading1);
        readings.add(reading2);
        readings.add(reading3);
        readings.add(reading4);
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator(test, readings);
        List<Measurement> measurementCollections = measurementGenerator.generate().stream().toList();

        Assertions.assertEquals(reading1.getTime(), measurementCollections.get(0).getStart(),
                "Start time of measurement2 doesn't match with time of reading");
        Assertions.assertEquals(reading2.getTime(), measurementCollections.get(0).getEnd(),
                "End time of measurement2 doesn't match with time of reading");
        Assertions.assertEquals(new BigDecimal("100"), measurementCollections.get(0).getValue(),
                "Quantity is different.");

        Assertions.assertEquals(reading2.getTime(), measurementCollections.get(1).getStart(),
                "Start time of measurement2 doesn't match with time of reading");
        Assertions.assertEquals(reading3.getTime(), measurementCollections.get(1).getEnd(),
                "End time of measurement2 doesn't match with time of reading");
        Assertions.assertEquals(new BigDecimal("70"), measurementCollections.get(1).getValue(),
                "Quantity is different.");

        Assertions.assertEquals(reading3.getTime(), measurementCollections.get(2).getStart(),
                "Start time of measurement2 doesn't match with time of reading");
        Assertions.assertEquals(reading4.getTime(), measurementCollections.get(2).getEnd(),
                "End time of measurement2 doesn't match with time of reading");
        Assertions.assertEquals(new BigDecimal("30"), measurementCollections.get(2).getValue(),
                "Quantity is different.");

    }
}