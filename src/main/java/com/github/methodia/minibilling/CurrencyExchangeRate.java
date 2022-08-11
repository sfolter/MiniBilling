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
        String url_str = "https://v6.exchangerate-api.com/v6/03049edfdad9865e25818961/latest/BGN";

        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();

        JSONParser jp = new JSONParser();
        JSONObject root = (JSONObject) jp.parse(new InputStreamReader((InputStream) request.getContent()));
//        JsonObject jsonobj = root.getAsJsonObject();

        return root;
    }
}
