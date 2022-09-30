package com.example.SpringBatchExample;

import com.example.SpringBatchExample.generators.InvoiceLineGenerator;
import com.example.SpringBatchExample.models.InvoiceLine;
import com.example.SpringBatchExample.models.Price;
import com.example.SpringBatchExample.models.PriceList;
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

public class InvoiceLineGeneratorTest {

    @Test
    void testInvoiceLine() {
        final InvoiceLine invoiceLine = getInvoiceLine();

        Assertions.assertEquals(new BigDecimal("10234").setScale(2, RoundingMode.HALF_UP).stripTrailingZeros(),
                invoiceLine.getAmount().stripTrailingZeros(),
                "Amount is incorrect");
        Assertions.assertEquals(1, invoiceLine.getIndex(),
                "Amount is incorrect");


    }

    private static InvoiceLine getInvoiceLine() {
        final InvoiceLineGenerator invoiceLineGenerator = new InvoiceLineGenerator();

        final List<Price> prices = new ArrayList<>();
        final Price price = new Price("gas", ZonedDateTime.of(2021, 1, 5, 0, 0, 0, 0, ZoneId.of("Z")),
                ZonedDateTime.of(2021, 2, 15, 0, 0, 0, 0, ZoneId.of("Z")), new BigDecimal("200"));
        prices.add(price);
        final PriceList priceList = new PriceList(1, prices);
        final User user = new User("Marko", 2, 1, priceList);

        final QuantityPricePeriod qpp = new QuantityPricePeriod(LocalDateTime.of(2021, Month.MAY, 10, 13, 40),
                LocalDateTime.of(2021, Month.AUGUST, 11, 18, 50), price, new BigDecimal("100"),
                user);
        return invoiceLineGenerator.generateInvoiceLine(1, qpp, user);
    }
}
