package com.github.methodia.minibilling;

import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


////////////////////////////////////////////////////////////////////////
public class CurrencyWebCalculator implements CurrencyCalculator {

    private final String key;

    public CurrencyWebCalculator(final String key) {
        this.key = key;
    }

    public BigDecimal calculateTo(final String currency, final BigDecimal amount) {
        BigDecimal result = BigDecimal.ZERO;
        if (!currency.equals("EUR")) {

            final String urlLink =
                    "https://api.apilayer.com/currency_data/convert?to=" + currency + "&from=EUR&amount=" + amount;
            final HttpClient client = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlLink)).header("apiKey", key)
                    .build();
            try {
                client.send(request, HttpResponse.BodyHandlers.ofString());
                final HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());
                final String body = response.body();
                final JSONObject obj = new JSONObject(body);
                result = obj.getBigDecimal("result");
                return result;
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public BigDecimal calculate(final BigDecimal amount, final String fromCurrency, final String toCurrency) {
        BigDecimal result = BigDecimal.ZERO;
        final String urlLink =
                "https://api.apilayer.com/currency_data/convert?to=" + toCurrency + "&from=" + fromCurrency + "&amount="
                        + amount;
        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlLink)).header("apiKey", key)
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            final HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            final String body = response.body();
            final JSONObject obj = new JSONObject(body);
            result = obj.getBigDecimal("result");
            return result;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }
    }
}
