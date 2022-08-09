package com.github.methodia.minibilling;

import java.util.List;

public class User {
    private final String name;
    private final String ref;
    private final int priceListNumber;
    private final List<Price> price;

    public User(String name, String ref, int priceListNumber, List<Price> price) {
        this.name = name;
        this.ref = ref;
        this.priceListNumber = priceListNumber;
        this.price = price;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                ", priceListNumber=" + priceListNumber +
                ", price=" + price +
                '}';
    }
}
