package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Readings implements FileReader {
    static ArrayList<String> referentenZaUser = new ArrayList<>();
    static ArrayList<String> Product = new ArrayList<>();
    static ArrayList<String> DateString = new ArrayList<>();
    static ArrayList<Float> Pokazanie = new ArrayList<>();
    static ArrayList<ZonedDateTime> Date = new ArrayList<ZonedDateTime>();




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

    public ArrayList<ZonedDateTime> parseToDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");


//        for (String dateString : DateString)

            for (int i = 0; i < DateString.size(); i++) {
                ZonedDateTime instant = ZonedDateTime.parse(DateString.get(i));
                Date.add(instant);
            }
        return Date;
    }
}




