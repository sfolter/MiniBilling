package com.github.methodia.minibilling;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ReportsFileReader implements FileReading{
    String path;

    public ReportsFileReader(String path) {
        this.path = path;
    }

    public ArrayList<Report> readToArrayList()throws Exception {
        String line = "";
        ArrayList<Report> arrListOfUserInformation=new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null)
            {
                //TODO:Продукт Дата (формат yyyy-MM-dd'T'HH:mm:ss'Z') Стойност (число с плаваща запетая)

                String[] data = line.split(", ");
                String dateAndTime= data[1];
                String input = dateAndTime;
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                LocalDateTime ldt = LocalDateTime.parse(input, inputFormatter);
                double price = Double.parseDouble(data[2]);
                arrListOfUserInformation.add(new Report(data[0],ldt,price));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrListOfUserInformation; }
}
