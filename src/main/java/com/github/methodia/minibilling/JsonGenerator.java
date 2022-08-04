package com.github.methodia.minibilling;

import com.google.gson.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class JsonGenerator {
    private Invoice invoice;
    private String folder;


    public JsonGenerator(Invoice invoice, String folder) {
        this.folder = folder;
        this.invoice = invoice;
    }

    JsonObject json = new JsonObject();
    JsonArray lines = new JsonArray();
    JsonObject newLine=new JsonObject();

    public void generate() throws IOException {
        Invoice invoiceObj=invoice;
        String folderPath=folder;
        User consumer = invoiceObj.getConsumer();
        LocalDateTime documentDate = invoiceObj.getDocumentDate();
        BigDecimal totalAmount = invoiceObj.getTotalAmount();


        List<InvoiceLine> lines1 = invoiceObj.getLines();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(invoiceObj);
        String jsonFilePath = folderPath + "\\" + invoiceObj.getDocumentNumber() + "-" +".json";
        File creatingFiles = new File(jsonFilePath);
        creatingFiles.createNewFile();
    }
}
