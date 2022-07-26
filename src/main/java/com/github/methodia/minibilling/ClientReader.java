package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ClientReader {
    public ArrayList<Client> read() {
        ArrayList<Client> listOfUsers = new ArrayList<>();
        String line;

        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\test\\resources\\sample1\\input\\users.csv"));

            while ((line = br.readLine()) != null) {
                String[] client = line.split(",");


                listOfUsers.add(new Client(client[0], client[1], Integer.parseInt(client[2])));


            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return listOfUsers;
    }
}


