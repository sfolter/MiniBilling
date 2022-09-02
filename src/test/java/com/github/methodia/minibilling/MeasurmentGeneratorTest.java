package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Todor Todorov
 * @Date 31.08.2022
 * Methodia Inc.
 */
public class MeasurmentGeneratorTest {
    @Test
    public void getMeasurement() {
        final List<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 2, 2), new BigDecimal("2")));
        final User user = new User("Gosho", "2", priceList, 2);
        final Reading firstReading = new Reading(user, "gas",
                ZonedDateTime.of(2022, 2, 2, 2, 2, 2, 2, ZoneId.of("GMT")), new BigDecimal("200"));
        final Reading secondReading = new Reading(user, "gas",
                ZonedDateTime.of(2022, 5, 2, 2, 2, 2, 2, ZoneId.of("GMT")), new BigDecimal("250"));
        final Collection<Reading> readings = new ArrayList<>();
        readings.add(firstReading);
        readings.add(secondReading);
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        final Collection<Measurement> measurements = measurementGenerator.generate(user, readings);
        final Measurement measurement = measurements.iterator().next();

        Assertions.assertEquals(firstReading.time().toLocalDateTime(), measurement.start(),
                "Start date did not match");
        Assertions.assertEquals(secondReading.time().toLocalDateTime(), measurement.end(),
                "End date did not match");
        Assertions.assertEquals(firstReading.user(), measurement.user(), "User did not match");
        Assertions.assertEquals(secondReading.user(), measurement.user(), "User did not match");
        Assertions.assertEquals(secondReading.value().subtract(firstReading.value()), measurement.value(),
                "Value did not match");
    }
}
