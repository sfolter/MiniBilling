package com.github.methodia.minibilling;

import com.github.methodia.minibilling.currency.CurrencyCalculator;
import com.github.methodia.minibilling.currency.CurrencyExchangeCalculator;
import com.github.methodia.minibilling.currency.SameCurrency;
import com.github.methodia.minibilling.entity.Reading;
import com.github.methodia.minibilling.entity.User;
import com.github.methodia.minibilling.mainlogic.InvoiceGenerator;
import com.github.methodia.minibilling.mainlogic.MeasurementGenerator;
import com.github.methodia.minibilling.readers.ReadingDao;
import com.github.methodia.minibilling.readers.ReadingsReader;
import com.github.methodia.minibilling.readers.SessionFactoryUtil;
import com.github.methodia.minibilling.readers.UserDao;
import com.github.methodia.minibilling.readers.UsersReader;
import com.github.methodia.minibilling.saveinvoice.SaveInvoice;
import com.github.methodia.minibilling.saveinvoice.SaveInvoiceInDataBase;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static final String ZONE_ID = "GMT";

    public static void main(String[] args) {
                final String resourceDirectory = args[1];
        final String outputDirectory = args[2];
        final String dateToReporting = args[0];
//     String dateToReporting="21-03";
        final LocalDate borderDate = Formatter.parseBorder(dateToReporting);
        final AtomicInteger documentNumberId = new AtomicInteger(10000);

        final String myApiKey = "9ab98220c5e63e8f38644829";
        final String fromCurrency = "BGN";
        final String toCurrency = "BGN";
        final CurrencyCalculator currencyCalculator =
                fromCurrency.compareTo(toCurrency) == 0 ? new SameCurrency() : new CurrencyExchangeCalculator(myApiKey);
        Session session= SessionFactoryUtil.getSession();
        session.beginTransaction();

        //final UsersReader userReader = new UserFileReader(resourceDirectory);
        final UsersReader userReader = new UserDao(session);
        final Map<String, User> users = userReader.read();

        //final ReadingsReader readingReader = new ReadingFileReader(users, resourceDirectory);
        final ReadingsReader readingReader = new ReadingDao(session);
        final Map<String, List<Reading>> readings = readingReader.read();

        final MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        final InvoiceGenerator invoiceGenerator = new InvoiceGenerator(currencyCalculator, fromCurrency);

       //final SaveInvoice saveInvoice=new SaveInvoiceInJson(outputDirectory,borderDate);
       final SaveInvoice saveInvoice=new SaveInvoiceInDataBase(session);

        users.values().stream()
                .map(user -> measurementGenerator.generate(user, readings.get(user.getRef())))
                .map(measurement -> invoiceGenerator.generate(measurement, documentNumberId.getAndIncrement(),
                        borderDate, toCurrency))
                .forEach(invoice -> saveInvoice.save(invoice));
//
    //    session.flush();
   session.close();
    }
}


