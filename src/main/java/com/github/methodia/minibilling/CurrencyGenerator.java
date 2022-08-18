package com.github.methodia.minibilling;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyGenerator {

    private final String currency;

    private final String key;

    public CurrencyGenerator(String currency, String key) {
        this.currency = currency;
        this.key = key;
    }

    public BigDecimal generateCurrency() throws IOException, ParseException {
        ExchangeRateAPI exchangeRateAPI = new ExchangeRateAPI(key);
        JSONObject exchange = exchangeRateAPI.exchange();
        JSONObject conversion_rates = (JSONObject) exchange.get(("conversion_rates"));
        BigDecimal currencyValue = new BigDecimal(String.valueOf(conversion_rates.get(currency)));
        return currencyValue;
    }
}
