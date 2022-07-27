package com.github.methodia.minibilling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    public File users;
    public User(File users) {
        this.users = users;
    }
    private ArrayList<String> consumers = new ArrayList<>();
    private ArrayList<Integer> reference = new ArrayList<>();
    private ArrayList<Integer> priceList = new ArrayList<>();
    private int counterUsers = 0;
    public User(ArrayList<String> consumers, ArrayList<Integer> reference, ArrayList<Integer> priceList, int counterUsers){
        this.consumers = consumers;
        this.reference = reference;
        this.priceList = priceList;
        this.counterUsers = counterUsers;
    }
    public void read() throws FileNotFoundException {

        Scanner sc = new Scanner(this.users);
        sc.useDelimiter(",|\\r\\n");
        int counter = 0;
        while (sc.hasNext()) {

            String i = sc.next();
            counter++;

            if (counter == 1) {
                consumers.add(i);
            }
            if (counter == 2) {
                reference.add(Integer.parseInt(i));
            }
            if (counter == 3) {
                priceList.add(Integer.parseInt(i));
                counter = 0;
            }
            counterUsers++;
        }
    }
    public String getConsumer(int i) {
       return this.consumers.get(i);
    }
    public Integer getReference(int i) {
        return this.reference.get(i);
    }
    public Integer getPriceList(int i) {
        return this.priceList.get(i);
    }
    public Integer getCount(){
        return this.counterUsers/3;
    }
}
