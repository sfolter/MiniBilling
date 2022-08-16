package com.github.methodia.minibilling;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws ParseException, IOException, NoSuchFieldException, IllegalAccessException, org.json.simple.parser.ParseException {
//        String reportDate = args[0];
//        String inPath = args[1];
//        String outPath = args[2];
        String reportDate = "21-03";
//        test1
//        String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\input\\";
//        String outPath = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\expected\\";
//        main sample1
//        String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
//        String outPath="C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\output\\";
//        main sample2
        String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample2\\input\\";
        String outPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample2\\output\\";
        String currency = "EUR";
        String key = "3b14c37cbcca1d0ff2fca003";

        CSVUserReader userReader = new CSVUserReader(inPath);
        Map<String, User> userMap = userReader.read();
        CSVReadingReader readingReader = new CSVReadingReader(inPath, userMap);
        Collection<Reading> readingCollection = readingReader.read();
        for (Map.Entry<String, User> userFromMap : userMap.entrySet()) {
            User user = userFromMap.getValue();
            List<Price> price = user.getPrice();
            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readingCollection);
            Collection<Measurement> measurements = measurementGenerator.generate();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurements, price, reportDate, currency, key);
            Invoice invoice = invoiceGenerator.generate();
            FolderGenerator folderGenerator = new FolderGenerator(user, outPath);
            String folderPath = folderGenerator.folderGenerate();
            JSONGenerator jsonGenerator = new JSONGenerator(invoice, folderPath, currency);
            jsonGenerator.generateJSON();
        }
    }
}

