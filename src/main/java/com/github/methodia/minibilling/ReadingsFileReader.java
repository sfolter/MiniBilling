package com.github.methodia.minibilling;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadingsFileReader implements FileReading {

    String path;


    public ReadingsFileReader(String path) {
        this.path = path;
    }

    public Map<String, List<Readings>> readToArrayList() throws Exception {
        String line = "";
        Map<String, List<Readings>> result = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String referentNumber = data[0];
                String product = data[1];
                String time = data[2];
                ZonedDateTime instant = ZonedDateTime.parse(time);
                double price = Double.parseDouble(data[3]);
                List<Readings> list = new ArrayList<>();
                if (result.get(referentNumber) == null) {
                    list.add(new Readings(referentNumber, product, instant, price));
                    result.put(referentNumber, list);
                } else{
                    result.get(referentNumber).add(new Readings(referentNumber, product, instant, price));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

