package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class PricesReader {
    public ArrayList<Prices> readPricesToList(String directory) {
        String line = "";
        BufferedReader br;
        ArrayList<Prices> informationForPrices = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(directory));

            while ((line = br.readLine()) != null) {
                String[] price = line.split(",");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate start = LocalDate.parse(price[1], formatter);
                LocalDate end = LocalDate.parse(price[2], formatter);

                informationForPrices.add(new Prices(price[0], start, end, Double.parseDouble(price[3])));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return informationForPrices;
    }
}
