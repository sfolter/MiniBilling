package com.github.methodia.minibilling;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;


public class PriceFileReader implements PricesReader {

    private final String directory;
    private final int priceListNumber;

    public PriceFileReader(final String directory, final int priceListNumber) {
        this.directory = directory;
        this.priceListNumber = priceListNumber;
    }

    @Override
    public List<Price> read() {

        final String path = directory + "prices-" + priceListNumber + ".csv";
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {

            return br.lines()
                    .map(l -> l.split(","))
                    .map(this::createPrice).toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Price createPrice(final String[] dataPrice) {

        final String product = dataPrice[0];
        final ZonedDateTime start = Formatter.parsePriceStart(dataPrice[1]);
        final ZonedDateTime end = Formatter.parsePriceEnd(dataPrice[2]);
        final BigDecimal price = new BigDecimal(dataPrice[3]);

        return new Price(product, start, end, price);
    }
}
