package com.example.SpringBatchExample;

import com.example.SpringBatchExample.models.Tax;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class InvoiceTaxGeneratorTest {


    @Test
    void taxTest() {

        final List<Integer> lines = new ArrayList<>();
        lines.add(1);
        final Tax tax = new Tax(2, lines, new BigDecimal("1.5"), new BigDecimal("0.1"), new BigDecimal("200"));
        Assertions.assertEquals(new BigDecimal("200").setScale(0, RoundingMode.HALF_UP), tax.getAmount(),
                "Amount is incorrect");
        Assertions.assertEquals(new BigDecimal("1.5").setScale(1, RoundingMode.HALF_UP), tax.getQuantity(),
                "Quantity is incorrect");
        Assertions.assertEquals(lines.get(0), tax.getLines().get(0),
                "Lines are not correct");
        Assertions.assertEquals(new BigDecimal("0.1").setScale(1, RoundingMode.HALF_UP), tax.getPrice(),
                "Price is incorrect");
        Assertions.assertEquals(2, tax.getIndex(),
                "Price is incorrect");
    }
}
