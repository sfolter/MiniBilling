package com.github.methodia.minibilling;


import java.util.List;
import java.util.Map;

public class LinesForJsonFile {
    int index;
    double quantity;
    String lineStart;
    String lineEnd;
    String product;
    Double price;
    int priceList;
    Double amount;





    public double getQuantity() {


        return quantity;
    }

    public Double getAmount() {
        amount = price * quantity;
        return amount;
    }

    public String getLineStart() {
        return lineStart;
    }

    public String getLineEnd() {
        return lineEnd;
    }
}
