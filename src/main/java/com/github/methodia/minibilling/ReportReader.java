package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.*;

public class ReportReader implements  ReadingsReader {
    public Map<String, List<Report>> readReportForGasToMap(String directory) {
        String line = "";
        Map<String, List<Report>> result = new HashMap<>();
        try {
            BufferedReader br =new BufferedReader (new InputStreamReader(new FileInputStream(directory), "UTF-8"));
            while ((line = br.readLine()) != null) {
                String[] report = line.split(",");
                //check kind of product
                if (report[1].equals("gas")) {


                    String referentNumber = report[0];
                    String product = report[1];
                    String date = report[2];
                    ZonedDateTime instant = ZonedDateTime.parse(date);
                    double price = Double.parseDouble(report[3]);

                    List<Report> list = new ArrayList<>();

                    if (result.get(referentNumber) == null) {
                        list.add(new Report(referentNumber, product, instant, price));
                        result.put(referentNumber, list);
                    } else {
                        result.get(referentNumber).add(new Report(referentNumber, product, instant, price));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    @Override
    public Collection<Reading> read(String directory){
        return null;
    }

}
