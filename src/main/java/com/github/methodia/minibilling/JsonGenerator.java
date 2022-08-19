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

    public JSONObject generate(final Invoice invoice, final String toCurrency) {

        final User user = invoice.getConsumer();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXX");
        final List<InvoiceLine> invoiceLines = invoice.getLines();
        final List<VatLine> vatLines = invoice.getVatLines();
        final List<TaxesLine> taxesLines = invoice.getTaxesLines();
        final JSONObject json = new JSONObject();
        final JSONArray lines = new JSONArray();
        final JSONArray vatArrayJson = new JSONArray();
        final JSONArray taxesArray = new JSONArray();
        orderedJsonObj(json);
        final String documentNumber = Invoice.getDocumentNumber();
        json.put("documentDate", invoice.getDocumentDate());
        json.put("documentNumber", documentNumber);
        json.put("consumer", user.name());
        json.put("reference", user.ref());
        final String totalAmount = invoice.getTotalAmount().toString() + toCurrency;
        json.put("totalAmount", totalAmount);
        final String totalAmountWithVat = invoice.getTotalAmountWithVat().toString() + toCurrency;
        json.put("totalAmountWithVat", totalAmountWithVat);


        final List<Price> prices = user.price();
        for (int i = 0; i < prices.size(); i++) {
            final JSONObject invoiceLine = new JSONObject();
            final JSONObject taxesJsonObj = new JSONObject();

            orderedJsonObj(invoiceLine);
            orderedJsonObj(taxesJsonObj);

            final int index = invoiceLines.get(i).index();
            invoiceLine.put("index", index);
            final BigDecimal quantity = invoiceLines.get(i).quantity();
            invoiceLine.put("quantity", quantity);
            final String lineStart = invoiceLines.get(i).start().atZone(ZoneId.of("GMT")).format(dateTimeFormatter);
            invoiceLine.put("lineStart", lineStart);
            final String lineEnd = invoiceLines.get(i).end().atZone(ZoneId.of("GMT")).format(dateTimeFormatter);
            invoiceLine.put("lineEnd", lineEnd);
            final String product = invoiceLines.get(i).product();
            invoiceLine.put("product", product);
            final BigDecimal price = invoiceLines.get(i).price();
            invoiceLine.put("price", price);
            final int priceList = invoiceLines.get(i).priceList();
            invoiceLine.put("priceList", priceList);
            final String amount = invoiceLines.get(i).amount().toString() + toCurrency;
            invoiceLine.put("amount", amount);
            lines.put(invoiceLine);

            taxesJsonObj.put("index", index);
            taxesJsonObj.put("linesIndex", index);
            final String nameForTaxes = taxesLines.get(i).name();
            taxesJsonObj.put("name", nameForTaxes);
            final long daysQuantity = taxesLines.get(i).daysQuantity();
            taxesJsonObj.put("quantity", daysQuantity);
            final String unit = taxesLines.get(i).unit();
            taxesJsonObj.put("unit", unit);
            final String priceForTaxes = taxesLines.get(i).price().toString() + toCurrency;
            taxesJsonObj.put("price", priceForTaxes);
            final String amountForTaxes = taxesLines.get(i).amount().toString() + toCurrency;
            taxesJsonObj.put("amount", amountForTaxes);
            taxesArray.put(taxesJsonObj);

        }
        for (int j = 0; j <invoice.getVatLines().size() ; j++) {
            final JSONObject vatJsonObj = new JSONObject();
            orderedJsonObj(vatJsonObj);
            vatJsonObj.put("index", vatLines.get(j).index());
            vatJsonObj.put("lineIndex", vatLines.get(j).lineIndex());
            vatJsonObj.put("taxedAmountPercentage",vatLines.get(j).taxedAmountPercentage());
            vatJsonObj.put("percentage", vatLines.get(j).vatPercentage());
            final String vatAmount = vatLines.get(j).amount().toString() + toCurrency;
            vatJsonObj.put("amount", vatAmount);
            vatArrayJson.put(vatJsonObj);
        }
        json.put("lines", lines);
        json.put("taxes", taxesArray);
        json.put("vat", vatArrayJson);
        return json;
    }

    private void orderedJsonObj(final JSONObject json) {
        try {
            final Field changeMap = json.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set(json, new LinkedHashMap<>());
            changeMap.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            System.out.println(e.getMessage());
        }
    }
}