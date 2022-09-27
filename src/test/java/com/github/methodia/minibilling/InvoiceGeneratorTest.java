package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.Invoice;
import com.github.methodia.minibilling.entityClasses.Price;
import com.github.methodia.minibilling.entityClasses.PriceList;
import com.github.methodia.minibilling.entityClasses.Reading;
import com.github.methodia.minibilling.entityClasses.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvoiceGeneratorTest {

    @Test
    void generateInvoice() {
        final List<Price> prices = new ArrayList<>();
        final String priceStartLdt = "2020-01-01";
        final String priceEndLdt = "2022-12-12";
        prices.add(new Price("gas", LocalDate.parse(priceStartLdt),
                LocalDate.parse(priceEndLdt), new BigDecimal("2.50")));
        final PriceList priceList = new PriceList(1, prices);
        final List<Reading> readingList = new ArrayList<>();
        final String time = "2021-03-17T13:20:00+03:00";
        readingList.add(new Reading("1", "gas", ZonedDateTime.parse(time), new BigDecimal("2.50")));
        final User user = new User("2", "Georgi Ivanov Simeonov", priceList, readingList, "EUR");

        final Collection<Measurement> measurementCollection = new ArrayList<>();
        measurementCollection.add(new Measurement(LocalDateTime.of(2021, 4, 4, 11, 12, 13),
                LocalDateTime.of(2021, 4, 24, 20, 20, 4), new BigDecimal("500"), user));
        measurementCollection.add(new Measurement(LocalDateTime.of(2021, 4, 24, 20, 20, 5),
                LocalDateTime.of(2021, 5, 4, 20, 20, 4), new BigDecimal("600"), user));

        final LocalDateTime borderLDT = LocalDateTime.of(2022, 7, 4, 4, 4, 4);

        final List<VatPercentages> vatPercentageList = new ArrayList<>();
        vatPercentageList.add(new VatPercentages(new BigDecimal("60"), new BigDecimal("20")));
        vatPercentageList.add(new VatPercentages(new BigDecimal("40"), new BigDecimal("10")));

        final BigDecimal currencyRate = new BigDecimal("2");
        final CurrencyConverter currencyConverter = val -> currencyRate;


        final InvoiceGenerator invoiceGenerator = new InvoiceGenerator(currencyConverter);
        final Invoice invoice = invoiceGenerator.generate(user, measurementCollection, borderLDT, vatPercentageList);


        Assertions.assertEquals(10000, invoice.getDocNumber(), "DocumentNumber is incorrect.");
        Assertions.assertEquals("Georgi Ivanov Simeonov", invoice.getConsumer(), "Consumer is incorrect.");
        Assertions.assertEquals("2", invoice.getReference(), "Reference is incorrect.");
        Assertions.assertEquals(new BigDecimal("5592.80"), invoice.getTotalAmount(), "TotalAmount is incorrect");
        Assertions.assertEquals(new BigDecimal("6491.36"), invoice.getTotalAmountWithVat(),
                "TotalAmountWithVat is incorrect");

        Assertions.assertEquals(2, invoice.getLines().size(), "InvoiceLinesList's size is incorrect.");

    }
}
