package com.github.methodia.minibilling;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyGenerator {

    private String currency;

    private String key;

    public CurrencyGenerator(String currency, String key) {
        this.currency = currency;
        this.key = key;
    }

    public BigDecimal generateCurrency() throws IOException, ParseException {
        ExchangeRateAPI exchangeRateAPI = new ExchangeRateAPI(key);
        JSONObject exchange = exchangeRateAPI.exchange();
        JSONObject conversion_rates = (JSONObject) exchange.get(("conversion_rates"));
        String currencyString = String.valueOf(conversion_rates.get(currency));
        BigDecimal currencyValue = new BigDecimal(currencyString);

        return currencyValue;
    }
}
