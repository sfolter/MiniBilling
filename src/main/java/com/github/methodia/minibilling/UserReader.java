package com.github.methodia.minibilling;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserReader implements UserReaderInterface {
    private final String path;

    public UserReader(String inputPath) {
        this.path = inputPath;
    }

    public Map<String, User> read() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "\\" + "users.csv")))) {

            return br.lines().map(l -> l.split(",")).map(this::createUser).collect(Collectors.toMap(User::getRef, u -> u));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private User createUser(String[] userLine) {
        PriceReaderInterface pricesReader = new PriceReader(path);
        String userName = userLine[0];
        String referenceNumber = userLine[1];
        return new User(userName, referenceNumber, Integer.parseInt(userLine[2]), pricesReader.read().get(Integer.valueOf(userLine[2])));
    }
}
