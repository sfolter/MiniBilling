package com.github.methodia.minibilling;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class JSONGenerator {
    Invoice invoice;
    String folderPath;

    String currency;

    public JSONGenerator(Invoice invoice, String folderPath, String currency) {
        this.invoice = invoice;
        this.folderPath = folderPath;
        this.currency = currency;
    }

    JSONObject json = new JSONObject();

    JSONArray lines = new JSONArray();

    JSONArray vatLines = new JSONArray();

    JSONArray taxesLines = new JSONArray();

    String documentNumber = Invoice.getDocumentNumber();


    public void generateJSON() throws ParseException, IOException, NoSuchFieldException, IllegalAccessException {
        User user = invoice.getConsumer();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXX");
        List<Price> prices = user.getPrice();
        for (int i = 0; i < prices.size(); i++) {
            JSONObject newLine = new JSONObject();
            JSONObject newVatLine = new JSONObject();
            JSONObject newTaxesLine = new JSONObject();
            orderJSON(json);
            orderJSON(newLine);
            orderJSON(newTaxesLine);
            orderJSON(newVatLine);
            json.put("documentDate", invoice.getDocumentDate());
            json.put("documentNumber", documentNumber);
            json.put("consumer", user.getName());
            json.put("reference", user.getRef());
            json.put("totalAmount", invoice.getTotalAmount().toString() + " " + currency);
            json.put("totalAmountWithVat", invoice.getTotalAmountWithVat().toString() + " " + currency);
            int index = invoice.getLines().get(i).getIndex();
            newLine.put("index", index);
            BigDecimal quantity = invoice.getLines().get(i).getQuantity();
            newLine.put("quantity", quantity);
            String lineStart = invoice.getLines().get(i).getStart().atZone(ZoneId.of("GMT")).format(dateTimeFormatter);
            newLine.put("lineStart", lineStart);
            String lineEnd = invoice.getLines().get(i).getEnd().atZone(ZoneId.of("GMT")).format(dateTimeFormatter);
            newLine.put("lineEnd", lineEnd);
            String product = this.invoice.getLines().get(i).getProduct();
            newLine.put("product", product);
            BigDecimal price = invoice.getLines().get(i).getPrice();
            newLine.put("price", price);
            int priceList = invoice.getLines().get(i).getPriceList();
            newLine.put("priceList", priceList);
            BigDecimal amount = invoice.getLines().get(i).getAmount();
            newLine.put("amount", amount.toString() + " " + currency);
            lines.put(newLine);
            int indexInTaxes = invoice.getTaxesLines().get(i).getIndex();
            newTaxesLine.put("index", indexInTaxes);
            int linesInTaxes = invoice.getTaxesLines().get(i).getLines();
            newTaxesLine.put("lines", linesInTaxes);
            String name = invoice.getTaxesLines().get(i).getName();
            newTaxesLine.put("name", name);
            int quantityInTaxes = invoice.getTaxesLines().get(i).getQuantity();
            newTaxesLine.put("quantity", quantityInTaxes);
            String unit = invoice.getTaxesLines().get(i).getUnit();
            newTaxesLine.put("unit", unit);
            BigDecimal priceInTaxes = invoice.getTaxesLines().get(i).getPrice();
            newTaxesLine.put("price", priceInTaxes);
            BigDecimal amountInTaxes = invoice.getTaxesLines().get(i).getAmount();
            newTaxesLine.put("amount", amountInTaxes.toString() + " " + currency);
            taxesLines.put(newTaxesLine);
            int indexInVat = invoice.getVatsLines().get(i).getIndex();
            newVatLine.put("index", indexInVat);
            int linesInVat = invoice.getVatsLines().get(i).getLines();
            newVatLine.put("lines", linesInVat);
            int percentage = invoice.getVatsLines().get(i).getPercentage();
            newVatLine.put("percentage", percentage);
            BigDecimal amountInVat = invoice.getVatsLines().get(i).getAmount();
            newVatLine.put("amount", amountInVat.toString() + " " + currency);
            vatLines.put(newVatLine);
        }
        json.put("lines", lines);
        json.put("taxes", taxesLines);
        json.put("vat", vatLines);
        fileGenerator();
    }

    public void orderJSON(JSONObject json) throws NoSuchFieldException, IllegalAccessException {
        Field changeMap = json.getClass().getDeclaredField("map");
        changeMap.setAccessible(true);
        changeMap.set(json, new LinkedHashMap<>());
        changeMap.setAccessible(false);
    }

    public void fileGenerator() throws IOException, ParseException {
        LocalDateTime end = invoice.getLines().get(invoice.getLines().size() - 1).getEnd();
        Date jud = new SimpleDateFormat("yy-MM").parse(String.valueOf(end));
        String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
        String[] splitDate = month.split("\\s+");
        String monthName = splitDate[1];
        int year = Integer.parseInt(splitDate[2]) % 100;
        String monthInUpperCase = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
        String fileWriter = documentNumber + "-" + monthInUpperCase + "-" + year;
        FileWriter file = new FileWriter(folderPath + "//" + fileWriter + ".json");
        file.write(json.toString(4));
        file.flush();
        file.close();
    }
}
