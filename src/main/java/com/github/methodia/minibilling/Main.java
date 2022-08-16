package com.github.methodia.minibilling;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    public static final String ZONE_ID = "GMT";

    public static void main(String[] args) {
        String resourceDirectory = args[1];
        String outputDirectory = args[2];
        String dateToReporting = args[0];
        AtomicLong documentNumberId = new AtomicLong(10000);

        final UserFileReader userReader = new UserFileReader(resourceDirectory);
        Map<String, User> users = userReader.read();

        final ReadingFileReader readingReader = new ReadingFileReader(users, resourceDirectory);
        List<Reading> readingCollection = readingReader.read();
//        for (int i = 1; i <= users.size(); i++) {
//
//            User user = users.get(String.valueOf(i));
//            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readingCollection);
//            List<Measurement> measurementCollection = measurementGenerator.generate();
//
//            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurementCollection);
//            Invoice invoice = invoiceGenerator.generate(documentNumberId.getAndIncrement(), dateToReporting);
//            SaveInvoice.saveToFile(invoice, outputDirectory, dateToReporting);
//
//        }
        users.values().stream()
                .map(user -> new MeasurementGenerator(user, readingCollection).generate())
                .map(measurement -> new InvoiceGenerator(measurement).generate(documentNumberId.getAndIncrement(), dateToReporting))
                .forEach(invoice -> SaveInvoice.saveToFile(invoice, outputDirectory, dateToReporting));
    }
}


