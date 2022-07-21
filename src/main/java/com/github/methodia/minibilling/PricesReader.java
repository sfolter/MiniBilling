package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class PricesReader {
    public  ArrayList<Prices> informationForPrices= new ArrayList<>();
    public ArrayList<Prices> readPricesToList(String directory) {
        String line = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(directory));
            while ((line = br.readLine()) != null) {
                String[] price = line.split(",");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date start = simpleDateFormat.parse(price[1]);
                Date end = simpleDateFormat.parse(price[2]);
                LocalDate.now();
                informationForPrices.add(new Prices(price[0], start  , end , Double.parseDouble(price[3])));
            }
        } catch (IOException|ParseException e) {
            throw new RuntimeException(e);
        }
        return informationForPrices;
    }
}
