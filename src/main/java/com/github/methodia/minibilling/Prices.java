package com.github.methodia.minibilling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Prices {
    public File prices;

    public Prices(File prices) {
        this.prices = prices;
    }

    private ArrayList<String> product = new ArrayList<>();
    private ArrayList<String> dateBegin = new ArrayList<>();
    private ArrayList<String> dateEnd = new ArrayList<>();
    private ArrayList<Double> price = new ArrayList<>();

    public Prices(ArrayList<String> product, ArrayList<String> dateBegin, ArrayList<String> dateEnd,  ArrayList<Double> price){
        this.product = product;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.price = price;
    }

    public void print() throws FileNotFoundException {

        Scanner sc = new Scanner(this.prices);
        sc.useDelimiter(",|\\r\\n");
        int counter = 0;
        while (sc.hasNext()) {
            String i = sc.next();
            counter++;

            if (counter == 1) {
                product.add(i);

            }
            if (counter == 2) {
                dateBegin.add(i);

            }
            if (counter == 3) {
                dateEnd.add(i);

            }
            if (counter == 4) {
                price.add(Double.parseDouble(i));
                counter = 0;
            }

        }
    }
    public String getProduct(int i) {
        return this.product.get(i);
    }
    public String getDataBegin(int i) {
        return this.dateBegin.get(i);
    }
    public String getDataEnd(int i) {
        return this.dateEnd.get(i);
    }
    public Double getPrice(int i) {
        return this.price.get(i);
    }

}