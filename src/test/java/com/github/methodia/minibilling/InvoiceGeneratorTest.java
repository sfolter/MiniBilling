package com.github.methodia.minibilling;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class InvoiceGeneratorTest {

    @Test
    public void generateInvoice() throws IOException, ParseException {
        final ArrayList<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 1, 7), LocalDate.of(2022, 6, 8), new BigDecimal("1.5")));
        final User user = new User("Ivan", "1", 1, priceList);
        final Reading firstReading = new Reading(ZonedDateTime.of(2022, 2, 2, 2, 2, 2, 2, ZoneId.of("GMT")),
                new BigDecimal("200"), user, "gas");
        final Reading secondReading = new Reading(ZonedDateTime.of(2022, 5, 2, 2, 2, 2, 2, ZoneId.of("GMT")),
                new BigDecimal("250"), user, "gas");
        final Collection<Reading> readings = new ArrayList<>();
        readings.add(firstReading);
        readings.add(secondReading);
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readings);
        final Collection<Measurement> measurements = measurementGenerator.generate();
        final Collection<Price> prices = new ArrayList<>();
        prices.add(new Price("gas", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 8, 2), new BigDecimal("2")));
        final String yearMonthStr = "22-06";
        final String currency = "EUR";
        final String key = "3b14c37cbcca1d0ff2fca003";
        final int taxedAmountPercentage = 60;
        final InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurements, prices, yearMonthStr,
                currency, key);
        final Invoice generatedInvoice = invoiceGenerator.generate(taxedAmountPercentage);
        final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("436"),
                LocalDateTime.of(2021, 1, 1, 14, 40, 0),
                LocalDateTime.of(2021, 3, 11, 7, 0, 0), "gas", new BigDecimal("1.8"), 1, new BigDecimal("401"));
        final List<InvoiceLine> invoiceLines = new ArrayList<>();
        invoiceLines.add(invoiceLine);
        final Taxes taxes = new Taxes(1, 0, "Standing charge", 69, "days", new BigDecimal("1.6"), new BigDecimal("56"));
        final List<Taxes> taxesLines = new ArrayList<>();
        taxesLines.add(taxes);
        final Vat vat1 = new Vat(1, 1, 0, 60, 20, new BigDecimal("150"));
        final Vat vat2 = new Vat(2, 1, 0, 40, 20, new BigDecimal("50"));
        final Vat vat3 = new Vat(3, 0, 1, 100, 20, new BigDecimal("100"));
        final List<Vat> vatLines = new ArrayList<>();
        vatLines.add(vat1);
        vatLines.add(vat2);
        vatLines.add(vat3);

        Assertions.assertEquals(user, generatedInvoice.getConsumer(), "User does not match");
        Assertions.assertEquals(invoiceLines.size(), generatedInvoice.getLines().size(), "Lines does not match");
        Assertions.assertEquals(taxesLines.size(), generatedInvoice.getTaxesLines().size(),
                "Taxes lines does not match");
        Assertions.assertEquals(vatLines.size(), generatedInvoice.getVatsLines().size(), "Vat lines does not match");
        Assertions.assertEquals(new BigDecimal("193.53"), generatedInvoice.getTotalAmount(),
                "Total amount does not match");
        Assertions.assertEquals(new BigDecimal("208.05"),
                generatedInvoice.getTotalAmountWithVat().setScale(2, RoundingMode.HALF_UP),
                "Total amount with vat does not match");

    }
}
