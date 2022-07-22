package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;

public class Prices implements FileReader {

    static ArrayList<String> product = new ArrayList<>();
    static ArrayList<String> lineStart = new ArrayList<>();
    static ArrayList<String> lineEnd = new ArrayList<>();
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
            product.add(strings[0]);
            lineStart.add(strings[1]);
            lineEnd.add(strings[2]);
            price.add(Float.parseFloat(strings[3]));


        }
        return pricesList;
    }
}
