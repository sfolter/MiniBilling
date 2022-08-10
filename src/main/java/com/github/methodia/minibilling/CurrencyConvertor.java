package com.github.methodia.minibilling;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class CurrencyConvertor {
    public void convertCurrency(JSONObject json, String currency) {
        JSONArray jsonArray = (JSONArray) json.get("conversion_rates");
        HashMap<String, Object> jsonLines = (HashMap<String, Object>) jsonArray.toList().get(1);
        String lineEnd = (String) jsonLines.get(currency);
    }
}
