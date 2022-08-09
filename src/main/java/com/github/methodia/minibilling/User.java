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
    private static List<Price> price;
    private static int priceList;


    public User(String name, String ref, int priceList, List<Price> price) {
        this.name = name;
        this.ref = ref;
        this.price = price;
        this.priceList = priceList;
    }
    public String getName() {
        return name;
    }

    public String getRef() {
        return ref;
    }

    public static List<Price> getPrice() {
        return price;
    }

    public static int getPriceList() {
        return priceList;
    }
}
