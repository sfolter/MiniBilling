package com.github.methodia.minibilling;

import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getNumberPricingList() == user.getNumberPricingList() && Objects.equals(getName(), user.getName())
                && Objects.equals(getRef(), user.getRef()) && Objects.equals(getPrice(), user.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getRef(), getNumberPricingList(), getPrice());
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
