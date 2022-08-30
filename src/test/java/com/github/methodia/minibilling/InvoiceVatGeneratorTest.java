package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvoiceVatGeneratorTest {

    @Test
    void testAssertVatListSize() {

        final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal(200),
                LocalDateTime.of(2021, Month.APRIL, 4, 5, 13),
                LocalDateTime.of(2021, Month.MAY, 10, 6, 17),
                "gas",
                new BigDecimal(1.4), 2, new BigDecimal(100)
        );

        List<Tax> taxList = new ArrayList<>();
        List<Integer> lines = new ArrayList<>();
        taxList.add(new Tax(1, lines, new BigDecimal("1.8"), new BigDecimal("0.4"), new BigDecimal("100")
        ));

        InvoiceVatGenerator invoiceVatGenerator = new InvoiceVatGenerator();
        List<Vat> vats = invoiceVatGenerator.generateVat(invoiceLine, taxList);
        Assertions.assertEquals(2, vats.size(), "");
        testTaxWithVat(invoiceVatGenerator, invoiceLine, taxList);
        //testPercentages(invoiceVatGenerator, invoiceLine, taxList);

    }

    @Test
    private void testTaxWithVat(InvoiceVatGenerator invoiceVatGenerator, InvoiceLine invoiceLine, List<Tax> taxList) {
        List<Vat> vats = invoiceVatGenerator.taxWithVat(Collections.emptyList().size(), taxList);
        Assertions.assertEquals(BigDecimal.valueOf(12.00).setScale(2, RoundingMode.HALF_UP), vats.get(0).getAmount(),
                "");
    }

    @Test
    void testPercentages(InvoiceVatGenerator invoiceVatGenerator, InvoiceLine invoiceLine, List<Tax> taxes) {
        List<Percentages> vatPercentages = new ArrayList<>();
        Percentages percentage1 = new Percentages(BigDecimal.valueOf(0.6), BigDecimal.valueOf(0.2));
        Percentages percentage2 = new Percentages(BigDecimal.valueOf(0.4), BigDecimal.valueOf(0.1));
        vatPercentages.add(percentage1);
        vatPercentages.add(percentage2);
        List<Vat> vats = invoiceVatGenerator.generateVat(invoiceLine, taxes);
        final Percentages percentages = vatPercentages.get(0);

        Assertions.assertEquals(percentage1.getPercentage(), percentages.getPercentage(), "");


    }


}
