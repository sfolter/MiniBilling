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
                //check kind of product

                String product = data[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDateStart = LocalDate.parse(data[1], formatter);
                int yearStart = localDateStart.getYear();
                int monthValueStart = localDateStart.getMonthValue();
                int dayOfMonthStart = localDateStart.getDayOfMonth();
                ZonedDateTime start = ZonedDateTime.of(yearStart, monthValueStart, dayOfMonthStart,
                        0, 0, 0, 0, ZoneId.of(ZONE_ID));
                LocalDate localDateEnd = LocalDate.parse(data[2], formatter);
                int yearEnd = localDateEnd.getYear();
                int monthValueEnd = localDateEnd.getMonthValue();
                int dayOfMonthEnd = localDateEnd.getDayOfMonth();
                ZonedDateTime end = ZonedDateTime.of(yearEnd, monthValueEnd, dayOfMonthEnd,
                        23, 59, 59, 0, ZoneId.of(ZONE_ID));
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
