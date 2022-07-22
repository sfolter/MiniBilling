package com.github.methodia.minibilling;


import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ReadingsFileReader  implements FileReading{
                    String path;

        public ReadingsFileReader(String path) {
                        this.path = path;
                    }

                    public ArrayList<Readings> readToArrayList()throws Exception {
                        String line = "";
                        ArrayList<Readings> arrListOfUserInformation=new ArrayList<>();
                        try {
                            BufferedReader br =new BufferedReader (new FileReader(path));
                            while ((line = br.readLine()) != null)
                            {
                                //TODO:Продукт Дата (формат yyyy-MM-dd'T'HH:mm:ss'Z') Стойност (число с плаваща запетая)

                                String[] data = line.split(",");
                                String referentNumber= data[0];
                                String product= data[1];
                                String time=data[2];
                                ZonedDateTime instant=ZonedDateTime.parse(time);
                                double price= Double.parseDouble(data[3]);

                    arrListOfUserInformation.add(new Readings(referentNumber,product,instant,price));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return arrListOfUserInformation; }
    }

