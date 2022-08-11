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

        final UserReader userReader = new UserReader(new PriceReader());

        Map<String, User> users = userReader.read(resourceDirectory);
        final ReadingReader readingReader = new ReadingReader(users);
        List<Reading> readingCollection = readingReader.read(resourceDirectory);
        for (int i = 1; i <= users.size(); i++) {

            User user = users.get(String.valueOf(i));
            List<Price> priceList = user.getPrice();
            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readingCollection);
            Collection<Measurement> measurementCollection = measurementGenerator.generate();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurementCollection, priceList);
            Invoice invoice = invoiceGenerator.generate(documentNumberId.getAndIncrement(), dateToReporting);

            try {
                SaveInvoice.saveToFile(invoice, outputDirectory, dateToReporting);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


