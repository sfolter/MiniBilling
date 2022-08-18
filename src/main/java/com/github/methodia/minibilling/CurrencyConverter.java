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

    private String key;

    public CurrencyConverter(String key) {
        this.key = key;
    }

    public BigDecimal getCurrencyValue(String currency) {
        try {
            String getRequest = httpGetRequest (currency);
            JSONObject obj = new JSONObject (getRequest);
            return new BigDecimal (obj.getJSONObject ("info").getString ("rate"))
                    .setScale (2, RoundingMode.HALF_UP);
        } catch (JSONException e) {
            throw new RuntimeException (e);
        }



    }

    private String httpGetRequest(String currency) {
        try {
            String urlLink = "https://api.apilayer.com/fixer/convert?to=" + currency + "&from=BGN&amount=1";
            HttpClient client = HttpClient.newHttpClient ();
            HttpRequest request = HttpRequest.newBuilder ()
                    .uri (URI.create (urlLink)).header ("apiKey", key)
                    .build ();
            client.send (request, HttpResponse.BodyHandlers.ofString ());
            HttpResponse<String> response =
                    client.send (request, HttpResponse.BodyHandlers.ofString ());
            return response.body ();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException (e);
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
