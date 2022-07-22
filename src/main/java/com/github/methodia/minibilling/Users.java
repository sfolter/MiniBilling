package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;

public class Users implements FileReader {
    static ArrayList<String> namesFolders = new ArrayList<>();
    static ArrayList<String> referentenZaPapka = new ArrayList<>();
    static ArrayList<Integer> numberofPriceList =new ArrayList<>();

    public ArrayList<String> getNamesForFolders() {
        return namesFolders;
    }

    public ArrayList<String> getReferentenForFolders() {
        return referentenZaPapka;
    }

    public ArrayList<Integer> getNumberofPriceListPriceList(){
        return numberofPriceList;
    }


    public ArrayList<String[]> reader(String path) {
        String[] lineInArray;
        ArrayList<String[]> userList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {

            while ((lineInArray = reader.readNext()) != null) {
                userList.add(lineInArray);

            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < userList.size(); i++) {
            String[] strings = userList.get(i);

            namesFolders.add(strings[0]);
            referentenZaPapka.add(strings[1]);
            numberofPriceList.add(Integer.parseInt(strings[2]));

        }

        return userList;
    }
}
