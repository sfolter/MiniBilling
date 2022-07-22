package com.github.methodia.minibilling;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class PricesFileReader {
    String path;

    public PricesFileReader(String path) {
        this.path = path;
    }

    public ArrayList<Prices> readToArrayList() throws ParseException {
        String line = "";
        ArrayList<Prices> arrListOfUserInformation=new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null)
            {
                String[] pricesData = line.split(",");
                String stringBeginningDate =pricesData[1];
                String stringEndDate =pricesData[2];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate start = LocalDate.parse(stringBeginningDate, formatter);
                LocalDate end = LocalDate.parse(stringEndDate, formatter);
                Double price= Double.parseDouble(pricesData[3]);

                arrListOfUserInformation.add(new Prices(pricesData[0],start,end,price));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrListOfUserInformation; }

}
