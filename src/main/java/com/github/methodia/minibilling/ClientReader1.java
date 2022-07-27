package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientReader1 {
    public Map<String, Map<String, Integer>> readClientsToMap(String directory) {
        String line = "";
        Map<String, Map<String,Integer>> client = new HashMap<>();
        Map<String,Integer> clientDetails = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(directory), "UTF-8"));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                //check kind of product

                String referenceNumber=data[0];
                String name= data[1];
                Integer pricelist= Integer.parseInt(data[2]);
                clientDetails.put(referenceNumber,pricelist);
                client.put(name,clientDetails);



            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return client;
    }
}
