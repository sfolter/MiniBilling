package com.github.methodia.minibilling;

public class Clients {

    private final String clientName;
    private final String referenceNumber;
    private final int priceListNumber;

    public Clients(String userName, String referenceNumber, int priceListNumber) {
        this.clientName = userName;
        this.referenceNumber = referenceNumber;
        this.priceListNumber = priceListNumber;
    }


    public String getClientName() {
        return clientName;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public int getPriceListNumber() {
        return priceListNumber;
    }

}
