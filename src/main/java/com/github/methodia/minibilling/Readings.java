package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Readings implements FileReader {
    static ArrayList<String> referentialNumberReadings = new ArrayList<>();
    static ArrayList<String> product = new ArrayList<>();
    static ArrayList<String> dataString = new ArrayList<>();
    static ArrayList<Float> pokazanie = new ArrayList<>();
    static ArrayList<ZonedDateTime> parsedData = new ArrayList<ZonedDateTime>();

    @Override
    public ArrayList<String[]> reader(String path) {
        String[] readingsLineInArray;
        ArrayList<String[]> readingsList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {

            while ((readingsLineInArray = reader.readNext()) != null) {
                readingsList.add(readingsLineInArray);

            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < readingsList.size(); i++) {
            String[] strings = readingsList.get(i);
            referentialNumberReadings.add(strings[0]);
            product.add(strings[1]);
            dataString.add(strings[2]);
            pokazanie.add(Float.parseFloat(strings[3]));

        }

        return readingsList;
    }

    public ArrayList<ZonedDateTime> dateParsing() {
        for (int i = 0; i < dataString.size(); i++) {
            ZonedDateTime instant = ZonedDateTime.parse(dataString.get(i));
            parsedData.add(instant);
        }
        return parsedData;
    }
}
