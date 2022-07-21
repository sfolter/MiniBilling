package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class User{
    public File users;

    public User(File users) {
        this.users = users;
    }

    ArrayList<String> consumers = new ArrayList<>();
    ArrayList<String> reference = new ArrayList<>();
    ArrayList<Integer> priceList = new ArrayList<>();

    public void print() throws FileNotFoundException {

        Scanner sc = new Scanner(this.users);
        sc.useDelimiter(" ");
        int counter = 0;
        while (sc.hasNext()) {

            String i = sc.next().trim();
            //System.out.println(i);
            counter++;
            if (counter == 1 ) {
                consumers.add(i);
                System.out.println("consumer" + "----" + i);
            }
            if (counter == 2){
                reference.add(i);
                System.out.println("reference" + "----" + i);
            }
            if (counter == 3) {
                priceList.add(Integer.parseInt(i));
               System.out.println("priceList" + "----" + i);
               counter = 0;
            }


            //System.out.println(sc.hasNext());
           // System.out.println(consumers.get(2));
           // System.out.println(counter);

        }
       //System.out.println(consumers);
       // sc.close();
    }

//    public String dirName() {
//        String name = consumers.get(0);
//        String ref = reference.get(0);
//        String directoryName = name + "-" + ref;
//        System.out.println(directoryName);
//        return directoryName;
//    }
}
