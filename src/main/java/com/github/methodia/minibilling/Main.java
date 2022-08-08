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

        Scanner scanner = new Scanner(System.in);
        String inPath = scanner.nextLine();
        String yearMonth = "21-03";
        //String resourceDirectory = args[1];
        //String outputDirectory = args[2];
        //String yearMonth = args[0];



        CSVPricesReader price = new CSVPricesReader(inPath);
        price.read();
        CSVUserReader user = new CSVUserReader(inPath);
        List<User> users = user.read();
        CSVReadingsReader readings = new CSVReadingsReader(inPath);
        Collection<Reading> readingCollection = readings.read();
        for (int i = 0; i < users.size(); i++) {
            MeasurementGenerator measurementGenerator = new MeasurementGenerator(users.get(i), readingCollection);
            Collection<Measurement> measurements = measurementGenerator.generate();
            List<Price> prices = CSVPricesReader.getPrices();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(users.get(i), measurements, prices, yearMonth);
            Invoice invoice = invoiceGenerator.generate();
            FolderGenerator folderGenerator = new FolderGenerator(users.get(i));
            String folderPath = folderGenerator.folderGenerate();
            JSONGenerator jsonGenerator = new JSONGenerator(invoice, folderPath);
            jsonGenerator.generateJSON();

        }
    }
}

