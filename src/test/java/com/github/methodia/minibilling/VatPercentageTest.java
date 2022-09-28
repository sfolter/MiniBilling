package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class VatPercentageTest {

    @Test
    public void getVatPercentage(){
        final VatPercentage vatPercentage = new VatPercentage(60,40);

        Assertions.assertEquals(60, vatPercentage.taxedAmountPercentage, "Taxed amount percentage does not match");
        Assertions.assertEquals(40, vatPercentage.getPercentage(), "Percentage does not match");
    }
}
