package com.github.methodia.minibilling;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyExchangeRate {
    public static JSONObject currencyExchangeRate() throws IOException, ParseException {
        final String urlStr = "https://v6.exchangerate-api.com/v6/03049edfdad9865e25818961/latest/BGN";

        final URL url = new URL(urlStr);
        final HttpURLConnection request = (HttpURLConnection) url.openConnection();

        final JSONParser jp = new JSONParser();


        return (JSONObject) jp.parse(new InputStreamReader((InputStream) request.getContent()));
    }
}
