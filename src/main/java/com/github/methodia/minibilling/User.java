package com.github.methodia.minibilling;

import java.util.List;

public class User {

    private final String name;
    private final String ref;
    private final int numberPricingList;
    private final List<Price> price;

    public User(String name, String ref, int numberPricingList, List<Price> price) {
        this.name = name;
        this.ref = ref;
        this.price = price;
        this.numberPricingList = numberPricingList;
    }

    public int getNumberPricingList() {
        return numberPricingList;
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