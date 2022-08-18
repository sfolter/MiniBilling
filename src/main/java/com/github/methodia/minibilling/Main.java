package com.github.methodia.minibilling;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Main {
    public static final String API_KEY = "1f16554ded67538b17b5bc97";
    public static void main(String[] args) throws ParseException, IOException, NoSuchFieldException, IllegalAccessException, org.json.simple.parser.ParseException {

        //String yearMonthStr = args[0];
      //  String resourceDir = args[1];
        //String outputDir = args[2];
        final String currency = "BGN";
        String resourceDir = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample2\\input\\";
        String outputDir = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample2\\test\\";
        String yearMonthStr = "21-03";


        CSVUserReader userReader = new CSVUserReader(resourceDir);
        Map<String, User> userMap = userReader.read();
        CSVReadingsReader readingReader = new CSVReadingsReader(resourceDir, userMap);
        Collection<Reading> readingCollection = readingReader.read();
        for (Map.Entry<String, User> userFromMap : userMap.entrySet()) {
            User user = userFromMap.getValue();
            List<Price> price = user.getPrice();
            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readingCollection);
            Collection<Measurement> measurements = measurementGenerator.generate();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurements, price, yearMonthStr, currency);
            Invoice invoice = invoiceGenerator.generate();
            FolderGenerator folderGenerator = new FolderGenerator(user, outputDir);
            String folderPath = folderGenerator.folderGenerate();
            JsonFileGenerator jsonGenerator = new JsonFileGenerator(invoice, folderPath, currency);
            jsonGenerator.generateJSON();
        }
    }
}

