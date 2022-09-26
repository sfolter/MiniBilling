package com.github.methodia.minibilling;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;


public class CurrencyRate implements CurrencyConverter {

    @Override
    public BigDecimal getCurrencyValue(final String currency) {
        try {
            final URL url = new URL("https://api.exchangerate.host/convert?from=BGN&to="+currency);
            final HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            final JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
            return root.getAsJsonObject().get("info").getAsJsonObject().get("rate")
                    .getAsBigDecimal().setScale(2, RoundingMode.HALF_UP);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

