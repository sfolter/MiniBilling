package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;

public class Readings implements FileReader {
    static ArrayList<String> referentenZaUser = new ArrayList<>();
    static ArrayList<String> Product = new ArrayList<>();
    static ArrayList<String> DateString = new ArrayList<>();
    static ArrayList<Float> Pokazanie = new ArrayList<>();
    static ArrayList<Instant> Date = new ArrayList<Instant>();




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
            referentenZaUser.add(strings[0]);
            Product.add(strings[1]);
            DateString.add(strings[2]);
            Pokazanie.add(Float.parseFloat(strings[3]));


        }


        return readingsList;
    }

    public ArrayList<Instant> parseToDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");


//        for (String dateString : DateString)

            for (int i = 0; i < DateString.size(); i++) {
                Date.add(Instant.parse(DateString.get(i)));
            }
        return Date;
    }
}




