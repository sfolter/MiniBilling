package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Todor Todorov
 * @Date 02.09.2022
 * Methodia Inc.
 */
public class InvoiceGeneratorTest {
    @Test
    public void generateInvoice() throws IOException, ParseException, org.json.simple.parser.ParseException {
    final ArrayList<Price> priceList = new ArrayList<>();
    priceList.add(new Price("gas", LocalDate.of(2022, 1, 7), LocalDate.of(2022, 6, 8), new BigDecimal("1.5")));
    final User user = new User("Ivan", "1",priceList , 1);
    final Reading firstReading = new Reading(user, "gas",
            ZonedDateTime.of(2022, 2, 2, 2, 2, 2, 2, ZoneId.of("GMT")), new BigDecimal("200"));
    final Reading secondReading = new Reading( user, "gas",
            ZonedDateTime.of(2022, 5, 2, 2, 2, 2, 2, ZoneId.of("GMT")), new BigDecimal("250"));
    final Collection<Reading> readings = new ArrayList<>();
    readings.add(firstReading);
    readings.add(secondReading);
    final MeasurementGenerator measurementGenerator = new MeasurementGenerator();
    final Collection<Measurement> measurements = measurementGenerator.generate(user, readings);
    final Collection<Price> prices = new ArrayList<>();
    prices.add(new Price("gas", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 8, 2), new BigDecimal("2")));
    final String yearMonthStr = "22-06";
    final String currency = "EUR";
    final int taxedAmountPercentage = 60;
    final InvoiceGenerator invoiceGenerator = new InvoiceGenerator(new CurrencyConvertor());
    final Invoice generatedInvoice = invoiceGenerator.generate(user, measurements, prices, yearMonthStr,
            currency);
    final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("436"),
            LocalDateTime.of(2021, 1, 1, 14, 40, 0),
            LocalDateTime.of(2021, 3, 11, 7, 0, 0), "gas", new BigDecimal("1.8"), 1, new BigDecimal("401"));
    final List<InvoiceLine> invoiceLines = new ArrayList<>();
    invoiceLines.add(invoiceLine);
    final TaxesLine taxes = new TaxesLine(1, 0, "Standing charge", 69, "days", new BigDecimal("1.6"), new BigDecimal("56"));
    final List<TaxesLine> taxesLines = new ArrayList<>();
    taxesLines.add(taxes);
    final VatLine vat1 = new VatLine(1, 1, 0, new BigDecimal(60), new BigDecimal(20), new BigDecimal("150"));
    final VatLine vat2 = new VatLine(2, 1, 0, new BigDecimal(40), new BigDecimal(20), new BigDecimal("50"));
    final VatLine vat3 = new VatLine(3, 0, 1, new BigDecimal(100), new BigDecimal(20), new BigDecimal("100"));
    final List<VatLine> vatLines = new ArrayList<>();
    vatLines.add(vat1);
    vatLines.add(vat2);
    vatLines.add(vat3);

    Assertions.assertEquals(user, generatedInvoice.getConsumer(), "User does not match");
    Assertions.assertEquals(invoiceLines.size(), generatedInvoice.getLines().size(), "Lines does not match");
    Assertions.assertEquals(taxesLines.size(), generatedInvoice.getTaxesLines().size(),
            "Taxes lines does not match");
    Assertions.assertEquals(vatLines.size(), generatedInvoice.getVatLines().size(), "Vat lines does not match");
    Assertions.assertEquals(new BigDecimal("193.53"), generatedInvoice.getTotalAmount(),
            "Total amount does not match");
    Assertions.assertEquals(new BigDecimal("208.05"),
            generatedInvoice.getTotalAmountWithVat().setScale(2, RoundingMode.HALF_UP),
            "Total amount with vat does not match");

}
}
