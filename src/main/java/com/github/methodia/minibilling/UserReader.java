package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class UserReader implements UsersReader {
    List<User> users = new ArrayList<>();

    @Override
    public Map<String, User> read(String directory) {
        String directoryUsers = directory + "users.csv";
        String line = "";
        Map<String, User> mapOfUser = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(directory + "\\users.csv"));
            while ((line = br.readLine()) != null) {
                String[] client = line.split(",");
                String name = client[0];
                String referentNumber = client[1];
                int priceList = parseInt(client[2]);
                PricesReader pricesReader = new PriceReader();
                List<Price> pricesReader1 = pricesReader.read(directory, priceList);
                mapOfUser.put(referentNumber, new User(name, referentNumber, priceList, pricesReader1));

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapOfUser;
    }

    public List<User> readToList(String directory) {
        String directoryUsers = directory + "users.csv";
        String line = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(directoryUsers));

            while ((line = br.readLine()) != null) {
                String[] client = line.split(",");
                int priceList = Integer.parseInt(client[2]);


                PricesReader pricesReader = new PriceReader();
                List<Price> pricesReader1 = pricesReader.read(directory, priceList);

                users.add(new User(client[0], client[1], priceList, pricesReader1));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}

