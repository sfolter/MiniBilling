package com.github.methodia.minibilling;

import java.io.IOException;
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
        String INPUT_PATH = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        String OUTPUT_PATH = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\output\\";
        String CURRENCY = "EUR";
        String KEY = "3b14c37cbcca1d0ff2fca003";

        CsvUserReader userReader = new CsvUserReader(INPUT_PATH);
        Map<String, User> userMap = userReader.read();
        CsvReadingReader readingReader = new CsvReadingReader(INPUT_PATH, userMap);
        Collection<Reading> readingCollection = readingReader.read();
        for (Map.Entry<String, User> userFromMap : userMap.entrySet()) {
            User user = userFromMap.getValue();
            List<Price> price = user.getPrice();
            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readingCollection);
            Collection<Measurement> measurements = measurementGenerator.generate();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurements, price, reportDate, CURRENCY, KEY);
            Invoice invoice = invoiceGenerator.generate();
            FolderGenerator folderGenerator = new FolderGenerator(user, OUTPUT_PATH);
            String folderPath = folderGenerator.folderGenerate();
            JsonGenerator jsonGenerator = new JsonGenerator(invoice, folderPath, CURRENCY);
            jsonGenerator.generateJSON();
        }
    }
}

