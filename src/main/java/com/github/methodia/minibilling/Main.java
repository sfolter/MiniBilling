package com.github.methodia.minibilling;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class Main {

    public static void main(final String[] args)
            throws ParseException, IOException, NoSuchFieldException, IllegalAccessException,
            org.json.simple.parser.ParseException, SQLException, ClassNotFoundException {
        final String reportDate = args[0];
        final String inPath = args[1];
        final String outPath = args[2];
        //        final String reportDate = "21-03";
        //             //   test1
        //                final String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\input\\";
        //                final String outPath = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\expected\\";
        //        main sample1
        //                String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        //                String outPath="C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\output\\";
        //        main sample2
        //        final String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        //        final String outPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\output\\";
        final String currency = "EUR";
        final String key = "3b14c37cbcca1d0ff2fca003";
        final int taxedAmountPercentageVat1 = 60;

        final CsvUserReader userReader = new CsvUserReader(inPath);
        final Map<String, User> userMap = userReader.read();
        //        reading user from database:
        //        final DataBaseUserReader dataBaseUserReader = new DataBaseUserReader("users");
        //        final Map<String, User> userMap = dataBaseUserReader.read();
        final CsvReadingReader readingReader = new CsvReadingReader(inPath, userMap);
        final Collection<Reading> readingCollection = readingReader.read();
        for (final Map.Entry<String, User> userFromMap : userMap.entrySet()) {
            final User user = userFromMap.getValue();
            final List<Price> price = user.getPrice();
            final MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readingCollection);
            final Collection<Measurement> measurements = measurementGenerator.generate();
            final InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurements, price, reportDate,
                    currency, key);
            final Invoice invoice = invoiceGenerator.generate(taxedAmountPercentageVat1);
            final FolderGenerator folderGenerator = new FolderGenerator(user, outPath);
            final String folderPath = folderGenerator.folderGenerate();
            final JsonGenerator jsonGenerator = new JsonGenerator(invoice, folderPath, currency);
            final JSONObject json = jsonGenerator.generateJSON();
            final JsonFileGenerator jsonFileGenerator = new JsonFileGenerator(json, folderPath);
            jsonFileGenerator.generateFile();

        }
    }
}

