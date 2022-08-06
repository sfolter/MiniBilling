package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class UserFileReader  implements UsersReader {
    String usersReadingPath;
    UserFileReader(String usersReadingPath){
        this.usersReadingPath=usersReadingPath;
    }





    @Override
    public Map<String, User> read() throws IOException {
        PricesFileReader pricesFileReader=new PricesFileReader(usersReadingPath);
        pricesFileReader.read();
        String line = "";
        Map<String,User> mapOfUser=new LinkedHashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(usersReadingPath+"\\users.csv"));
            while ((line = br.readLine()) != null)
            {
                String[] client = line.split(",");
                String name=client[0];
                String referentNumber=client[1];

                User user=new User( name,referentNumber,Integer.parseInt(client[2]), pricesFileReader.read().get(Integer.valueOf(client[2])));

                mapOfUser.put(referentNumber,user);

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapOfUser;
    }

}