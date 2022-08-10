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
    String folder;

    public JSONGenerator(Invoice invoice, String folder) {
        this.invoice = invoice;
        this.folder = folder;
    }

    JSONObject json = new JSONObject();

    JSONArray lines = new JSONArray();

    JSONArray vatLines = new JSONArray();

    String documentNumber = Invoice.getDocumentNumber();

    public void generateJSON() throws ParseException, IOException {
        Invoice invoice1 = invoice;
        User user = invoice1.getConsumer();
        String folderPath = folder;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXX");

        LocalDateTime end = invoice1.getLines().get(invoice1.getLines().size() - 1).getEnd();
        List<Price> prices = CSVPricesReader.getPriceCollection().get(String.valueOf(user.getPriceListNumber()));
        for (int i = 0; i < prices.size(); i++) {
            JSONObject newLine = new JSONObject();
            JSONObject newVatLine = new JSONObject();
            try {
                Field changeMap = json.getClass().getDeclaredField("map");
                changeMap.setAccessible(true);
                changeMap.set(json, new LinkedHashMap<>());
                changeMap.setAccessible(false);
                Field changeMapForArray = newLine.getClass().getDeclaredField("map");
                changeMapForArray.setAccessible(true);
                changeMapForArray.set(newLine, new LinkedHashMap<>());
                changeMapForArray.setAccessible(false);
                Field changeMapForVat = newVatLine.getClass().getDeclaredField("map");
                changeMapForVat.setAccessible(true);
                changeMapForVat.set(newVatLine, new LinkedHashMap<>());
                changeMapForVat.setAccessible(false);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                System.out.println((e.getMessage()));
            }
            json.put("documentDate", invoice1.getDocumentDate());
            json.put("documentNumber", documentNumber);
            json.put("consumer", user.getName());
            json.put("reference", user.getRef());
            json.put("totalAmount", invoice1.getTotalAmount());


            int index = invoice1.getLines().get(i).getIndex();
            newLine.put("index", index);
            BigDecimal quantity = invoice1.getLines().get(i).getQuantity();
            newLine.put("quantity", quantity);
            String lineStart = invoice1.getLines().get(i).getStart().atZone(ZoneId.of("GMT")).format(dateTimeFormatter);
            newLine.put("lineStart", lineStart);
            String lineEnd = invoice1.getLines().get(i).getEnd().atZone(ZoneId.of("GMT")).format(dateTimeFormatter);
            newLine.put("lineEnd", lineEnd);
            String product = invoice.getLines().get(i).getProduct();
            newLine.put("product", product);
            BigDecimal price = invoice1.getLines().get(i).getPrice();
            newLine.put("price", price);
            int priceList = invoice1.getLines().get(i).getPriceList();
            newLine.put("priceList", priceList);
            BigDecimal amount = invoice1.getLines().get(i).getAmount();
            newLine.put("amount", amount);
            lines.put(newLine);
            int indexInVat = invoice1.getVatsLines().get(i).getIndex();
            newVatLine.put("index", indexInVat);
            List<Integer> linesInVat = invoice1.getVatsLines().get(i).getLines();
            newVatLine.put("lines", linesInVat);
            int percentage = invoice1.getVatsLines().get(i).getPercentage();
            newVatLine.put("percentage", percentage);
            BigDecimal amountInVat = invoice1.getVatsLines().get(i).getAmount();
            newVatLine.put("amount", amountInVat);
            vatLines.put(newVatLine);

        }
        json.put("lines", lines);
        json.put("vat", vatLines);

        Date jud = new SimpleDateFormat("yy-MM").parse(String.valueOf(end));
        String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
        String[] splitDate = month.split("\\s+");
        String monthInCyrilic = splitDate[1];
        int year = Integer.parseInt(splitDate[2]) % 100;
        String monthInUpperCase = monthInCyrilic.substring(0, 1).toUpperCase() + monthInCyrilic.substring(1);
        String fileWriter = documentNumber + "-" + monthInUpperCase + "-" + year;
        FileWriter file = new FileWriter(folderPath + "//" + fileWriter + ".json");
        file.write(json.toString(4));
        file.flush();
        file.close();

    }
}