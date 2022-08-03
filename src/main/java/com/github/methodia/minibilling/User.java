package com.github.methodia.minibilling;

import java.util.List;

public class User {
    private String name;
    private String ref;
    private int priceListNumber;
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
                ", price=" + price +
                '}';
    }
}
