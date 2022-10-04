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
import java.util.Locale;
import java.util.Set;

public class JsonFileGenerator {

    private final Invoice invoice;
    private final String folderPath;
    private final String currency;
    private final User user;

    public JsonFileGenerator(final Invoice invoice, final String folderPath, final String currency, final User user) {
        this.invoice = invoice;
        this.folderPath = folderPath;
        this.currency = currency;
        this.user = user;
    }

    JSONObject json = new JSONObject();

    JSONArray lines = new JSONArray();

    JSONArray vatLines = new JSONArray();

    JSONArray taxesLines = new JSONArray();

    String documentNumber = Invoice.getDocumentNumber();


    public JSONObject generateJSON() throws ParseException, IOException, NoSuchFieldException, IllegalAccessException {
        final String consumer = invoice.getConsumer();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXX");
        final Set<Price> prices = user.getPriceList().getPrices();
        //        final List<Price> prices = user.getPrice();
        for (int i = 0; i < prices.size(); i++) {
            final JSONObject newLine = new JSONObject();
            final JSONObject newTaxesLine = new JSONObject();
            orderJson(json);
            orderJson(newLine);
            orderJson(newTaxesLine);

            json.put("documentDate", invoice.getDocumentDate());
            json.put("documentNumber", documentNumber);
            json.put("consumer", user.getName());
            json.put("reference", user.getRef());
            json.put("totalAmount", invoice.getTotalAmount().toString() + " " + currency);
            json.put("totalAmountWithVat", invoice.getTotalAmountWithVat().toString() + " " + currency);
            //          Invoice lines
            final int index = invoice.getLines().get(i).getIndex();
            newLine.put("index", index);
            final BigDecimal quantity = invoice.getLines().get(i).getQuantity();
            newLine.put("quantity", quantity);
            final String lineStart = invoice.getLines().get(i).getStart().atZone(ZoneId.of("GMT"))
                    .format(dateTimeFormatter);
            newLine.put("lineStart", lineStart);
            final String lineEnd = invoice.getLines().get(i).getEnd().atZone(ZoneId.of("GMT"))
                    .format(dateTimeFormatter);
            newLine.put("lineEnd", lineEnd);
            final String product = invoice.getLines().get(i).getProduct();
            newLine.put("product", product);
            final BigDecimal price = invoice.getLines().get(i).getPrice();
            newLine.put("price", price);
            final int priceList = invoice.getLines().get(i).getPriceList();
            newLine.put("priceList", priceList);
            final BigDecimal amount = invoice.getLines().get(i).getAmount();
            newLine.put("amount", amount.toString() + " " + currency);
            lines.put(newLine);
            //          Tax lines
            final int indexInTaxes = invoice.getTaxesLines().get(i).getIndex();
            newTaxesLine.put("index", indexInTaxes);
            final int linesInTaxes = invoice.getTaxesLines().get(i).getLines();
            newTaxesLine.put("lines", linesInTaxes);
            final String name = invoice.getTaxesLines().get(i).getName();
            newTaxesLine.put("name", name);
            final int quantityInTaxes = invoice.getTaxesLines().get(i).getQuantity();
            newTaxesLine.put("quantity", quantityInTaxes);
            final String unit = invoice.getTaxesLines().get(i).getUnit();
            newTaxesLine.put("unit", unit);
            final BigDecimal priceInTaxes = invoice.getTaxesLines().get(i).getPrice();
            newTaxesLine.put("price", priceInTaxes);
            final BigDecimal amountInTaxes = invoice.getTaxesLines().get(i).getAmount();
            newTaxesLine.put("amount", amountInTaxes.toString() + " " + currency);
            taxesLines.put(newTaxesLine);
        }
        //          VAT lines
        for (int j = 0; j < invoice.getVatsLines().size(); j++) {
            final JSONObject newVatLine = new JSONObject();
            orderJson(newVatLine);
            final int indexInVat = invoice.getVatsLines().get(j).getIndex();
            newVatLine.put("index", indexInVat);
            final int linesInVat = invoice.getVatsLines().get(j).getLines();
            newVatLine.put("lines", linesInVat);
            final int taxesInVat = invoice.getVatsLines().get(j).getTaxes();
            newVatLine.put("taxes", taxesInVat);
            final int taxedAmountPercentage = invoice.getVatsLines().get(j).getTaxedAmountPercentage();
            newVatLine.put("taxedAmountPercentage", taxedAmountPercentage);
            final int percentage = invoice.getVatsLines().get(j).getPercentage();
            newVatLine.put("percentage", percentage);
            final BigDecimal amountInVat = invoice.getVatsLines().get(j).getAmount();
            newVatLine.put("amount", amountInVat.toString() + " " + currency);
            vatLines.put(newVatLine);
        }
        json.put("lines", lines);
        json.put("taxes", taxesLines);
        json.put("vat", vatLines);
        fileGenerator();
        return json;
    }

    public void orderJson(final JSONObject json) throws NoSuchFieldException, IllegalAccessException {
        final Field changeMap = json.getClass().getDeclaredField("map");
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