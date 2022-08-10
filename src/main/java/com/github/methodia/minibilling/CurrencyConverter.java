package com.github.methodia.minibilling;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyConverter {

    private static String makeGetRequest() {
        try {
            String urlLink = "https://api.apilayer.com/fixer/convert?to=GBP&from=Eur&amount=15";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlLink)).header("apiKey", "GtSaOVN01I9qY015hvRRdhJpX7vBtHze")
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
    /*{
    "success": true,
    "query": {
        "from": "EUR",
        "to": "GBP",
        "amount": 15
    },
    "info": {
        "timestamp": 1660115344,
        "rate": 0.845291
    },
    "date": "2022-08-10",
    "result": 12.679365
}
*/

}
