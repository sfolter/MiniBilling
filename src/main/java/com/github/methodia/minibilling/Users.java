package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Users implements FileReader {


    List<String> userNames = new ArrayList<>();

    public List<String> returnNameUser() {

        return userNames;
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
            String name = strings[0];
            userNames.add(strings[0]);


//            String referentenNomer = strings[1];
//            String nomerNaCenovaLista = strings[2];
//            System.out.println(name + " " + referentenNomer + " " + nomerNaCenovaLista);
//
        }

        return userList;
    }


}


