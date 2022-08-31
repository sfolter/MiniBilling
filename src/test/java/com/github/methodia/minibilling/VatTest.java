package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

public class VatTest {

    @Test
    public void getVat() {
        final Vat vat = new Vat(1, 1, 0, 60, 20, new BigDecimal("150"));

        Assertions.assertEquals(1, vat.getIndex(), "Vat inex did not match the expected.");
        Assertions.assertEquals(1, vat.getLines(), "Lines did not match the expected.");
        Assertions.assertEquals(0, vat.getTaxes(), "Taxes did not match the expected.");
        Assertions.assertEquals(60, vat.getTaxedAmountPercentage(),
                "TaxedAmountPercentage did not match the expected.");
        Assertions.assertEquals(20, vat.getPercentage(), "Percentage did not match the expected.");
        Assertions.assertEquals(new BigDecimal("150"), vat.getAmount(), "Amount did not match the expected.");
    }
}
