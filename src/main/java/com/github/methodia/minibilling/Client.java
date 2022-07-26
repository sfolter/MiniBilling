package com.github.methodia.minibilling;

public class Client {

    private final String userName;
    private final String referenceNumber;
    private final int priceListNumber;

    public Client(String userName, String referenceNumber, int priceListNumber) {
        this.userName = userName;
        this.referenceNumber = referenceNumber;
        this.priceListNumber = priceListNumber;
    }


    public String getUserName() {
        return userName;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public int getPriceListNumber() {
        return priceListNumber;
    }

}
