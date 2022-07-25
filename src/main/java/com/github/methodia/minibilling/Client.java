package com.github.methodia.minibilling;

/**
 * @author Miroslav Kovachev
 * 25.07.2022
 * Methodia Inc.
 */
public class Client {
    private String name;
    private String reference;
    private int priceListNumber;

    public Client(String name, String reference, int priceListNumber) {
        this.name = name;
        this.reference = reference;
        this.priceListNumber = priceListNumber;
    }

    public String getName() {
        return name;
    }

    public String getReference() {
        return reference;
    }

    public int getPriceListNumber() {
        return priceListNumber;
    }
}
