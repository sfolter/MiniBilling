package com.github.methodia.minibilling;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class UsersReaders implements UsersReader {
    private final String path;
    public UsersReaders(String inputPath) {
        this.path = inputPath;
    }

    public Map<String, User> read() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path+"\\" + "users.csv")))) {

           return br.lines().map(l->l.split(","))
                   .map(this::createGraph).collect(Collectors.toMap(User::getRef,u->u));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private User createGraph(String[] client) {
        PricesReader pricesReader = new PriceReader(path);
        String clientName = client[0];
        String referenceNumber = client[1];
        return new User(clientName,referenceNumber,Integer.parseInt(client[2]), pricesReader.read().get(Integer.valueOf(client[2])));
    }
}
