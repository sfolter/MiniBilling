package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.List;

public class InvoiceTaxGeneratorTest {


@Test
    void taxAmountTest(){

    List<Integer> lines = new ArrayList<>();
    lines.add(1);
    Tax tax = new Tax(2, lines, new BigDecimal("1.5"), new BigDecimal("0.1"), new BigDecimal("200"));
    Assertions.assertEquals(BigDecimal.valueOf(200).setScale(0, RoundingMode.HALF_UP), tax.getAmount(),
            "Amount is incorrect");

}
}
