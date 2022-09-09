package com.github.methodia.minibilling;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(final String[] args)
            throws ParseException, IOException, org.json.simple.parser.ParseException, SQLException,
            ClassNotFoundException {
        //        final String yearMonthStr = args[0];
        //        final String resourceDir = args[1];
        //        final String outputDir = args[2];

        final String resourceDir = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        final String outputDir = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\test\\";
        final String yearMonthStr = "21-03";
        final String toCurrency = "BGN";
        int a = 1;


        //user database
        UserDataBaseReader userDataBaseReader = new UserDataBaseReader();
        Map<String, User> userMapDataBase = userDataBaseReader.read();

        //user.csv
        final CsvFileUserReader userFileRead = new CsvFileUserReader();
        final TreeMap<String, User> userMap = userFileRead.read(resourceDir);

        //readings.csv
        final CsvFileReadingReader reading = new CsvFileReadingReader();
        final Collection<Reading> readings = reading.read(userMap, resourceDir);

        //readings database
        ReadingsDataBaseReader readingsDataBaseReader = new ReadingsDataBaseReader();
        Collection<Reading> readingsDataBase = readingsDataBaseReader.read(userMapDataBase);

        //Creating objects
        final MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        final InvoiceGenerator invoiceGenerator = new InvoiceGenerator(new CurrencyConvertor());
        final FolderGenerator folderGenerator = new FolderGenerator();
        final JsonGenerator jsonGenerator = new JsonGenerator();
        final JsonFileGenerator jsonFileGenerator = new JsonFileGenerator();

        for (final String refNumb : userMap.keySet()) {
            final User user = userMap.get(refNumb);
            final List<Price> priceList = user.price();
            final Collection<Measurement> measurmentGenerated = measurementGenerator.generate(user, readings);
            final Invoice invoice = invoiceGenerator.generate(user, measurmentGenerated, priceList, yearMonthStr,
                    toCurrency);
            final String folder = folderGenerator.generate(user, outputDir);
            final JSONObject jsonInvoiceObject = jsonGenerator.generate(invoice, toCurrency);
            jsonFileGenerator.generateJsonFile(jsonInvoiceObject, folder);


        }
    }
}
