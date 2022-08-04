package com.github.methodia.minibilling;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException, IOException, IllegalAccessException, NoSuchFieldException {
        //prices-1.csv
        String pricesPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv";
        CsvFilePriceReader price = new CsvFilePriceReader();
        Map<String, List<Price>> priceL = price.read(pricesPath);


        String userPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\users.csv";
        CsvFileUserReader userFileRead = new CsvFileUserReader();
        List<User> users = userFileRead.read(userPath);
        //FolderCreator
        FolderCreatorTodor foldersCreation = new FolderCreatorTodor();
        foldersCreation.createFolders();
        ArrayList<String> folderPath = foldersCreation.getFolderPath();
        //readings.csv
        String readingsPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\readings.csv";
        CsvFileReadingReader reading = new CsvFileReadingReader();
        Collection<Reading> readings = reading.read(readingsPath);
//        ArrayList<Float> quantity = reading.getQuantity();
        MeasurementGenerator measurementGenerator = new MeasurementGenerator(users.get(1), readings);
        Collection<Measurement> measermantGenerated = measurementGenerator.generate();
        // Current date and time
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Calendar cal = Calendar.getInstance();

        InvoiceGenerator invoiceGenerator = new InvoiceGenerator(users.get(1), measermantGenerated, users.get(1).getPrice());
        Invoice invoice = invoiceGenerator.generate();
        FolderGenerator folderGenerator = new FolderGenerator(users.get(3));
        String folder = folderGenerator.generate();
        JsonGenerator jsonGenerator = new JsonGenerator(invoice,folder);
        jsonGenerator.generate();

    }
}
