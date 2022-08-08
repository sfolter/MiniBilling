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
        String yearMonthStr = args[0];
        String resourceDir = args[1];
        String outputDir = args[2];
//        String resourceDir= "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample2\\input\\";
//        String outputDir="C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample2\\test\\";


        String userPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        CsvFileUserReader userFileRead = new CsvFileUserReader();
        List<User> users = userFileRead.read(resourceDir);
        Map<String, User> userMap = CsvFileUserReader.getUserMap();
        //FolderCreator
        FolderCreatorTodor foldersCreation = new FolderCreatorTodor();
        foldersCreation.createFolders();
        ArrayList<String> folderPath = foldersCreation.getFolderPath();
        //readings.csv
        String readingsPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        CsvFileReadingReader reading = new CsvFileReadingReader();
        Collection<Reading> readings = reading.read(resourceDir);
//prices-1.csv
        String pricesPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        CsvFilePriceReader price = new CsvFilePriceReader();



        for (int i = 1; i <= users.size(); i++) {
            User user = userMap.get(String.valueOf(i));
            Map<String, List<Price>> priceL = price.read(user,resourceDir);
            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readings);
            Collection<Measurement> measermantGenerated = measurementGenerator.generate();

            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measermantGenerated, user.getPrice(), yearMonthStr);
            Invoice invoice = invoiceGenerator.generate();
            FolderGenerator folderGenerator = new FolderGenerator(user, outputDir);
            String folder = folderGenerator.generate();
            JsonGenerator jsonGenerator = new JsonGenerator(invoice, folder);
            jsonGenerator.generate();

        }
    }
}
