package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class InvoiceLineGeneratorTest {

    @Test
    void testAmount() throws IOException, InterruptedException {
        InvoiceLineGenerator invoiceLineGenerator = new InvoiceLineGenerator();

        List<Price> prices = new ArrayList<>();
        Price price = new Price("gas", LocalDate.of(2021, Month.JANUARY, 5),
                LocalDate.of(2021, Month.FEBRUARY, 15), new BigDecimal("200"));
        prices.add(price);

        User user = new User("Marko", "2", 1, prices);

        QuantityPricePeriod qpp = new QuantityPricePeriod(LocalDateTime.of(2021, Month.MAY, 10, 13, 40),
                LocalDateTime.of(2021, Month.AUGUST, 11, 18, 50), price, new BigDecimal("100"),
                user);
        InvoiceLine invoiceLine = invoiceLineGenerator.generateInvoiceLine(1, qpp, user);

        Assertions.assertEquals(new BigDecimal("20000.00").setScale(2, RoundingMode.HALF_UP).stripTrailingZeros(), invoiceLine.getAmount().stripTrailingZeros(),
                "Amount is incorrect");

    }
}
