package com.github.methodia.minibilling;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;


public class CurrencyConverter {

    private final String key;

    public CurrencyConverter(String key) {
        this.key = key;
    }

    public BigDecimal getCurrencyValue(String currency) {
        String url_str = "https://api.exchangerate.host/convert?from=" + currency + "&to=BGN";
        try {
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();
            return new BigDecimal(jsonobj.get("result").getAsString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //    private String httpGetRequest(String currency) {
    //        try {
    //            String urlLink = "https://api.apilayer.com/fixer/convert?to=" + currency + "&from=BGN&amount=1";
    //            HttpClient client = HttpClient.newHttpClient ();
    //            HttpRequest request = HttpRequest.newBuilder ()
    //                    .uri (URI.create (urlLink)).header ("apiKey", key)
    //                    .build ();
    //            client.send (request, HttpResponse.BodyHandlers.ofString ());
    //            HttpResponse<String> response =
    //                    client.send (request, HttpResponse.BodyHandlers.ofString ());
    //            return response.body ();
    //        } catch (IOException | InterruptedException e) {
    //            throw new RuntimeException (e);
    //        }
    //    }
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
