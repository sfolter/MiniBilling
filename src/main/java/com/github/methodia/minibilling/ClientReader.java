package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class ClientReader {
    ArrayList<Client> informationForClient = new ArrayList<>();
    public ArrayList<Client> readClientsToList(String directory) {
        String line = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(directory));
            while ((line = br.readLine()) != null) {
                String[] client = line.split(",");
                informationForClient.add(new Client(client[0], client[1], parseInt(client[2])));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return informationForClient;
    }
}
