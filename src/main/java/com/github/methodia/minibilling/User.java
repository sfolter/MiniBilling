package com.github.methodia.minibilling;

import java.util.List;

public class User {

    private final String name;
    private final String ref;
    private final List<Price> price;
    private final int priceListNumber;


    public User(final String name, final String ref, final int priceListNumber, final List<Price> price) {
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
