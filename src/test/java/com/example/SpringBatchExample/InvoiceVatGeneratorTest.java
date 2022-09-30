package com.example.SpringBatchExample;

import com.example.SpringBatchExample.generators.InvoiceVatGenerator;
import com.example.SpringBatchExample.models.InvoiceLine;
import com.example.SpringBatchExample.models.Tax;
import com.example.SpringBatchExample.models.Vat;
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
        final List<Vat> vats = getVats();

        Assertions.assertEquals(2, vats.size(), "The size is different");
    }

    @Test
    void testVat() {
        final List<Vat> vats = getVats();

        final List<Integer> lines = new ArrayList<>();
        lines.add(1);

        Assertions.assertEquals(new BigDecimal("12.00").setScale(2, RoundingMode.HALF_UP), vats.get(0).getAmount(),
                "Amount is incorrect");
        Assertions.assertEquals(new BigDecimal("4.00").setScale(2, RoundingMode.HALF_UP), vats.get(1).getAmount(),
                "Amount is incorrect");
        Assertions.assertEquals(lines.get(0), vats.get(0).getLines().get(0),
                "Lines are not correct");

    }


    @Test
    void testTaxWithVat() {
        final List<Vat> vats = getVats();
        Assertions.assertEquals(new BigDecimal("12.00").setScale(2, RoundingMode.HALF_UP), vats.get(0).getAmount(),
                "Amount is incorrect");
    }

    private static List<Vat> getVats() {
        final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("200"),
                LocalDateTime.of(2021, Month.APRIL, 4, 5, 13),
                LocalDateTime.of(2021, Month.MAY, 10, 6, 17),
                "gas",
                new BigDecimal("1.4"), 2, new BigDecimal("100"));

        final List<Tax> taxList = new ArrayList<>();
        final List<Integer> lines = new ArrayList<>();

        taxList.add(new Tax(1, lines, new BigDecimal("1.8"), new BigDecimal("0.4"), new BigDecimal("100")
        ));
        final InvoiceVatGenerator vatGenerator = new InvoiceVatGenerator();
        return vatGenerator.generateVat(invoiceLine, taxList);
    }


}
