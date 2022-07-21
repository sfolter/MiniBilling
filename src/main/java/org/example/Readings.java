package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Readings {
    public File readings;
    public Readings(File readings){
        this.readings = readings;
    }

    ArrayList<String> product = new ArrayList<>();
    ArrayList<String> data = new ArrayList<>();
    ArrayList<Double> price = new ArrayList<>();

    public void printRead() throws FileNotFoundException {

        Scanner sc = new Scanner(this.readings);
        sc.useDelimiter(" ");
        int counter = 0;
        while (sc.hasNext()) {

            String i = sc.next();
            i.replaceAll("\\s+$", "");

            counter++;

            //System.out.println(counter);
            if (counter == 1) {
                product.add(i);
                //System.out.println("product" + "----" + i);
            }
            if (counter == 2) {
                data.add(i);
                //System.out.println("data" + "----" + i);
            }
            if (counter == 3) {
                price.add(Double.parseDouble(i));
                //System.out.println("price" + "----" + i);
                counter = 0;
            }


            //System.out.println(sc.hasNext());
            //System.out.println(consumers.get(2));
            // System.out.println(counter);

        }
        System.out.println(price.get(1));
        // sc.close();
    }
}


