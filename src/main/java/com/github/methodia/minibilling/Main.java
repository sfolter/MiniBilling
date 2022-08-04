package com.github.methodia.minibilling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        //WELCOME TO MINI BILLING!
        //check args and print an error to the user if there are no arguments

        PriceReader pr = new PriceReader();
        pr.read();
        UsersReader ur = new UsersReaders();
        ur.read();
        ReadingsReader rr = new ReadingsReader();

        Map<String, User> ll = ur.read();
        for (int i = 0; i < ll.size(); i++) {

            int k = i + 1;

            User user = ll.get(String.valueOf(k));

            MeasurementGenerator mmGenerator = new MeasurementGenerator(user, rr.read());
            mmGenerator.generate();

            ProportionalMeasurementDistributor proportionalMmDistributor
                    = new ProportionalMeasurementDistributor(mmGenerator.generate(), user.getPrice());
            proportionalMmDistributor.distribute();

            //  List<QuantityPricePeriod> qppInvoiceList=proportionalMmDistributor.distribute();

            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, mmGenerator.generate(), user.getPrice());
            Invoice invoice = invoiceGenerator.generate();

            System.out.println(invoice);
            System.out.println();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            

        }


    }

}
