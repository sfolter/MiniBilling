package com.github.methodia.minibilling;

import java.io.*;
import java.util.*;

public class UsersReaders implements UsersReader {
    public Map<String, User> read() {

        PricesReader pricesReader = new PriceReader();

        Map<String, User> listOfClients = new HashMap<>();

        String line;

        try (BufferedReader br = new BufferedReader(new FileReader("src\\test\\resources\\sample1\\input\\users.csv"))) {

            while ((line = br.readLine()) != null) { //read all lines from b1
                String[] client = line.split(",");

                String clientName = client[0];
                String referenceNumber = client[1];
               // List<Price> price = pricesReader.read().get(Integer.parseInt(client[2]));
               // int priceListNumber = Integer.parseInt(client[3]);

                User user = new User(clientName,referenceNumber,Integer.parseInt(client[2]), pricesReader.read().get(Integer.valueOf(client[2])));
                listOfClients.put(referenceNumber, user);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listOfClients;

    }
}
