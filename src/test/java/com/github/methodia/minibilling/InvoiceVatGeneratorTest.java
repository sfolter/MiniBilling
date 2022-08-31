package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class InvoiceVatGeneratorTest {

    @Test
    void testAssertVatListSize() {

        final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("200"),
                LocalDateTime.of(2021, Month.APRIL, 4, 5, 13),
                LocalDateTime.of(2021, Month.MAY, 10, 6, 17),
                "gas",
                new BigDecimal("1.4"), 2, new BigDecimal("100")
        );

        List<Tax> taxList = new ArrayList<>();
        List<Integer> lines = new ArrayList<>();
        taxList.add(new Tax(1, lines, new BigDecimal("1.8"), new BigDecimal("0.4"), new BigDecimal("100")
        ));

        InvoiceVatGenerator invoiceVatGenerator = new InvoiceVatGenerator();
        List<Vat> vats = invoiceVatGenerator.generateVat(invoiceLine, taxList);
        Assertions.assertEquals(2, vats.size(), "The size is different");

    }

    @Test
    void testVatAmount() {
        final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("200"),
                LocalDateTime.of(2021, Month.APRIL, 4, 5, 13),
                LocalDateTime.of(2021, Month.MAY, 10, 6, 17),
                "gas",
                new BigDecimal("1.4"), 2, new BigDecimal("100")
        );


        List<Integer> lines = new ArrayList<>();
        List<Tax> taxList = new ArrayList<>();
        taxList.add(new Tax(1, lines, new BigDecimal("1.8"), new BigDecimal("0.4"), new BigDecimal("100")
        ));
        taxList.add(new Tax(2, lines, new BigDecimal("1.5"), new BigDecimal("0.1"), new BigDecimal("200")
        ));
        InvoiceVatGenerator vatGenerator = new InvoiceVatGenerator();
        List<Vat> vats1 = vatGenerator.generateVat(invoiceLine, taxList);

        Assertions.assertEquals(new BigDecimal("12.00").setScale(2, RoundingMode.HALF_UP), vats1.get(0).getAmount(),
                "Amount is incorrect");
        Assertions.assertEquals(new BigDecimal("4.00").setScale(2, RoundingMode.HALF_UP), vats1.get(1).getAmount(),
                "Amount is incorrect");

    }

    @Test
    void testTaxWithVat() {
        List<Tax> taxes = new ArrayList<>();
        List<Integer> lines = new ArrayList<>();
        lines.add(1);
        Tax tax = new Tax(1, lines, new BigDecimal("18.0"), new BigDecimal("1.4"), new BigDecimal("100"));
        taxes.add(tax);
        InvoiceVatGenerator invoiceVatGenerator = new InvoiceVatGenerator();
        List<Vat> vats = invoiceVatGenerator.taxWithVat(taxes);
        Assertions.assertEquals(new BigDecimal("20.00").setScale(2, RoundingMode.HALF_UP), vats.get(0).getAmount(),
                "Amount is incorrect");

    }


}
