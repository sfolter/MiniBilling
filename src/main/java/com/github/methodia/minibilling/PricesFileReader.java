package com.github.methodia.minibilling;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PricesFileReader  implements FileReading {
    final private Path resourceDirectory = Paths.get("src", "test", "resources", "sample1", "input");
    final private String pricesFilePath = resourceDirectory + "\\" + "prices-1.csv";
    String path;

    public PricesFileReader() {
        this.path = pricesFilePath;
    }

    public Map<String, List<Prices>> convertPricesInformationToMap() throws ParseException, IOException {
        String line = "";
        Map<String, List<Prices>> mapOfPricesInformation = new LinkedHashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(path));

        while ((line = br.readLine()) != null) {
            String[] pricesData = line.split(",");
            String product = pricesData[0];
            String stringBeginningDate = pricesData[1];
            String stringEndDate = pricesData[2];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(stringBeginningDate, formatter);
            LocalDate endDate = LocalDate.parse(stringEndDate, formatter);
            Double price = Double.parseDouble(pricesData[3]);

            mapOfPricesInformation.putIfAbsent(product, new ArrayList<>());
            mapOfPricesInformation.get(product).add(new Prices(product,startDate,endDate, price));
        }


        return mapOfPricesInformation;
    }
}

