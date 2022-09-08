package com.github.methodia.minibilling;

import java.util.List;
import java.util.Objects;

public class User {

    private final String name;
    private final String ref;
    private final int numberPricingList;
    private final List<Price> price;
    private final String currency;

    public User(String name, String ref, int numberPricingList, List<Price> price, String currency) {
        this.name = name;
        this.ref = ref;
        this.numberPricingList = numberPricingList;
        this.price = price;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public String getRef() {
        return ref;
    }

    public int getNumberPricingList() {
        return numberPricingList;
    }

    public List<Price> getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "User[" +
                "name=" + name + ", " +
                "ref=" + ref + ", " +
                "numberPricingList=" + numberPricingList + ", " +
                "price=" + price + ", " +
                "currency=" + currency + ']';
    }


}
