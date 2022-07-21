package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;

public class Readings implements FileReader{
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
//        for (int i = 0; i < readingsList.size(); i++) {
//            String[] strings = readingsList.get(i);
//            String refNum = strings[0];
//            String referentenNomer = strings[1];
//            String nomerNaCenovaLista = strings[2];
//            String nomerNaCenovaL = strings[3];
//            System.out.println(refNum + " " + referentenNomer + " " + nomerNaCenovaLista + " " + nomerNaCenovaL );
//
//        }

        return readingsList;
    }
}
