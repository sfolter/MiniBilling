package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class InvoiceGeneratorTest {

    @Test
    void amountTest() {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();

        List<Price> prices = new ArrayList<>();
        Price price = new Price("gas", LocalDate.of(2021, Month.JANUARY, 5),
                LocalDate.of(2021, Month.FEBRUARY, 15), new BigDecimal("1.8"));
        prices.add(price);

        User user = new User("Marko", "2", 1, prices);

        List<Measurement> measurements = new ArrayList<>();
        Measurement measurement = new Measurement(LocalDateTime.of(2021, Month.JANUARY, 5, 16, 19),
                LocalDateTime.of(2021, Month.FEBRUARY, 15, 20, 15), new BigDecimal("200"), user);
        measurements.add(measurement);

        Converter currencyConverter = (f, t, a) -> a;
        Invoice invoice = invoiceGenerator.generate(LocalDateTime.of(2021, Month.DECEMBER, 23, 5, 30, 50),
                measurements, "BGN", "EUR", currencyConverter);

        Assertions.assertEquals(new BigDecimal("251.41").setScale(2, RoundingMode.HALF_UP).stripTrailingZeros(),
                invoice.getTotalAmount(),
                "Amount is incorrect");
        Assertions.assertEquals(new BigDecimal("294.33").setScale(2, RoundingMode.HALF_UP).stripTrailingZeros(),
                invoice.getTotalAmountWithVat().stripTrailingZeros(),
                "Amount is incorrect");
    }

    @Test
    void currencyConverter() {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        List<Price> prices = new ArrayList<>();
        Price price = new Price("gas", LocalDate.of(2021, Month.JANUARY, 5),
                LocalDate.of(2021, Month.FEBRUARY, 15), new BigDecimal("1.8"));
        prices.add(price);

        User user = new User("Marko", "2", 1, prices);

        List<Measurement> measurements = new ArrayList<>();
        Measurement measurement = new Measurement(LocalDateTime.of(2021, Month.JANUARY, 5, 16, 19),
                LocalDateTime.of(2021, Month.FEBRUARY, 15, 20, 15), new BigDecimal("200"), user);
        measurements.add(measurement);

        Converter currencyConverter = (f, t, a) -> a;
        Invoice invoice = invoiceGenerator.generate(LocalDateTime.of(2021, Month.DECEMBER, 23, 5, 30, 50),
                measurements, "BGN", "EUR", currencyConverter
        );

        currencyConverter.convertTo("BGN", "EUR", invoice.getTotalAmount());

        Assertions.assertEquals(new BigDecimal("218.6"), invoice.getTotalAmount(), "Amount is incorrect");

    }

    @Test
    void emptyMeasurementReturnExceptions() {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        MeasurementGenerator measurementGenerator = new MeasurementGenerator();

        List<Reading> readings = new ArrayList<>();

        Assertions.assertThrows(IllegalStateException.class, () -> invoiceGenerator.generate(LocalDateTime.of(2021, Month.AUGUST, 10, 5, 30),
                measurementGenerator.generate(null, readings), "", "", null),"The measurement is empty");
    }

}
