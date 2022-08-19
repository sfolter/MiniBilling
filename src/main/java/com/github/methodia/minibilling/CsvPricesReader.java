package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class CsvPricesReader implements PricesReader {

    private final String path;

    private final int priceListNumber;

    public CsvPricesReader(final String path, final int priceListNumber) {
        this.path = path;
        this.priceListNumber = priceListNumber;
    }

    String[] line;

    @Override
    public List<Price> read() {
        final List<Price> prices = new LinkedList<>();
        final String directory = path + "prices-" + priceListNumber + ".csv";
        try (final CSVReader reader = new CSVReader(new FileReader(directory))) {
            while (null != (line = reader.readNext())) {
                final String product = line[0];
                final LocalDate startDate = LocalDate.parse(line[1]);
                final LocalDate endDate = LocalDate.parse(line[2]);
                final BigDecimal value = new BigDecimal(line[3]);
                prices.add(new Price(product, startDate, endDate, value));
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return prices;
    }
}