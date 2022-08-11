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

    protected static BigDecimal getCurrencyValue(User user) {
        try {
            String getRequest= httpGetRequest(user);
            JSONObject obj=new JSONObject(getRequest);
            System.out.println(obj);
            JSONObject result= obj.getJSONObject("info");
            return new BigDecimal( String.valueOf(result.getString("rate")))
                    .setScale(2, RoundingMode.HALF_UP);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }



    }
    private static String httpGetRequest(User user) {
        try {
            String urlLink = "https://api.apilayer.com/fixer/convert?to=BGN&from="+user.getCyrrency()+"&amount=1";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlLink)).header("apiKey", "GtSaOVN01I9qY015hvRRdhJpX7vBtHze")
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
