package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Prices implements FileReader{

    static ArrayList<String> productInPrices = new ArrayList<>();
    static ArrayList<String> startDateString = new ArrayList<>();
    static ArrayList<ZonedDateTime> parsedStartDate = new ArrayList<>();
    static ArrayList<String> endDateString = new ArrayList<>();
    static ArrayList<ZonedDateTime> parsedEndDate = new ArrayList<>();
    static ArrayList<Float> price = new ArrayList<>();
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
            startDateString.add(strings[1]);
            endDateString.add(strings[2]);
            price.add(Float.parseFloat(strings[3]));

        }
        return pricesList;
    }
    public ArrayList<ZonedDateTime> startDateParsing() {
        for (int i = 0; i < startDateString.size(); i++) {
            ZonedDateTime instant = ZonedDateTime.parse(startDateString.get(i));
            parsedStartDate.add(instant);
        }
        return parsedStartDate;
    }
    public ArrayList<ZonedDateTime> endDateParsing() {
        for (int i = 0; i < endDateString.size(); i++) {
            ZonedDateTime instant = ZonedDateTime.parse(endDateString.get(i));
            parsedEndDate.add(instant);
        }
        return parsedEndDate;
    }
}
