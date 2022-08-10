package com.github.methodia.minibilling;

import java.util.List;

public class User {
    private String name;
    private String ref;
    private List<Price> price;
    private int priceListNumber;


    public User(String name, String ref, int priceListNumber, List<Price> price) {
        this.name = name;
        this.ref = ref;
        this.price = price;
        this.priceListNumber = priceListNumber;
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

    public int getPriceListNumber() {
        return priceListNumber;
    }
}
