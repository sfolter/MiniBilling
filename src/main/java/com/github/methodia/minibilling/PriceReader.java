package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;


public class PriceReader implements PricesFileReader {
    @Override
    public List<Price> read(String directory, int priceListNumber) {

        String path = directory + "prices-" + priceListNumber + ".csv";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {

            return br.lines()
                    .map(l -> l.split(","))
                    .map(this::createPrice).toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Price createPrice(String[] dataPrice) {

        String product = dataPrice[0];
        ZonedDateTime start = Formatter.parsePriceStart(dataPrice[1]);
        ZonedDateTime end = Formatter.parsePriceEnd(dataPrice[2]);
        BigDecimal price = new BigDecimal(dataPrice[3]);

        return new Price(product, start, end, price);
    }
}
