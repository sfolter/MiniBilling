package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.Integer.parseInt;

public class UserReader implements UsersReader {
    List<User> users = new ArrayList<>();

    @Override
    public List<User> read(String directory) {
        String directoryUsers = directory + "users.csv";
        String line = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(directoryUsers));

            while ((line = br.readLine()) != null) {
                String[] client = line.split(",");
                int priceList=Integer.parseInt(client[2]);


                PricesReader pricesReader=new PriceReader();
                List<Price> pricesReader1= pricesReader.read(directory,priceList);

                users.add(new User(client[0], client[1],priceList, pricesReader1));

            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}

