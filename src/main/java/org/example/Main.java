package org.example;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

      User user = new User(new File ("C:\\Users\\user\\Desktop\\MiniBilling\\test.csv"));
      //user.print();



        Readings readings = new Readings(new File("C:\\Users\\user\\Desktop\\MiniBilling\\readings.csv"));
        //readings.printRead();
       Prices prices = new Prices(new File("C:\\Users\\user\\Desktop\\MiniBilling\\prices.csv"));
       prices.print();

    }
}
