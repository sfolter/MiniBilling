package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class Prices implements FileReader {

    static ArrayList<String> productInPrices = new ArrayList<>();
    static ArrayList<LocalDate> parsedStartDate = new ArrayList<>();

    static ArrayList<LocalDate> parsedEndDate = new ArrayList<>();
    static ArrayList<Float> price = new ArrayList<>();

    public ArrayList<String> getProductInPrices() {
        return productInPrices;
    }

    public ArrayList<LocalDate> getParsedStartDate() {
        return parsedStartDate;
    }

    public ArrayList<LocalDate> getParsedEndDate() {
        return parsedEndDate;
    }

    public  ArrayList<Float> getPrice() {
        return price;
    }

    @Override
    public ArrayList<String[]> reader(String path) {
        String[] pricesLineInArray;
        ArrayList<String[]> pricesList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {

            while ((pricesLineInArray = reader.readNext()) != null) {
                pricesList.add(pricesLineInArray);

            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < pricesList.size(); i++) {
            String[] strings = pricesList.get(i);
            productInPrices.add(strings[0]);
            LocalDate parsedStartDate = LocalDate.parse(strings[1]);
            Prices.parsedStartDate.add(parsedStartDate);
            LocalDate parsedEndDate = LocalDate.parse(strings[2]);
            Prices.parsedEndDate.add(parsedEndDate);
            price.add(Float.parseFloat(strings[3]));

        }
        return pricesList;
    }
}

