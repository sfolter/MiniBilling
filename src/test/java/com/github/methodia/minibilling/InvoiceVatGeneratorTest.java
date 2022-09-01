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
        List<Vat> vats = getVats();

        Assertions.assertEquals(2, vats.size(), "The size is different");
    }

    @Test
    void testVatAmount() {
        List<Vat> vats = getVats();

        Assertions.assertEquals(new BigDecimal("12.00").setScale(2, RoundingMode.HALF_UP), vats.get(0).getAmount(),
                "Amount is incorrect");
        Assertions.assertEquals(new BigDecimal("4.00").setScale(2, RoundingMode.HALF_UP), vats.get(1).getAmount(),
                "Amount is incorrect");

    }


    @Test
    void testTaxWithVat() {
        List<Vat> vats = getVats();
        Assertions.assertEquals(new BigDecimal("12.00").setScale(2, RoundingMode.HALF_UP), vats.get(0).getAmount(),
                "Amount is incorrect");
    }

    private List<Vat> getVats() {
        final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("200"),
                LocalDateTime.of(2021, Month.APRIL, 4, 5, 13),
                LocalDateTime.of(2021, Month.MAY, 10, 6, 17),
                "gas",
                new BigDecimal("1.4"), 2, new BigDecimal("100"));

        List<Tax> taxList = new ArrayList<>();
        List<Integer> lines = new ArrayList<>();

        taxList.add(new Tax(1, lines, new BigDecimal("1.8"), new BigDecimal("0.4"), new BigDecimal("100")
        ));
        InvoiceVatGenerator vatGenerator = new InvoiceVatGenerator();
        List<Vat> vats = vatGenerator.generateVat(invoiceLine, taxList);
        return vats;
    }


}
