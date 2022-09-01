package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MeasurementTest {

    @Test
    public void getMeasurement() {
        final ArrayList<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 5, 7), LocalDate.of(2022, 6, 8), new BigDecimal("1.5")));
        final User user = new User("Ivan", "1", 1, priceList);
        final Measurement measurement = new Measurement(LocalDateTime.of(2021, 2, 2, 2, 2, 2, 2),
                LocalDateTime.of(2022, 6, 6, 6, 6, 6, 6), new BigDecimal("50"), user);

        Assertions.assertEquals(LocalDateTime.of(2021, 2, 2, 2, 2, 2, 2), measurement.getStart(),
                "Start date does not match.");
        Assertions.assertEquals(LocalDateTime.of(2022, 6, 6, 6, 6, 6, 6), measurement.getEnd(),
                "End date does not match.");
        Assertions.assertEquals(new BigDecimal("50"), measurement.getValue(), "Value does not match");
        Assertions.assertEquals(user, measurement.getUser(), "User does not match.");
    }
}
