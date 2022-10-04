package com.example.SpringBatchExample;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyConverter implements Converter {

    @Override
    public BigDecimal convertTo(final String convertFrom, final String convertTo, final BigDecimal amount) {
        final String url =
                "https://api.apilayer.com/currency_data/convert?to=" + convertTo + "&from=" + convertFrom + "&amount=1";

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("apikey", "NKGQsd93JumbLiPqOu3ZmBI8PSQgHZbu").build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final String responseBody = response.body();

            final JSONObject json = new JSONObject(responseBody);
            return (BigDecimal) json.get("result");
        } catch (IOException | InterruptedException | JSONException e) {
            throw new RuntimeException(e);
        }

    }
}