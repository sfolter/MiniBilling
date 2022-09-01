package com.github.methodia.minibilling;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyGeneratorTest {

    @Test
    public void getCurrency() throws IOException, ParseException {
        final String currency = "EUR";
        final String key = "3b14c37cbcca1d0ff2fca003";
        final CurrencyGenerator currencyGenerator = new CurrencyGenerator(currency, key);
        final BigDecimal generatedCurrency = currencyGenerator.generateCurrency();

        Assertions.assertEquals(new BigDecimal("0.51"), generatedCurrency.setScale(2, RoundingMode.HALF_UP),
                "Currency value does not match");
        Assertions.assertEquals("EUR", currency, "Currency does not match");
    }
}
