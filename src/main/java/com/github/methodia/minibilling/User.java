package com.github.methodia.minibilling;



public class User {

   private String name;

    private String referentNumber;

    private int numberPricingList;

    public User(String name, String referentNumber, int numberPricingList) {
        this.name = name;
        this.referentNumber = referentNumber;
        this.numberPricingList = numberPricingList;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReferentNumber() {
        return referentNumber;
    }

    public void setReferentNumber(String referentNumber) {
        this.referentNumber = referentNumber;
    }

    public int getNumberPricingList() {
        return numberPricingList;
    }

    public void setNumberPricingList(int numberPricingList) {
        this.numberPricingList = numberPricingList;
    }

}



