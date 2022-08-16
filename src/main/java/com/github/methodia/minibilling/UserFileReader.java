package com.github.methodia.minibilling;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class UserFileReader implements UsersReader {
    private final String directory;

    public UserFileReader(String directory) {
        this.directory = directory;
    }

    @Override
    public Map<String, User> read() {
        String directoryUsers = directory + "users.csv";

        Map<String, User> mapOfUser;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(directoryUsers)))) {

            mapOfUser = br.lines()
                    .map(l -> l.split(","))
                    .map(a -> createUser(a, directory))
                    .collect(Collectors.toMap(User::getRef, user -> user));

        } catch (IOException e) {
            throw new RuntimeException();
        }
        return mapOfUser;
    }

    private User createUser(String[] dataForUser, String directory) {

        String name = dataForUser[0];
        String referentNumber = dataForUser[1];
        int priceList = parseInt(dataForUser[2]);
        PriceFileReader pricesReader = new PriceFileReader(directory, priceList);
        List<Price> pricesReader1 = pricesReader.read();

        return new User(name, referentNumber, priceList, pricesReader1);
    }
}

