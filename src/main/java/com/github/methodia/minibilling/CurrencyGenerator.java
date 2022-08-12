package com.github.methodia.minibilling;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyGenerator {

    String currency;

    public CurrencyGenerator(String currency) {
        this.currency = currency;
    }

    public BigDecimal generateCurrency() throws IOException, ParseException {
        ExchangeRateAPI exchangeRateAPI = new ExchangeRateAPI();
        JSONObject exchange = exchangeRateAPI.exchange();
        JSONObject conversion_rates = (JSONObject) exchange.get(("conversion_rates"));
        String currencyString = String.valueOf(conversion_rates.get(currency));
        BigDecimal currencyValue = new BigDecimal(currencyString);

        return currencyValue;
    }
}
