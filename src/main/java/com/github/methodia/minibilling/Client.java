package com.github.methodia.minibilling;

public class Client {
    private String name;
    private String referenseNumber;
    private Integer numberOfPriceList;
    public Client(String name, String referenseNumber, Integer numberOfPriceList) {
        this.name = name;
        this.referenseNumber = referenseNumber;
        this.numberOfPriceList = numberOfPriceList;
    }

    public int getNumberOfPriceList() {
        return numberOfPriceList;
    }

    public void setNumberOfPriceList(int numberOfPriceList) {
        this.numberOfPriceList = numberOfPriceList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReferenseNumber() {
        return referenseNumber;
    }

    public void setReferenseNumber(String referenseNumber) {
        this.referenseNumber = referenseNumber;
    }

    @Override
    public String toString() {
        return name + " " + referenseNumber + " " +numberOfPriceList;
    }
}
