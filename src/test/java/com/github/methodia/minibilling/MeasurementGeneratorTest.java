package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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
        final User user = new User("Gosho", "2", 2, priceList);
        final Reading firstReading = new Reading(ZonedDateTime.of(2022, 2, 2, 2, 2, 2, 2, ZoneId.of("GMT")),
                new BigDecimal("200"), user, "gas");
        final Reading secondReading = new Reading(ZonedDateTime.of(2022, 5, 2, 2, 2, 2, 2, ZoneId.of("GMT")),
                new BigDecimal("250"), user, "gas");
        final Collection<Reading> readings = new ArrayList<>();
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
}
