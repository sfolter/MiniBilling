package com.github.methodia.minibilling;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class InvoiceGeneratorTest {
    @Test
    void Sample1() {
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

    }
}
