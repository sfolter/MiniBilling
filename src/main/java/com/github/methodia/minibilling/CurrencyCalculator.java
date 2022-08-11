package com.github.methodia.minibilling;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyCalculator {
//    public double calculateTo(String currency,double amount){
//        String  urlLink="https://api.apilayer.com/currency_data/convert?to="+currency+"&from=EUR&amount="+amount +"\"";
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(urlLink)).header("apiKey", "u4hHXxTZJcijFYOGD3M2DPintFpugci8")
//                .build();
//        try {
//            client.send(request, HttpResponse.BodyHandlers.ofString());
//            HttpResponse<String> response =
//                    client.send(request, HttpResponse.BodyHandlers.ofString());
//            String responceBody=response.body();
//            JSONObject obj=new JSONObject(responceBody);
//            double result = obj.getDouble("result");
//            return result;
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
