package com.github.methodia.minibilling;


import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyConverter {

    public String convertTo(String convertFrom, String convertTo)  {
        String url = "https://api.apilayer.com/currency_data/convert?to="+convertTo+"&from="+ convertFrom+"&amount=1";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("apikey", "NKGQsd93JumbLiPqOu3ZmBI8PSQgHZbu").build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            JSONObject json = new JSONObject(responseBody);
            return String.valueOf(json.get("result"));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}