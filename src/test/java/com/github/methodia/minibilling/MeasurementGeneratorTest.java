package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Executable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MeasurementGeneratorTest {

    @Test
    public void getMeasurement() {
        final List<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 2, 2), new BigDecimal("2")));
        final User user = new User("Gosho", "2", 2);
        final Reading firstReading = new Reading(ZonedDateTime.of(2022, 2, 2, 2, 2, 2, 2, ZoneId.of("GMT")),
                new BigDecimal("200"), user, "gas");
        final Reading secondReading = new Reading(ZonedDateTime.of(2022, 5, 2, 2, 2, 2, 2, ZoneId.of("GMT")),
                new BigDecimal("250"), user, "gas");
        final List<Reading> readings = new ArrayList<>();
        readings.add(firstReading);
        readings.add(secondReading);
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readings);
        final Collection<Measurement> measurements = measurementGenerator.generate();
        final Measurement measurement = measurements.iterator().next();

        Assertions.assertEquals(firstReading.getTime().toLocalDateTime(), measurement.getStart(),
                "Start date does not match");
        Assertions.assertEquals(secondReading.getTime().toLocalDateTime(), measurement.getEnd(),
                "End date does not match");
        Assertions.assertEquals(firstReading.getUser(), measurement.getUser(), "User does not match");
        Assertions.assertEquals(secondReading.getUser(), measurement.getUser(), "User does not match");
        Assertions.assertEquals(secondReading.getValue().subtract(firstReading.getValue()), measurement.getValue(),
                "Value does not match");
    }

    @Test
    public void generateTwoMeasurements() {
        final List<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 2, 2), new BigDecimal("2")));
        final User user = new User("Gosho", "2", 2);
        final Reading firstReading = new Reading(ZonedDateTime.of(2022, 2, 2, 2, 2, 2, 2, ZoneId.of("GMT")),
                new BigDecimal("200"), user, "gas");
        final Reading secondReading = new Reading(ZonedDateTime.of(2022, 5, 2, 2, 2, 2, 2, ZoneId.of("GMT")),
                new BigDecimal("250"), user, "gas");
        final Reading thirdReading = new Reading(ZonedDateTime.of(2022, 6, 2, 2, 2, 2, 2, ZoneId.of("GMT")),
                new BigDecimal("320"), user, "gas");
        final List<Reading> readings = new ArrayList<>();
        readings.add(firstReading);
        readings.add(secondReading);
        readings.add(thirdReading);
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readings);
        final List<Measurement> measurementList = measurementGenerator.generate().stream().toList();

        Assertions.assertEquals(firstReading.getTime(), measurementList.get(0).getStart().atZone(ZoneId.of("GMT")),
                "Start date of first measurement does not match");
        Assertions.assertEquals(secondReading.getTime(), measurementList.get(0).getEnd().atZone(ZoneId.of("GMT")),
                "End date of first measurement does not match");
        Assertions.assertEquals(secondReading.getValue().subtract(firstReading.getValue()),
                measurementList.get(0).getValue(), "Value in first measurement does not match");
        Assertions.assertEquals(firstReading.getUser(), measurementList.get(0).getUser(),
                "User in first measurement does not match");
        Assertions.assertEquals(secondReading.getTime(), measurementList.get(1).getStart().atZone(ZoneId.of("GMT")),
                "Start date of second measurement does not match");
        Assertions.assertEquals(thirdReading.getTime(), measurementList.get(1).getEnd().atZone(ZoneId.of("GMT")),
                "End date of second measurement does not match");
        Assertions.assertEquals(thirdReading.getValue().subtract(secondReading.getValue()),
                measurementList.get(1).getValue(), "Value in second measurement does not match");
        Assertions.assertEquals(secondReading.getUser(), measurementList.get(1).getUser(),
                "User in second measurement does not match");
    }

    @Test
    public void emptyMeasurement(){
        final List<Reading> readings = new ArrayList<>();
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator(null, readings);

        Assertions.assertThrows(IllegalStateException.class, measurementGenerator::generate, "There is no measurement in list");

    }
}
