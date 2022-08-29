package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExampleInputInformation {

    public static List<VatPercentage> vatPercentages() {
        final List<VatPercentage> vatPercentages = new ArrayList<>();
        final VatPercentage percentage1 = new VatPercentage(new BigDecimal("60"), new BigDecimal("20"));
        final VatPercentage percentage2 = new VatPercentage(new BigDecimal("40"), new BigDecimal("10"));
        vatPercentages.add(percentage1);
        vatPercentages.add(percentage2);
        return vatPercentages;
    }

    public List<Vat> vatInfo(final List<VatPercentage> percentages) {
        final List<Vat> vatInfo = new ArrayList<>();
        return vatInfo;
    }
}
