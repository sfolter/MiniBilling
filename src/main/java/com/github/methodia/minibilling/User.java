package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class User{
    private String name;
    private String ref;
    private List<Price> price;

    public User(String name, String ref, List<Price> price) {
        this.name = name;
        this.ref = ref;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public String getRef() {
        return ref;
    }

    public List<Price> getPrice() {
        return price;
    }



}
