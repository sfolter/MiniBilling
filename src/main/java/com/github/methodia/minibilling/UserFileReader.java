package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class UserFileReader implements UsersReader {

    String usersReadingPath;

    UserFileReader(String usersReadingPath) {
        this.usersReadingPath = usersReadingPath;
    }



    @Override
    public List<User> read() throws IOException {
        PricesFileReader pricesFileReader = new PricesFileReader(usersReadingPath);
        pricesFileReader.read();
        String line;
        List<User> usersList = new LinkedList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(usersReadingPath + "\\users.csv"));
            while ((line = br.readLine()) != null) {
                String[] client = line.split(",");
                String name = client[0];
                String referentNumber = client[1];

                usersList.add(new User(name, referentNumber, Integer.parseInt(client[2]),
                        pricesFileReader.read().get(Integer.valueOf(client[2])), client[3]));
            }
            br.close();
        } catch (IOException e) {
            throw new IOException(e);
        }
        return usersList;
    }

}