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
        final User user = new User("Ivan", "1", 1);
        final Reading firstReading = new Reading(ZonedDateTime.of(2022, 2, 2, 2, 2, 2, 2, ZoneId.of("GMT")),
                new BigDecimal("200"), user, "gas");
        final Reading secondReading = new Reading(ZonedDateTime.of(2022, 5, 2, 2, 2, 2, 2, ZoneId.of("GMT")),
                new BigDecimal("250"), user, "gas");
        final List<Reading> readings = new ArrayList<>();
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
        final Invoice generatedInvoice = invoiceGenerator.generate();
        final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("436"),
                LocalDateTime.of(2021, 1, 1, 14, 40, 0),
                LocalDateTime.of(2021, 3, 11, 7, 0, 0), "gas", new BigDecimal("1.8"), 1, new BigDecimal("401"));
        final List<InvoiceLine> invoiceLines = new ArrayList<>();
        invoiceLines.add(invoiceLine);
        final List<Integer> linesInTaxes = new ArrayList<>();
        final Taxes taxes = new Taxes(1, linesInTaxes, "Standing charge", 69, "days", new BigDecimal("1.6"), new BigDecimal("56"));
        final List<Taxes> taxesLines = new ArrayList<>();
        taxesLines.add(taxes);
        final Vat vat1 = new Vat(1, 1, 0, 60, 20, new BigDecimal("150"));
        final Vat vat2 = new Vat(2, 1, 0, 40, 20, new BigDecimal("50"));
        final Vat vat3 = new Vat(3, 0, 1, 100, 20, new BigDecimal("100"));
        final List<Vat> vatLines = new ArrayList<>();
        vatLines.add(vat1);
        vatLines.add(vat2);
        vatLines.add(vat3);

        Assertions.assertEquals("Ivan", generatedInvoice.getConsumer(), "User does not match");
        Assertions.assertEquals(invoiceLines.size(), generatedInvoice.getLines().size(), "Lines does not match");
        Assertions.assertEquals(taxesLines.size(), generatedInvoice.getTaxesLines().size(),
                "Taxes lines does not match");
        Assertions.assertEquals(vatLines.size(), generatedInvoice.getVatsLines().size(), "Vat lines does not match");
        Assertions.assertEquals(new BigDecimal("123.94"), generatedInvoice.getTotalAmount(),
                "Total amount does not match");
        Assertions.assertEquals(new BigDecimal("146.69"),
                generatedInvoice.getTotalAmountWithVat().setScale(2, RoundingMode.HALF_UP),
                "Total amount with vat does not match");
        Assertions.assertEquals(1, generatedInvoice.getLines().get(0).getIndex(), "Index in line does not match");
        Assertions.assertEquals(new BigDecimal("50"), generatedInvoice.getLines().get(0).getQuantity(),
                "Quantity in line does not match");
        Assertions.assertEquals(LocalDateTime.of(2022, 2, 2, 2, 2, 2, 2), generatedInvoice.getLines().get(0).getStart(),
                "Start date in line does not match");
        Assertions.assertEquals(LocalDateTime.of(2022, 5, 2, 2, 2, 2, 2), generatedInvoice.getLines().get(0).getEnd(),
                "End date in line does not match");
        Assertions.assertEquals("gas", generatedInvoice.getLines().get(0).getProduct(),
                "Product in line does not match");
        Assertions.assertEquals(new BigDecimal("2"), generatedInvoice.getLines().get(0).getPrice(),
                "Price in line does not match");
        Assertions.assertEquals(1, generatedInvoice.getLines().get(0).getPriceList(),
                "Price list in line does not match");
        Assertions.assertEquals(new BigDecimal("51.13"), generatedInvoice.getLines().get(0).getAmount(),
                "Amount in line does not match");
        Assertions.assertEquals(1, generatedInvoice.getTaxesLines().get(0).getIndex(), "Index in tax does not match");
        Assertions.assertEquals(1, generatedInvoice.getTaxesLines().get(0).getLines().size(), "Lines in tax does not match");
        Assertions.assertEquals("Standing charge", generatedInvoice.getTaxesLines().get(0).getName(),
                "Name in tax does not match");
        Assertions.assertEquals(89, generatedInvoice.getTaxesLines().get(0).getQuantity(),
                "Quantity in tax does not match");
        Assertions.assertEquals("days", generatedInvoice.getTaxesLines().get(0).getUnit(),
                "Unit in tax does not match");
        Assertions.assertEquals(new BigDecimal("1.6"),
                generatedInvoice.getTaxesLines().get(0).getPrice().setScale(1, RoundingMode.HALF_UP),
                "Price in tax does not match");
        Assertions.assertEquals(new BigDecimal("72.81"), generatedInvoice.getTaxesLines().get(0).getAmount(),
                "Amount in tax does not match");
        Assertions.assertEquals(1, generatedInvoice.getVatsLines().get(0).getIndex(), "Index in vat does not match");
        Assertions.assertEquals(1, generatedInvoice.getVatsLines().get(0).getLines(), "Lines in vat does not match");
        Assertions.assertEquals(0, generatedInvoice.getVatsLines().get(0).getTaxes(), "Tax in vat does not match");
        Assertions.assertEquals(60, generatedInvoice.getVatsLines().get(0).getTaxedAmountPercentage(),
                "Taxed amount percentage does not match");
        Assertions.assertEquals(20, generatedInvoice.getVatsLines().get(0).getPercentage(),
                "Percentage does not match");
        Assertions.assertEquals(new BigDecimal("6.14"), generatedInvoice.getVatsLines().get(0).getAmount(),
                "Amount in vat does not match");
    }
}
