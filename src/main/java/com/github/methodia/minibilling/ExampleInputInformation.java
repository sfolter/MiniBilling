package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExampleInputInformation {

    public static List<VatPercentage> vatPercentages() {
        List<VatPercentage> vatPercentages = new ArrayList<>();
        VatPercentage percentage1 = new VatPercentage(BigDecimal.valueOf(0.6), BigDecimal.valueOf(0.2));
        VatPercentage percentage2 = new VatPercentage(BigDecimal.valueOf(0.4), BigDecimal.valueOf(0.1));
        vatPercentages.add(percentage1);
        vatPercentages.add(percentage2);
        return vatPercentages;
    }

    public List<Vat> vatInfo(List<VatPercentage> percentages) {
        List<Vat> vatInfo = new ArrayList<>();
        return vatInfo;
    }
}
