package com.github.methodia.minibilling;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyExchangeRate {
    public static JsonObject currencyExchangeRate() throws IOException {
        String url_str = "https://v6.exchangerate-api.com/v6/03049edfdad9865e25818961/latest/BGN";

        // Making Request
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();

        // Convert to JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();

        return jsonobj;
    }
}
