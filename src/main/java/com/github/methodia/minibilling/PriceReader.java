package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PriceReader implements  PricesReader {
    String path;
    public PriceReader(String path) {
        this.path = path;
    }

    @Override
    public Map<Integer, List<Price>> read() {


        File dir = new File(path);

        String[] filesList = dir.list();

        Map<Integer, List<Price>> informationForPrices = new LinkedHashMap<>(); //hashMap -> key(gas) -> Price.class
        String line;

        for (String file1 : filesList) {
            if (file1.contains("prices-")) {

                try (BufferedReader br = new BufferedReader(new FileReader(path + "\\" + file1))) {
//                    List<File> files = Files.list(Paths.get(pricePath1))
//                            .map(Path::toFile)
//                            .filter(File::isFile).toList();

                    String[] arr=String.valueOf(file1).split("prices-");
                    String [] arr2= arr[1].split(".csv");
                    //    String [] arr2= String.valueOf(file1).split("([a-z -.])");
                    int numPricingList=Integer.parseInt(arr2[0]);


//                    files.stream().filter(file -> file.getName().contains("prices-")).forEach(file -> {
//                        String[] split = file.getName().split("prices-");
//                        String [] arr2= split[1].split(".csv");
//                        int numPricingList=Integer.parseInt(arr2[0]);
//
//
//                    });
                    while ((line = br.readLine()) != null) { //read all lines from br


                        String[] price = line.split(",");

                        String product = price[0];

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate start = LocalDate.parse(price[1], formatter);
                        LocalDate end = LocalDate.parse(price[2], formatter);
                        double price1 = Double.parseDouble(price[3]);
                        List<Price> list = new ArrayList<>();
                        if (informationForPrices.get(numPricingList) == null) {
                            list.add(new Price(product, start, end, BigDecimal.valueOf(price1)));
                            informationForPrices.put(numPricingList, list);
                        } else {
                            informationForPrices.get(numPricingList).add(new Price(product, start, end, BigDecimal.valueOf(price1)));
                        }

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return informationForPrices;

    }}



