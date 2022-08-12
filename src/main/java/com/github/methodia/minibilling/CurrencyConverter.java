package com.github.methodia.minibilling;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyConverter {

    String key=Main.ApiKey;

    public CurrencyConverter() {
        this.key = Main.ApiKey;
    }

    public static BigDecimal getCurrencyValue(String currency) {
        try {
            String getRequest= httpGetRequest(currency);
            JSONObject obj=new JSONObject(getRequest);
            return new BigDecimal(obj.getJSONObject("info").getString("quote"))
                    .setScale(2, RoundingMode.HALF_UP);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }



    }
    private static String httpGetRequest(String currency) {
        try {

            String urlLink = "https://api.apilayer.com/currency_data/convert?to="+currency+"&from=BGN&amount=1";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlLink)).header("apiKey",new CurrencyConverter().key )
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw  new RuntimeException(e);
        }
    }
    /*{
    "success": true,
    "query": {
        "from": "BGN",
        "to": "EUR",
        "amount":1
    },
    "info": {
        "timestamp": 1660115344,
        "rate": 0.51...
    },
    "date": "2022-08-10",
    "result": 0.51
}
*/



}
