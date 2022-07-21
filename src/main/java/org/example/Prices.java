package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Prices {
    public File prices;

    public Prices(File prices) {
        this.prices = prices;
    }

    ArrayList<String> productPrice = new ArrayList<>();
    ArrayList<String> dataBegin = new ArrayList<>();
    ArrayList<String> dataEnd = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();

    public void print() throws FileNotFoundException {

        Scanner sc = new Scanner(this.prices);
        sc.useDelimiter(" ");
        int counter = 0;
        while (sc.hasNext()) {

            String i = sc.next();
           i.replaceAll("\\s+$", "");
           counter++;
            //System.out.println(i);
            //System.out.println(counter);

            //System.out.println(counter);
            if (counter == 1) {
                productPrice.add(i);
                System.out.println("product" + "----" + i);
            }
            if (counter == 2) {
                dataBegin.add(i);
               System.out.println("dataBegin" + "----" + i);
            }
            if (counter == 3) {
                dataEnd.add(i);
               System.out.println("dateEnd" + "----" + i);
                //counter = 0;
            }
            if (counter == 4) {
               // price.add(i);
                System.out.println("price" + "----" + i);
                counter = 0;
            }
            //counter++;

            //System.out.println(sc.hasNext());
            //System.out.println(consumers.get(2));
            // System.out.println(counter);

        }
        //System.out.println(price.get(0));
        // sc.close();
    }
}