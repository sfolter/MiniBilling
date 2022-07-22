package com.github.methodia.minibilling;

public class Users {

    private String userName;
    private String referenceNumber;
    private int priceListNumber;

    public Users(String userName,String referenceNumber, int priceListNumber) {
        this.userName = userName;
        this.referenceNumber = referenceNumber;
        this.priceListNumber = priceListNumber;
    }

    public Users() {
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

    @Override
    public String toString() {
        // return String.format("%s %s %d %n",this.userName,this.referenceNumber,this.priceListNumber);
        return   this.userName + " " +
                this.referenceNumber + " " +
                this.priceListNumber + "\n";
    }
}
