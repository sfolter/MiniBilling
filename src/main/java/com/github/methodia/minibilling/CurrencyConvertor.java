package com.github.methodia.minibilling;


import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyConvertor {
    public BigDecimal convertCurrency(final String toCurrency) throws IOException, ParseException {
        final JSONObject json = CurrencyExchangeRate.currencyExchangeRate();
        final JSONObject conversionRates = (JSONObject) json.get("conversion_rates");
        final String currencyRateStr = conversionRates.get(toCurrency).toString();
        final BigDecimal currencyRate = new BigDecimal(currencyRateStr);
        return currencyRate;

    }
}
