package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;

public class Users implements FileReader {
    static ArrayList<String> nameList = new ArrayList<>();
    static ArrayList<String> refList = new ArrayList<>();

    static ArrayList<Integer> numOfPriceList = new ArrayList<>();

    public ArrayList<String> returnNameList() {
        return nameList;
    }

    public ArrayList<String> returnRefList() {
        return refList;
    }

    public ArrayList<Integer> numOfPrice() {
        return numOfPriceList;
    }


    @Override
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
            nameList.add(strings[0]);
            refList.add(strings[1]);
            numOfPriceList.add(Integer.parseInt(strings[2]));
        }

        return userList;
    }

}


