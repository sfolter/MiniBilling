package com.github.methodia.minibilling;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CurrencyGeneratorTest {

    @Test
    void Sample1() throws IOException, ParseException {

        String currency = "EUR";
        CurrencyConvertor currencyConvertor = new CurrencyConvertor(currency);
        BigDecimal convertedCurrency = currencyConvertor.generateCurrency();
        Assertions.assertEquals(Main.CURRENCY, convertedCurrency,"error");
    }
}
