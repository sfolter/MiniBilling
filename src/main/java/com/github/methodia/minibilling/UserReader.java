package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class UserReader implements UsersReader {
    ArrayList<Client> informationForClient = new ArrayList<>();
    Collection<User> users = new ArrayList<>();

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

    @Override
    public Collection<User> read(String directory) {
        String directoryUsers = directory + "users.csv";
        String line = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(directoryUsers));
            while ((line = br.readLine()) != null) {
                String[] client = line.split(",");
                String priceList=client[2];
                String pricesPath = directory + "prices-" + priceList + ".csv";
                PricesReader pricesReader=new PriceReader();
                List<Price> pricesReader1= pricesReader.read(pricesPath);
                users.add(new User(client[0], client[1], pricesReader1));


            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}

