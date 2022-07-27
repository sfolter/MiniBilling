package com.github.methodia.minibilling;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Prices {
    public File prices;

    public Prices(File prices) {
        this.prices = prices;
    }

    private ArrayList<String> product = new ArrayList<>();
    private ArrayList<ZonedDateTime> dateBegin = new ArrayList<>();
    private ArrayList<ZonedDateTime> dateEnd = new ArrayList<>();
    private ArrayList<Double> price = new ArrayList<>();

    private int countPrices = 0;

    public Prices(ArrayList<String> product, ArrayList<ZonedDateTime> dateBegin, ArrayList<ZonedDateTime> dateEnd,
                  ArrayList<Double> price, int countPrices){
        this.product = product;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.price = price;
        this.countPrices = countPrices;
    }

    public void read() throws FileNotFoundException {

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

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                ZonedDateTime zdt = LocalDate.parse(i, dtf).atStartOfDay(ZoneOffset.UTC);
                dateBegin.add(zdt);

            }
            if (counter == 3) {

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                ZonedDateTime zdt = LocalDate.parse(i, dtf).atStartOfDay(ZoneOffset.UTC);
                dateEnd.add(zdt);

            }
            if (counter == 4) {
                price.add(Double.parseDouble(i));
                counter = 0;
            }
          countPrices++;
        }
    }
    public String getProduct(int i) {
        return this.product.get(i);
    }
    public ZonedDateTime getDataBegin(int i) {
        return this.dateBegin.get(i);
    }
    public ZonedDateTime getDataEnd(int i) {
        return this.dateEnd.get(i);
    }
    public Double getPrice(int i) {
        return this.price.get(i);
    }
    public Integer getPriceCount(){
        return countPrices/4;
    }


}