package com.github.methodia.minibilling;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExchangeRateAPI {

    private final String key;

    public ExchangeRateAPI(String key) {
        this.key = key;
    }

    public JSONObject exchange() throws IOException, ParseException {
        String url_str = "https://v6.exchangerate-api.com/v6/" + key + "/latest/BGN";
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        JSONParser jp = new JSONParser();
        JSONObject root = (JSONObject) jp.parse(new InputStreamReader((InputStream) request.getContent()));
        return root;
    }
}
