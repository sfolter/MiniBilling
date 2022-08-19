package com.github.methodia.minibilling;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonGenerator {

    public JSONObject generate(Invoice invoice, String currency) {

        User user = invoice.getConsumer();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXX");
        List<InvoiceLine> invoiceLines = invoice.getLines();
        List<VatLine> vatLines = invoice.getVatLines();
        List<TaxesLine> taxesLines = invoice.getTaxesLines();
        JSONObject json = new JSONObject();
        JSONArray lines = new JSONArray();
        JSONArray vatArrayJson = new JSONArray();
        JSONArray taxesArray = new JSONArray();
        orderedJsonObj(json);
        String documentNumber = Invoice.getDocumentNumber();
        json.put("documentDate", invoice.getDocumentDate());
        json.put("documentNumber", documentNumber);
        json.put("consumer", user.getName());
        json.put("reference", user.getRef());
        String totalAmount = invoice.getTotalAmount().toString() + currency;
        json.put("totalAmount", totalAmount);
        String totalAmountWithVat = invoice.getTotalAmountWithVat().toString() + currency;
        json.put("totalAmountWithVat", totalAmountWithVat);


        List<Price> prices = user.getPrice();
        for (int i = 0; i < prices.size(); i++) {
            JSONObject invoiceLine = new JSONObject();

            JSONObject taxesJsonObj = new JSONObject();

            orderedJsonObj(invoiceLine);

            orderedJsonObj(taxesJsonObj);

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
            String amount = invoiceLines.get(i).getAmount().toString() + currency;
            invoiceLine.put("amount", amount);
            lines.put(invoiceLine);



            taxesJsonObj.put("index", index);
            taxesJsonObj.put("linesIndex", index);
            String nameForTaxes = taxesLines.get(i).getName();
            taxesJsonObj.put("name", nameForTaxes);
            long daysQuantity = taxesLines.get(i).getDaysQuantity();
            taxesJsonObj.put("quantity", daysQuantity);
            String unit = taxesLines.get(i).getUnit();
            taxesJsonObj.put("unit", unit);
            String priceForTaxes = taxesLines.get(i).getPrice().toString() + currency;
            taxesJsonObj.put("price", priceForTaxes);
            String amountForTaxes = taxesLines.get(i).getAmount().toString() + currency;
            taxesJsonObj.put("amount", amountForTaxes);
            taxesArray.put(taxesJsonObj);

        }
        for (int j = 0; j <invoice.getVatLines().size() ; j++) {
            JSONObject vatJsonObj = new JSONObject();
            orderedJsonObj(vatJsonObj);
            vatJsonObj.put("index", vatLines.get(j).getIndex());
            vatJsonObj.put("lineIndex", vatLines.get(j).getLineIndex());
            vatJsonObj.put("taxedAmountPercentage",vatLines.get(j).getTaxedAmountPercentage());
            vatJsonObj.put("percentage", vatLines.get(j).getVatPercentage());
            String vatAmount = vatLines.get(j).getAmount().toString() + currency;
            vatJsonObj.put("amount", vatAmount);
            vatArrayJson.put(vatJsonObj);
        }
        json.put("lines", lines);
        json.put("taxes", taxesArray);
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