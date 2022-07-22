package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVUserReader {
    public List<Users> read(String csvFile) {
        List<Users> listOfUsers = new ArrayList<>();
        String line = "";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\user\\Desktop\\users.csv"));

            while ((line = br.readLine()) != null)

            {
                String[] client = line.split(", ");


              listOfUsers.add(new Users(client[0], client[1], Integer.parseInt(client[2])));


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // return listOfUsers;
        return  listOfUsers;
    }
}
