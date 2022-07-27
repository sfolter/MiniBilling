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
    private ArrayList<Float> quantityList = new ArrayList<>();

    Users users = new Users();
    ArrayList<String[]> readingsList = new ArrayList<>();

    public ArrayList<String> getReferentialNumberReadings() {
        return referentialNumberReadings;
    }

    public ArrayList<String> getProduct() {
        return product;
    }

    public ArrayList<Float> getPokazanie() {
        return pokazanie;
    }

    public ArrayList<ZonedDateTime> getParsedData() {
        return parsedData;
    }

    ArrayList<String> refList = users.returnRefList();

    public ArrayList<Float> getQuantity() {


        int j = 0;
        while (j < refList.size()) {
            for (int i = readingsList.size() / 2; i < readingsList.size() && j < refList.size(); i++) {
                if (refList.get(j).equals(referentialNumberReadings.get(i))) {
                    for (int k = 0; k < refList.size(); k++) {
                        if (referentialNumberReadings.get(i).equals(referentialNumberReadings.get(k))) {
                            float quantity = pokazanie.get(i) - pokazanie.get(k);
                            quantityList.add(quantity);
                            //i = readingsList.size() / 2;
                            j++;
                            break;
                        }

                    }
                }
            }

        }
        return quantityList;
    }

    @Override
    public ArrayList<String[]> reader(String path) {

        String[] readingsLineInArray;


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
