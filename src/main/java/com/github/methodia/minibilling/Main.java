package com.github.methodia.minibilling;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static final String ZONE_ID = "GMT";

    public static void main(String[] args) {

        final String resourceDirectory = args[1];
        final String outputDirectory = args[2];
        final String dateToReporting = args[0];
        final LocalDate borderDate = Formatter.parseBorder(dateToReporting);
        final AtomicLong documentNumberId = new AtomicLong(10000);

        final UserFileReader userReader = new UserFileReader(resourceDirectory);
        final Map<String, User> users = userReader.read();

        final ReadingFileReader readingReader = new ReadingFileReader(users, resourceDirectory);
        final Map<String, List<Reading>> readings = readingReader.read();

        final MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        final InvoiceGenerator invoiceGenerator = new InvoiceGenerator();

        users.values().stream()
                .map(user -> measurementGenerator.generate(user, readings.get(user.getRef())))
                .map(measurement -> invoiceGenerator.generate(measurement, documentNumberId.getAndIncrement(),
                        borderDate))
                .forEach(invoice -> SaveInvoice.saveToFile(invoice, outputDirectory, dateToReporting));
    }
}


