package com.github.methodia.minibilling;

import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws ParseException, IOException, org.json.simple.parser.ParseException {
//        String yearMonthStr = args[0];
//        String resourceDir = args[1];
//        String outputDir = args[2];


        final String resourceDir = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        final String outputDir = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\test\\";
        final String yearMonthStr = "21-03";
        final String currency = "BGN";

        //user.csv
        CsvFileUserReader userFileRead = new CsvFileUserReader();
        TreeMap<String, User> userMap = userFileRead.read(resourceDir);

        //readings.csv
        CsvFileReadingReader reading = new CsvFileReadingReader();
        Collection<Reading> readings = reading.read(userMap, resourceDir);

        //Creating objects
        MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator(new CurrencyConvertor());
        FolderGenerator folderGenerator = new FolderGenerator();
        JsonGenerator jsonGenerator = new JsonGenerator();
        JsonFileGenerator jsonFileGenerator = new JsonFileGenerator();

        for (String refNumb : userMap.keySet()) {
            User user = userMap.get(refNumb);
            List<Price> priceList = user.getPrice();
            Collection<Measurement> measurmentGenerated = measurementGenerator.generate(user, readings);
            Invoice invoice = invoiceGenerator.generate(user, measurmentGenerated, priceList, yearMonthStr, currency);
            String folder = folderGenerator.generate(user, outputDir);
            JSONObject jsonInvoiceObject = jsonGenerator.generate(invoice, currency);
            jsonFileGenerator.generateJsonFile(jsonInvoiceObject, folder);


        }
    }
}
