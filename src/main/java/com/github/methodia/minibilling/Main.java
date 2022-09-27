package com.github.methodia.minibilling;

import com.github.methodia.minibilling.dataRead.DataReader;
import com.github.methodia.minibilling.dataRead.UserDbRead;
import com.github.methodia.minibilling.dataSave.DataBaseParser;
import com.github.methodia.minibilling.dataSave.JsonFileCreator;
import com.github.methodia.minibilling.dataSave.SaveData;
import com.github.methodia.minibilling.entityClasses.Invoice;
import com.github.methodia.minibilling.entityClasses.Reading;
import com.github.methodia.minibilling.entityClasses.User;
import org.hibernate.Session;

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

    public static void main(final String[] args) {
        final String dateReportingTo = args[0];
        final LocalDateTime dateReportingToLDT = convertingBorderTimeIntoLDT(dateReportingTo);
       // final String inputPath = args[1];
        final String outputPath = args[2];

        final Session session = SessionCreator.createSession();
        final List<User> result = readData(new UserDbRead(session));

        final MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        final CurrencyConverter currencyConverter = new CurrencyRate();
        final InvoiceGenerator invoiceGenerator = new InvoiceGenerator(currencyConverter);
        final List<VatPercentages> vatPercentages = new ArrayList<>();
        vatPercentages.add(new VatPercentages(new BigDecimal("60"), new BigDecimal("20")));
        vatPercentages.add(new VatPercentages(new BigDecimal("40"), new BigDecimal("10")));

        result.forEach(user -> {
            final List<Reading> userReadings = user.getReadingsList();
            final Collection<Measurement> userMeasurements = measurementGenerator.generate(user, userReadings);
            final Invoice invoice = invoiceGenerator.generate(user, userMeasurements, dateReportingToLDT,
                    vatPercentages);
            saveData(new JsonFileCreator(invoice, user, outputPath));
            saveData(new DataBaseParser(invoice, session));
        });
        session.getTransaction().commit();
    }



    private static LocalDateTime convertingBorderTimeIntoLDT(final String borderDateString) {
        final YearMonth yearMonth = YearMonth.parse(borderDateString, DateTimeFormatter.ofPattern("yy-MM"));
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        final ZonedDateTime borderTimeZDT = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.of("Z"));
        return LocalDateTime.parse(String.valueOf(borderTimeZDT), formatter);
    }

    private static List<User> readData(final DataReader dr) {
        return dr.read();
    }

    private static void saveData(final SaveData saveData) {
        saveData.save();
    }
}



