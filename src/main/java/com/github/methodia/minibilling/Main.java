package com.github.methodia.minibilling;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class Main {

    public static void main(String[] args) {
        //WELCOME TO MINI BILLING!
        //check args and print an error to the user if there are no arguments

        if(args.length < 2) {
            System.out.println("Ne raboti taka");
            return;
        }

        final String yearMonth = args[0];
        final String billingDataDir = args[1];
        final File usersFile = new File(billingDataDir + "users.csv");
        if(!usersFile.exists()) {
            System.out.println("users.csv file missing.");
            return;
        }

        boolean isTrue = false;


        final HashMap<String, List<Reading>> referenceToReadingsMap = new HashMap<>();
        final HashMap<Integer, List<Price>> priceVersionToPricesMap = new HashMap<>();
        final ArrayList<Client> clients = new ArrayList<>();
        for (Client client : clients) {
            final String reference = client.getReference();
            final List<Reading> readings = referenceToReadingsMap.get(reference);
            final HashMap<String, List<Reading>> stringListHashMap = new HashMap<>();

            final int priceListNumber = client.getPriceListNumber();
            final List<Price> prices = priceVersionToPricesMap.get(priceListNumber);

        }


    }

    public ClientProductReadingPair parseToMap(boolean isTrue){
        final ArrayList<String> lines = new ArrayList<>();
        final HashMap<String, List<Reading>> clientRefToReadings = new HashMap<>();
        final HashMap<String, List<Reading>> productKeyToReadings = new HashMap<>();


        final ClientProductReadingPair clientProductReadingPair = new ClientProductReadingPair(clientRefToReadings,
                productKeyToReadings);

        return clientProductReadingPair;
    }
}
