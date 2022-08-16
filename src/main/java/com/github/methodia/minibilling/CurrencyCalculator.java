package com.github.methodia.minibilling;

import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


////////////////////////////////////////////////////////////////////////
public class CurrencyCalculator {
    private final String key;

    public CurrencyCalculator(String key) {
        this.key = key;
    }

    public BigDecimal calculateTo(String currency, BigDecimal amount){
        BigDecimal result=BigDecimal.ZERO;
        if (!currency.equals("EUR")) {

            String urlLink = "https://api.apilayer.com/currency_data/convert?to=" + currency + "&from=EUR&amount=" + amount;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlLink)).header("apiKey", key)
                    .build();
            try {
                client.send(request, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());
                String responceBody = response.body();
                JSONObject obj = new JSONObject(responceBody);
                result = obj.getBigDecimal("result");
                return result;
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
