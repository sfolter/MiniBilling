package com.github.methodia.minibilling;


import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyConvertor {
    public BigDecimal convertCurrency(String currency) throws IOException, ParseException {
        JSONObject json = CurrencyExchange.currencyExchangeRate();
        JSONObject conversion_rates = (JSONObject) json.get("conversion_rates");
        Double currencyRateInDouble = (Double) conversion_rates.get(currency);
        BigDecimal currencyRate = BigDecimal.valueOf(currencyRateInDouble);
        return currencyRate;

    }
}