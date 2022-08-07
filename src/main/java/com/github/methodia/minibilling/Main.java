package com.github.methodia.minibilling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.*;

public class Main {
    public static final String ZONE_ID = "GMT";

    public static void main(String[] args) {

        String resourceDirectory = args[1];
        String outputDirectory = args[2];
        String dateToReporting = args[0];

        final UserReader userReader = new UserReader();
        final ReadingReader readingReader = new ReadingReader();
        List<User> userList = userReader.readToList(resourceDirectory);
        List<Reading> readingCollection = readingReader.read(resourceDirectory,dateToReporting);

        for (User user : userList) {

            List<Price> priceList = user.getPrice();
            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readingCollection);
            Collection<Measurement> measurementCollection = measurementGenerator.generate();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurementCollection, priceList);
            Invoice invoice = invoiceGenerator.generate();

            try {
                SaveInvoice.savingFiles(outputDirectory, dateToReporting, invoice, user);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


