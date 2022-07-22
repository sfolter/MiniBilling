package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVPriceReader {
    public List<Prices> read(String csvFile) throws IOException {
    ArrayList<Prices> informationForPrices= new ArrayList<>();

        String line = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\user\\MiniBilling1\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv"));
            while ((line = br.readLine()) != null) {
                String[] price = line.split(",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate start = LocalDate.parse(price[1], formatter);
                LocalDate end = LocalDate.parse(price[2], formatter);
                informationForPrices.add(new Prices(price[0], start  , end , Double.parseDouble(price[3])));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return informationForPrices;
    }
}
