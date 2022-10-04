package com.github.methodia.minibilling;

import org.hibernate.Session;
import org.json.JSONObject;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class Main {

    public static final String API_KEY = "1f16554ded67538b17b5bc97";

    //public static final String CURRENCY = "EUR";
    public static void main(final String[] args)
            throws ParseException, IOException, NoSuchFieldException, IllegalAccessException,
            org.json.simple.parser.ParseException, SQLException, ClassNotFoundException {
        //                final String reportDate = args[0];
        //                final String inPath = args[1];
        //                final String outPath = args[2];
        final String reportDate = "21-03";
        //        main sample1
        //        String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        //        String outPath="C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\output\\";
        //        main sample2
        //        final String inPath = "C:\\java projects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        final String outPath = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample2\\test\\";
        final String currency = "EUR";

        final SessionGenerator sessionGenerator = new SessionGenerator();
        final Session session = sessionGenerator.createSession();
        session.beginTransaction();
        final UserHibernateDataBaseReader userHibernateDataBaseReader = new UserHibernateDataBaseReader(session);
        final Map<String, User> userList = userHibernateDataBaseReader.read();

        for (final Map.Entry<String, User> userFromMap : userList.entrySet()) {
            final User user = userFromMap.getValue();
            final PriceLists priceList = user.getPriceList();
            final Set<Price> price = priceList.getPrices();
            final MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, user.getPrice());
            final Collection<Measurement> measurements = measurementGenerator.generate();
            final InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurements, price, reportDate,
                    currency);
            final Invoice invoice = invoiceGenerator.generate();
            final FolderGenerator folderGenerator = new FolderGenerator(user, outPath);
            final String folderPath = folderGenerator.folderGenerate();
            final JsonFileGenerator jsonGenerator = new JsonFileGenerator(invoice, folderPath, currency, user);
            final JSONObject json = jsonGenerator.generateJSON();
            final JsonFileGenerator jsonFileGenerator = new JsonFileGenerator(invoice, folderPath, currency, user);
            jsonFileGenerator.generateJSON();
            //session.getTransaction().commit();
        }
    }
}