package com.github.methodia.minibilling;


import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyConverter {
    public BigDecimal convertTo() throws IOException, InterruptedException {
        String url = "https://api.apilayer.com/currency_data/convert?to=GBP&from=USD&amount=30";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("apikey", "NKGQsd93JumbLiPqOu3ZmBI8PSQgHZbu").build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        JSONObject json = new JSONObject(responseBody);
        return (BigDecimal) json.get("");


    }
}