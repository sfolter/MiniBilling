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

        Assertions.assertEquals(new BigDecimal("427.20").setScale(2, RoundingMode.HALF_UP).stripTrailingZeros(),
                invoice.getTotalAmount(),
                "Amount is incorrect");
        Assertions.assertEquals(new BigDecimal("403.20").setScale(2, RoundingMode.HALF_UP).stripTrailingZeros(),
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

        Assertions.assertEquals("BGN", invoice.getCurrencyFrom(), "Currency is incorrect");
        Assertions.assertEquals("EUR", invoice.getCurrencyTo(), "Currency is incorrect");

        Assertions.assertEquals(new BigDecimal("218.33"), invoice.getExchangedTotalAmount(), "Amount is incorrect");

    }
}
