package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entity.InvoiceLine;
import com.github.methodia.minibilling.entity.Price;
import com.github.methodia.minibilling.entity.PriceList;
import com.github.methodia.minibilling.entity.User;
import com.github.methodia.minibilling.mainlogic.InvoiceLineGenerator;
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



class InvoiceLineGeneratorTest {
    @Test
    void OneLine() {
        final Price price = new Price("gas", ZonedDateTime.of(2021, 3, 1, 0, 0, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 5, 1, 23, 59, 59, 0, ZoneId.of("GMT")), new BigDecimal("1.4"));
        final List<Price> prices = new ArrayList<>();
        prices.add(price);
        final Measurement firstMeasurement = new Measurement(
                ZonedDateTime.of(2021, 3, 6, 13, 23, 0, 0, ZoneId.of("GMT")),
                ZonedDateTime.of(2021, 4, 14, 15, 32, 0, 0, ZoneId.of("GMT")), new BigDecimal("100"),
                new User("Test Testov", "ref", new PriceList(1), prices));
        final List<Measurement> measurements = new ArrayList<>();
        measurements.add(firstMeasurement);
        final CurrencyCalculator currencyCalculator=new SameCurrency();
        final InvoiceLineGenerator invoiceLineGenerator=new InvoiceLineGenerator();
        final List<InvoiceLine> invoiceLine = invoiceLineGenerator.createInvoiceLine(measurements,
                LocalDate.of(2021, Month.APRIL, 30), currencyCalculator, "BGN", "BGN");
        Assertions.assertEquals(new BigDecimal("140").setScale(2,RoundingMode.HALF_UP).stripTrailingZeros(),invoiceLine.get(0).getAmount().stripTrailingZeros() ,
                "Amount is incorrect.");


    }
}