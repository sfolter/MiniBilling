package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.methodia.minibilling.Main.ZONE_ID;

public class PriceReader implements PricesReader {

    @Override
    public List<Price> read(String directory, int priceListNumber) {
        String line = "";
        List<Price> result = new ArrayList<>();
        String path = directory + "prices-" + priceListNumber + ".csv";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");


                String product = data[0];
                ZonedDateTime start = Formatter.parsePriceStart(data[1]);
                ZonedDateTime end =Formatter.parsePriceEnd(data[2]);
                BigDecimal price = new BigDecimal(data[3]);

                result.add(new Price(product, start, end, price));
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
