package com.github.methodia.minibilling;

import org.json.simple.JSONArray;
import org.json.*;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) throws ParseException, IOException, NoSuchFieldException, IllegalAccessException {
//        String reportDate = args[0];
//        String inPath = args[1];
//        String outPath = args[2];
        String reportDate = "21-03";
//        test1
//        String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\input\\";
//        String outPath = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\expected\\";
//        main sample1
        String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        String outPath="C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\output\\";
//        main sample2
//        String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample2\\input\\";
//        String outPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample2\\output\\";

        CSVUserReader user = new CSVUserReader(inPath);
        List<User> users = user.read();
        Map<String, User> userMap = CSVUserReader.getUserMap();
        CSVReadingsReader readings = new CSVReadingsReader(inPath);
        Collection<Reading> readingCollection = readings.read();

        for (int i = 1; i <= users.size(); i++) {
            User user1 = userMap.get(String.valueOf(i));
            List<Price> price = user1.getPrice();
            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user1, readingCollection);
            Collection<Measurement> measurements = measurementGenerator.generate();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user1, measurements, price, reportDate);
            Invoice invoice = invoiceGenerator.generate();
            FolderGenerator folderGenerator = new FolderGenerator(user1, outPath);
            String folderPath = folderGenerator.folderGenerate();
            JSONGenerator jsonGenerator = new JSONGenerator(invoice, folderPath);
            jsonGenerator.generateJSON();
        }
    }
}

