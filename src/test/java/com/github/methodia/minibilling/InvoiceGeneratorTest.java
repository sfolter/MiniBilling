package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

class InvoiceGeneratorTest {

    @Test
    void withoutChangingCurrency() {
        final Price price = new Price("gas", ZonedDateTime.of(2021, 3, 1, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 5, 1, 23, 59, 59, 0, ZoneId.of("GMT")), new BigDecimal("1.4"));
        final List<Price> prices = new ArrayList<>();
        prices.add(price);
        final Measurement firstMeasurement = new Measurement(
                ZonedDateTime.of(2021, 3, 6, 13, 23, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 4, 14, 15, 32, 0, 0, ZoneId.of("GMT")), new BigDecimal("100"),
                new User("Test Testov", "ref", 1, prices));
        final Measurement secondMeasurement = new Measurement(
                ZonedDateTime.of(2021, 4, 14, 15, 32, 1, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 4, 29, 15, 32, 0, 0, ZoneId.of("GMT")), new BigDecimal("200"),
                new User("Test Testov", "ref", 1, prices));
        final List<Measurement> measurements = new ArrayList<>();
        measurements.add(firstMeasurement);
        measurements.add(secondMeasurement);
        final CurrencyCalculator currencyCalculator = new SameCurrency();
        final InvoiceGenerator invoiceGenerator = new InvoiceGenerator(currencyCalculator, "BGN");
        Invoice invoice = invoiceGenerator.generate(measurements, 1, LocalDate.of(2021, Month.APRIL, 30), "BGN");
        Assertions.assertEquals(new BigDecimal("140").stripTrailingZeros(),invoice.getLines().get(0).getAmount(),
                "Amount of the first line is incorrect.");
        Assertions.assertEquals(new BigDecimal("280").stripTrailingZeros(),invoice.getLines().get(1).getAmount(),
                "Amount of the first line is incorrect.");
        Assertions.assertEquals(new BigDecimal("508"),invoice.getTotalAmount(),
                "Total amount isn't correct.");
        Assertions.assertEquals(new BigDecimal("592.80"),invoice.getTotalAmountWithVat(),
                "Total amount with vat isn't correct.");
        Assertions.assertEquals(measurements.size(),invoice.getLines().size(),
                "Count of lines is incorrect.");
        Assertions.assertEquals(invoice.getLines().size(),invoice.getTaxes().size(),
                "Count of taxes is incorrect");
        Assertions.assertEquals(invoice.getLines().size()*2+invoice.getTaxes().size(),invoice.getVat().size(),
                "Count of vats is incorrect");
    }

}