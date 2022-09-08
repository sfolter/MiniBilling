package com.github.methodia.minibilling;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
//base for CurrencyTest
public class InvoiceGeneratorTest {
    @Test
    void Sample1() throws IOException, ParseException {

        List<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022,1 , 29), LocalDate.of(2022, 8, 31), new BigDecimal("10")));
        User user = new User("Ivan Ivanov", "1", 1, priceList);
        Collection<Reading> readings = new ArrayList<>();
        Reading readingTestOne = new Reading(ZonedDateTime.of(2021, 3, 6, 13, 23, 0, 0, ZoneId.of("GMT")),
                new BigDecimal("100"), user, "gas");
        Reading readingTestTwo = new Reading(ZonedDateTime.of(2021, 5, 6, 13, 23, 0, 0, ZoneId.of("GMT")),
                new BigDecimal("200"), user, "gas");
        readings.add(readingTestOne);
        readings.add(readingTestTwo);
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readings);
        Collection<Measurement> measurementCollection = measurementGenerator.generate();
        final Collection<Price> prices = new ArrayList<>();
        prices.add(new Price("gas", LocalDate.of(2021, 1, 4), LocalDate.of(2022, 5, 6), new BigDecimal("2")));
        String yearMonthStr = "22-06";
        String currency = "EUR";

        InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurementCollection, prices, yearMonthStr, currency);
        final Invoice generatedInvoice = invoiceGenerator.generate();

        List<InvoiceLine> invoiceLines = new ArrayList<>();
        invoiceLines.add(new InvoiceLine(1, new BigDecimal("10"),(LocalDateTime.of(LocalDate.of(2022,8,30), LocalTime.of(12,59,13))),
                LocalDateTime.of(LocalDate.of(2022,8,30), LocalTime.of(12,59,13)), "gas", new BigDecimal("10"),
                1, new BigDecimal("10")));

        List<VatLine> vatLines = new ArrayList<>();
        vatLines.add(new VatLine(1, 1, 10, 20, 10, new BigDecimal("12.5")));
        vatLines.add(new VatLine(2, 2, 5, 20, 10, new BigDecimal("10.5")));
        vatLines.add(new VatLine(3, 3, 7, 20, 10, new BigDecimal("11.5")));

        List<TaxLines> taxLines = new ArrayList<>();
        taxLines.add(new TaxLines(1,1,"Standing Charge",10,"days", new BigDecimal("12"), new BigDecimal("100")));

        Assertions.assertEquals(user,generatedInvoice.getConsumer(),"Consumer doesn't match");
        Assertions.assertEquals(invoiceLines.size(), generatedInvoice.getLines().size(),"Lines don't match");
        Assertions.assertEquals(taxLines.size(), generatedInvoice.getTaxesLines().size(), "Taxes don't match");
        Assertions.assertEquals(vatLines.size(), generatedInvoice.getVatsLines().size(), "VATs don't match");


    }
}
