package com.github.methodia.minibilling;

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
    private int priceList=0;


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

    public int getPriceList() {
        return priceList;
    }
}
