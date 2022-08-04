package com.github.methodia.minibilling;

import java.util.List;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class User {
    private String name;
    private String ref;
    private int numberPricingList;
    private List<Price> price;


    public User(String name, String ref, int numberPricingList, List<Price> price) {
        this.name = name;
        this.ref = ref;
        this.price = price;
        this.numberPricingList=numberPricingList;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                ", numberPricingList=" + numberPricingList +
                ", price=" + price +
                '}';
    }
}
