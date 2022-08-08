package com.github.methodia.minibilling;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException, IOException, IllegalAccessException, NoSuchFieldException {
//        String yearMonthStr = "21-03";
        String yearMonthStr=args[0];
        String resourceDir=args[1];
        String outputDir=args[2];

        //prices-1.csv
//        String pricesPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        CsvFilePriceReader price = new CsvFilePriceReader();
        Map<String, List<Price>> priceL = price.read(resourceDir);


//        String userPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        CsvFileUserReader userFileRead = new CsvFileUserReader();
        List<User> users = userFileRead.read(resourceDir);
        //FolderCreator
        FolderCreatorTodor foldersCreation = new FolderCreatorTodor();
        foldersCreation.createFolders();
        ArrayList<String> folderPath = foldersCreation.getFolderPath();
        //readings.csv
//        String readingsPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        CsvFileReadingReader reading = new CsvFileReadingReader();
        Collection<Reading> readings = reading.read(resourceDir);
//        ArrayList<Float> quantity = reading.getQuantity();
        for (int i = 0; i < users.size(); i++) {


            MeasurementGenerator measurementGenerator = new MeasurementGenerator(users.get(i), readings);
            Collection<Measurement> measermantGenerated = measurementGenerator.generate();
            // Current date and time
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Calendar cal = Calendar.getInstance();

            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(users.get(i), measermantGenerated, users.get(i).getPrice(), yearMonthStr);
            Invoice invoice = invoiceGenerator.generate();
            FolderGenerator folderGenerator = new FolderGenerator(users.get(i),outputDir);
            String folder = folderGenerator.generate();
            JsonGenerator jsonGenerator = new JsonGenerator(invoice, folder);
            jsonGenerator.generate();

        }
    }
}
