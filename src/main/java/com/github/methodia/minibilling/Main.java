package com.github.methodia.minibilling;

import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ParseException, IOException, org.json.simple.parser.ParseException {
//
//        String yearMonthStr = args[0];
//        String resourceDir = args[1];
//        String outputDir = args[2];
        final String resourceDir = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample2\\input\\";
        final String outputDir = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample2\\test\\";
        final String yearMonthStr = "21-03";
        final String currency = "EUR";


        CsvFileUserReader userFileRead = new CsvFileUserReader();
        List<User> users = userFileRead.read(resourceDir);
        Map<String, User> userMap = CsvFileUserReader.getUserMap();
        //readings.csv
        CsvFileReadingReader reading = new CsvFileReadingReader();
        Collection<Reading> readings = reading.read(resourceDir);


        for (int i = 1; i <= users.size(); i++) {
            User user = userMap.get(String.valueOf(i));
            List<Price> priceList = user.getPrice();
            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readings);
            Collection<Measurement> measurmentGenerated = measurementGenerator.generate();

            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(new CurrencyConvertor());
            Invoice invoice = invoiceGenerator.generate(user, measurmentGenerated, priceList, yearMonthStr, currency);
            FolderGenerator folderGenerator = new FolderGenerator();
            String folder = folderGenerator.generate(user, outputDir);
            JsonGenerator jsonGenerator = new JsonGenerator();
            JSONObject jsonInvoiceObject = jsonGenerator.generate(invoice,currency);
            JsonFileGenerator jsonFileGenerator = new JsonFileGenerator();
            jsonFileGenerator.generateJsonFile(jsonInvoiceObject, folder);


        }
    }
}
