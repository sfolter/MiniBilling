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

    public static void main(String[] args) throws ParseException, IOException {

        String inPath = args[1];
        //String inPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample2\\input\\";
        String outputDirectory = args[2];
        String yearMonth = args[0];

        CSVUserReader user = new CSVUserReader();
        List<User> users = user.read(inPath);

        Map<String, User> userMap = CSVUserReader.getUserMap();
        CSVReadingsReader readings = new CSVReadingsReader(inPath);
        Collection<Reading> readingCollection = readings.read();

        //Generating JSON
        for (int i = 1; i <= users.size(); i++) {

            User user1 = userMap.get(String.valueOf(i));
            List<Price> price1 = user1.getPrice();
            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user1, readingCollection);
            Collection<Measurement> measurements = measurementGenerator.generate();
            //List<Price> prices = CSVPricesReader.getPriceList();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user1, measurements, price1, yearMonth);
            Invoice invoice = invoiceGenerator.generate();
            FolderGenerator folderGenerator = new FolderGenerator(user1, outputDirectory);
            String folderPath = folderGenerator.folderGenerate();
            JSONGenerator jsonGenerator = new JSONGenerator(invoice, folderPath);
            jsonGenerator.generateJSON();

        }
    }
}




