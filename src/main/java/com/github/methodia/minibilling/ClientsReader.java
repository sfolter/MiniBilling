package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ClientsReader {
    public ArrayList<Clients> read() {
        ArrayList<Clients> listOfClients = new ArrayList<>();
        String line;

        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\test\\resources\\sample1\\input\\users.csv"));

            while ((line = br.readLine()) != null) { //read all lines from b1
                String[] client = line.split(",");

                String clientName = client[0];
                String referenceNumber = client[1];
                int priceListNumber = Integer.parseInt(client[2]);

                listOfClients.add(new Clients(clientName, referenceNumber, priceListNumber));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listOfClients; //arrayList
    }
}


