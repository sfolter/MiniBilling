package com.github.methodia.minibilling;

import java.util.List;

public class User {
    private String name;
    private String ref;
    private int priceListNumber;
    private List<Price> price;

    public User(String name, String ref, List<Price> price, int priceListNumber) {
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

    public int getPriceListNumber() {
        return priceListNumber;
    }

    public List<Price> getPrice() {
        return price;
    }

}
