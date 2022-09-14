package com.github.methodia.minibilling;

import com.github.methodia.minibilling.dataSave.JsonFileCreator;
import org.hibernate.Session;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException {
        String dateReportingTo = args[0];
        LocalDateTime dateReportingToLDT = convertingBorderTimeIntoLDT(dateReportingTo);
        String inputPath = args[1];
        String outputPath = args[2];

        Session session = SessionCreator.createSession();
        List<User> result = readData(new UserDbRead(session));

        MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        CurrencyConverter currencyConverter = new CurrencyRate();
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator(currencyConverter);
        List<VatPercentages> vatPercentages = new ArrayList<>();
        vatPercentages.add(new VatPercentages(new BigDecimal("60"), new BigDecimal("20")));
        vatPercentages.add(new VatPercentages(new BigDecimal("40"), new BigDecimal("10")));

        for (User user : result) {
            List<Reading> userReadings = user.getReadingsList();

            Collection<Measurement> userMeasurements = measurementGenerator.generate(user, userReadings);

            Invoice invoice = invoiceGenerator.generate(user, userMeasurements, dateReportingToLDT,
                    vatPercentages);

            new JsonFileCreator(invoice, outputPath, user).save();
            session.persist(invoice);

        }
        session.getTransaction().commit();
    }



    private static LocalDateTime convertingBorderTimeIntoLDT(String borderDateString) {
        final YearMonth yearMonth = YearMonth.parse(borderDateString, DateTimeFormatter.ofPattern("yy-MM"));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        ZonedDateTime borderTimeZDT = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.of("Z"));
        return LocalDateTime.parse(String.valueOf(borderTimeZDT), formatter);
    }

    private static List<User> readData(DataReader dr) {
        return dr.read();
    }
}



