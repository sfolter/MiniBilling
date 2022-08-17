package com.github.methodia.minibilling;

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
        List<Reading> readings = readingReader.read();

        MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();

        users.values().stream()
                .map(user -> measurementGenerator.generate(user, readings))
                .map(measurement -> invoiceGenerator.generate(measurement, documentNumberId.getAndIncrement(), dateToReporting))
                .forEach(invoice -> SaveInvoice.saveToFile(invoice, outputDirectory, dateToReporting));
    }
}


