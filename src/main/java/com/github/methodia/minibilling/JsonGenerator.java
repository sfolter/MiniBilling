package com.github.methodia.minibilling;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonGenerator {
    Invoice invoice;
    String folder;

    public JsonGenerator(Invoice invoice, String folder) {
        this.invoice = invoice;
        this.folder = folder;
    }

    public JSONObject generate() throws ParseException, IOException {

        User user = invoice.getConsumer();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXX");
        List<InvoiceLine> invoiceLines = invoice.getLines();
        List<VatLine> vatLines = invoice.getVatLines();
        LocalDateTime end = invoiceLines.get(invoiceLines.size() - 1).getEnd();
        JSONObject json = new JSONObject();
        JSONArray lines = new JSONArray();
        JSONArray vatArrayJson = new JSONArray();
        orderedJsonObj(json);
        String documentNumber = Invoice.getDocumentNumber();
        json.put("documentDate", invoice.getDocumentDate());
        json.put("documentNumber", documentNumber);
        json.put("consumer", user.getName());
        json.put("reference", user.getRef());
        json.put("totalAmount", invoice.getTotalAmount());
        json.put("totalAmountWithVat", invoice.getTotalAmountWithVat());


        List<Price> prices = user.getPrice();
        for (int i = 0; i < prices.size(); i++) {
            JSONObject invoiceLine = new JSONObject();
            JSONObject vatJsonObj = new JSONObject();

            orderedJsonObj(invoiceLine);
            orderedJsonObj(vatJsonObj);


            int index = invoiceLines.get(i).getIndex();
            invoiceLine.put("index", index);
            BigDecimal quantity = invoiceLines.get(i).getQuantity();
            invoiceLine.put("quantity", quantity);
            String lineStart = invoiceLines.get(i).getStart().atZone(ZoneId.of("GMT")).format(dateTimeFormatter);
            invoiceLine.put("lineStart", lineStart);
            String lineEnd = invoiceLines.get(i).getEnd().atZone(ZoneId.of("GMT")).format(dateTimeFormatter);
            invoiceLine.put("lineEnd", lineEnd);
            String product = invoiceLines.get(i).getProduct();
            invoiceLine.put("product", product);
            BigDecimal price = invoiceLines.get(i).getPrice();
            invoiceLine.put("price", price);
            int priceList = invoiceLines.get(i).getPriceList();
            invoiceLine.put("priceList", priceList);
            BigDecimal amount = invoiceLines.get(i).getAmount();
            invoiceLine.put("amount", amount);
            lines.put(invoiceLine);

            vatJsonObj.put("index", vatLines.get(i).getIndex());
            vatJsonObj.put("lineIndex", index);
            vatJsonObj.put("percentage", vatLines.get(i).getVatPercentage());
            vatJsonObj.put("amount", vatLines.get(i).getAmount());
            vatArrayJson.put(vatJsonObj);

        }
        json.put("lines", lines);
        json.put("vat", vatArrayJson);
        return json;
    }

    private void orderedJsonObj(JSONObject json) {
        try {
            Field changeMap = json.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set(json, new LinkedHashMap<>());
            changeMap.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            System.out.println((e.getMessage()));
        }
    }
}