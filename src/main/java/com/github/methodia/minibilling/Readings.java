package com.github.methodia.minibilling;

import org.joda.time.DateTime;


public class Readings {
    private String referenceNumber;
    private String product;
    private DateTime date;
    private int indication;

    public Readings(String referenceNumber, String product, DateTime date, int indication) {


        this.referenceNumber = referenceNumber;
        this.product = product;
        this.date = date;
        this.indication = indication;
        // this.value = value;
    }



    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String getProduct() {
        return product;
    }

    public DateTime getDate() {
        return date;
    }

    public int getIndication() {
        return indication;
    }

    @Override
    public String toString() {
        return this.referenceNumber + " " +
                this.product + " " +
                this.date +" "+ this.indication + "\n";
    }
}
