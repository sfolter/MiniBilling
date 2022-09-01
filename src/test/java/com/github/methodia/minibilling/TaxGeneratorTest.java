package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaxGeneratorTest {

    @Test
    void generateTax() {
        BigDecimal taxAmount = new BigDecimal("100");
        LocalDateTime ldtStart = LocalDateTime.of(2021, 3, 15, 13, 23, 12);
        LocalDateTime ldtEnd = LocalDateTime.of(2021, 3, 17, 15, 45, 28);
        InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("100"),
                ldtStart, ldtEnd, "gas", new BigDecimal("2"), 1, new BigDecimal("45"));
        TaxGenerator taxGenerator = new TaxGenerator();
        BigDecimal currencyValue=new BigDecimal("2.50");
        Tax tax = taxGenerator.generate(invoiceLine,taxAmount,currencyValue,  0);

        List<Integer> taxLineExample = new ArrayList<>();
        taxLineExample.add(1);
        Assertions.assertEquals(taxLineExample, tax.getLines(), "Wrong Lines");
        Assertions.assertEquals(new BigDecimal("200"), tax.getAmount(), "Wrong Amount");
        Assertions.assertEquals(1, tax.getIndex(), "Wrong index");




    }
}
