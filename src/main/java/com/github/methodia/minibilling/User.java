package com.github.methodia.minibilling;

import java.util.List;

public class User {
    private final String name;
    private final String ref;
    private final List<Price> price;
    private final int priceListNumber;


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
