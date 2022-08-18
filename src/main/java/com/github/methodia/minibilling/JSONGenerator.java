package com.github.methodia.minibilling;

import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JsonGenerator {
    Invoice invoice;
    String folderPath;

    public JsonGenerator(Invoice invoice, String folderPath) {
        this.invoice = invoice;
        this.folderPath = folderPath;
    }

    public JSONObject generateJSON(Invoice invoice, String currency)  {

            User user = invoice.getConsumer();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXX");
            List<InvoiceLine> invoiceLines = invoice.getLines();
            List<VatLine> vatLines = invoice.getVatsLines();
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
            String totalAmount = invoice.getTotalAmount().toString()+currency;
            json.put("totalAmount", totalAmount);
            String totalAmountWithVat = invoice.getTotalAmountWithVat().toString()+currency;
            json.put("totalAmountWithVat", totalAmountWithVat);


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
                String amount = invoiceLines.get(i).getAmount().toString()+currency;
                invoiceLine.put("amount", amount);
                lines.put(invoiceLine);

                vatJsonObj.put("index", vatLines.get(i).getIndex());
                vatJsonObj.put("lines", index);
                vatJsonObj.put("percentage", vatLines.get(i).getPercentage());
                String vatAmount = vatLines.get(i).getAmount().toString()+currency;
                vatJsonObj.put("amount", vatAmount);
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



