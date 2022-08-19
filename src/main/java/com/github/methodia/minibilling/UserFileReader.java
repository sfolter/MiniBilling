package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class UserFileReader implements UsersReader {

    private final String directory;

    public UserFileReader(final String directory) {
        this.directory = directory;
    }

    @Override
    public Map<String, User> read() {
        final String directoryUsers = directory + "users.csv";

        final Map<String, User> mapOfUser;
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(directoryUsers)))) {

            mapOfUser = br.lines()
                    .map(l -> l.split(","))
                    .map(a -> createUser(a, directory))
                    .collect(Collectors.toMap(User::getRef, user -> user));

        } catch (IOException e) {
            throw new RuntimeException();
        }
        return mapOfUser;
    }

    private User createUser(final String[] dataForUser, final String directory) {

        final String name = dataForUser[0];
        final String referentNumber = dataForUser[1];
        final int priceList = parseInt(dataForUser[2]);
        final PriceFileReader pricesReader = new PriceFileReader(directory, priceList);
        final List<Price> pricesReader1 = pricesReader.read();

        return new User(name, referentNumber, priceList, pricesReader1);
    }
}

