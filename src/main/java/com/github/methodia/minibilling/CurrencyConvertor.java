package com.github.methodia.minibilling;


import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyConvertor {
    public BigDecimal convertCurrency(String currency) throws IOException, ParseException {
        JSONObject json = CurrencyExchangeRate.currencyExchangeRate();
        JSONObject conversion_rates = (JSONObject) json.get("conversion_rates");
        String currencyRateStr =  conversion_rates.get(currency).toString();
        BigDecimal currencyRate=new BigDecimal(currencyRateStr);
        return currencyRate;

    }
}
