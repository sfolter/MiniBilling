package com.example.SpringBatchExample;

import com.example.SpringBatchExample.generators.InvoiceGenerator;
import com.example.SpringBatchExample.generators.MeasurementGenerator;
import com.example.SpringBatchExample.models.Invoice;
import com.example.SpringBatchExample.models.Price;
import com.example.SpringBatchExample.models.PriceList;
import com.example.SpringBatchExample.models.Reading;
import com.example.SpringBatchExample.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceGeneratorTest {

    @Test
    void amountTest() {
        final InvoiceGenerator invoiceGenerator = new InvoiceGenerator();

        final List<Price> prices = new ArrayList<>();
        final Price price = new Price("gas", ZonedDateTime.of(2021, 1, 5, 0, 0, 0, 0, ZoneId.of("Z")),
                ZonedDateTime.of(2021, 1, 15, 0, 0, 0, 0, ZoneId.of("Z")), new BigDecimal("1.8"));
        prices.add(price);
        final PriceList priceList = new PriceList(1, prices);
        final User user = new User("Marko", 2, 1, priceList);

        final List<Measurement> measurements = new ArrayList<>();
        final Measurement measurement = new Measurement(LocalDateTime.of(2021, Month.JANUARY, 5, 16, 19),
                LocalDateTime.of(2021, Month.FEBRUARY, 15, 20, 15), new BigDecimal("200"), user);
        measurements.add(measurement);

        final Converter currencyConverter = (s, t, a) -> a;
        final Invoice invoice = invoiceGenerator.generate(LocalDateTime.of(2021, Month.DECEMBER, 23, 5, 30, 50),
                measurements, "BGN", "EUR", currencyConverter);

        Assertions.assertEquals(new BigDecimal("48.72").setScale(2, RoundingMode.HALF_UP).stripTrailingZeros(),
                invoice.getTotalAmount(),
                "Amount is incorrect");
        Assertions.assertEquals(new BigDecimal("56.84").setScale(2, RoundingMode.HALF_UP).stripTrailingZeros(),
                invoice.getTotalAmountWithVat().stripTrailingZeros(),
                "Amount is incorrect");
    }

    @Test
    void currencyConverter() {
        final InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        final List<Price> prices = new ArrayList<>();
        final Price price = new Price("gas", ZonedDateTime.of(2021, 1, 5, 0, 0, 0, 0, ZoneId.of("Z")),
                ZonedDateTime.of(2021, 2, 15, 0, 0, 0, 0, ZoneId.of("Z")), new BigDecimal("1.8"));
        prices.add(price);
        final PriceList priceList = new PriceList(1, prices);

        final User user = new User("Marko", 2, 1, priceList);

        final List<Measurement> measurements = new ArrayList<>();
        final Measurement measurement = new Measurement(LocalDateTime.of(2021, Month.JANUARY, 5, 16, 19),
                LocalDateTime.of(2021, Month.FEBRUARY, 15, 20, 15), new BigDecimal("200"), user);
        measurements.add(measurement);

        final Converter currencyConverter = (s, t, a) -> a;
        final Invoice invoice = invoiceGenerator.generate(LocalDateTime.of(2021, Month.DECEMBER, 23, 5, 30, 50),
                measurements, "BGN", "EUR", currencyConverter
        );

        currencyConverter.convertTo("BGN", "EUR", invoice.getTotalAmount());

        Assertions.assertEquals(new BigDecimal("213.36"), invoice.getTotalAmount(), "Amount is incorrect");

    }

    @Test
    void emptyMeasurementReturnExceptions() {
        final InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator();

        final List<Reading> readings = new ArrayList<>();

        Assertions.assertThrows(IllegalStateException.class,
                () -> invoiceGenerator.generate(LocalDateTime.of(2021, Month.AUGUST, 10, 5, 30),
                        measurementGenerator.generate(null, readings), "", "", null), "The measurement is empty");
    }

}
