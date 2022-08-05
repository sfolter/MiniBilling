package com.github.methodia.minibilling;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONGenerator {
    Invoice invoice;
    String folder;

    public JSONGenerator(Invoice invoice, String folder) {
        this.invoice = invoice;
        this.folder = folder;
    }

    JSONObject json = new JSONObject();
    JSONObject newLine = new JSONObject();
    JSONArray lines = new JSONArray();
    User user;

    public void generateJSON(){
        Invoice invoice1 = invoice;
        String folderPath = folder;
        json.put("documentDate", invoice1.getDocumentDate());
//        json.put("documentNumber", invoice1);
        json.put("reference", user.getRef());
        json.put("totalAmount", invoice1.getTotalAmount());


    }

}
