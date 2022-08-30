package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VatPercentageGenerator {

    BigDecimal firstPercentage;
    BigDecimal secondPercentage;

    public List<BigDecimal> percentageGenerate(BigDecimal firstPercentage, BigDecimal secondPercentage) {
        List<BigDecimal> percentages = new ArrayList<>();
        percentages.add(firstPercentage);
        percentages.add(secondPercentage);
        return percentages;
    }
}
