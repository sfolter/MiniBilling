package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.InvoiceLine;
import com.github.methodia.minibilling.entityClasses.Tax;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("AutoBoxing")
public class TaxGeneratorTest {

    @Test
    void generateTax() {
        final BigDecimal taxAmount = new BigDecimal("100");
        final LocalDateTime ldtStart = LocalDateTime.of(2021, 3, 15, 13, 23, 12);
        final LocalDateTime ldtEnd = LocalDateTime.of(2021, 3, 17, 15, 45, 28);
        final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("100"),
                ldtStart, ldtEnd, "gas", new BigDecimal("2"), 1, new BigDecimal("45"));
        final TaxGenerator taxGenerator = new TaxGenerator();
        final BigDecimal currencyValue=new BigDecimal("2.50");
        final Tax tax = taxGenerator.generate(invoiceLine,taxAmount,currencyValue,  0);

        final List<Integer> taxLineExample = new ArrayList<>();
        taxLineExample.add(1);
        Assertions.assertEquals(taxLineExample, tax.getLines(), "Wrong Lines");
        Assertions.assertEquals(new BigDecimal("500.00"), tax.getAmount(), "Wrong Amount");
        Assertions.assertEquals(1, tax.getIndex(), "Wrong index");




    }
}
