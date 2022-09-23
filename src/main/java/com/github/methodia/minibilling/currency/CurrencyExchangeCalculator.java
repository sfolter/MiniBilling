package com.github.methodia.minibilling.currency;

import com.github.methodia.minibilling.currency.CurrencyCalculator;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyExchangeCalculator implements CurrencyCalculator {
    final String apiKey;

    public CurrencyExchangeCalculator(final String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public BigDecimal calculate(final BigDecimal amount, final String fromCurrency, final String toCurrency) {
        final String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + fromCurrency;
        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request1 = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        try {
            client.send(request1, HttpResponse.BodyHandlers.ofString());
            final HttpResponse<String> response =
                    client.send(request1, HttpResponse.BodyHandlers.ofString());
            final String responceBody = response.body();
            final JSONObject obj = new JSONObject(responceBody);
            final JSONObject conversionRates = obj.getJSONObject("conversion_rates");
            final BigDecimal result = conversionRates.getBigDecimal(toCurrency);
            return amount.multiply(result);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
