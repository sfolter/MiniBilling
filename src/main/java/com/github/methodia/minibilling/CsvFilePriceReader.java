package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvFilePriceReader implements PricesReader {

    @Override
    public List<Price> read(final int priceListNum, final String path) {
        String[] line;
        final ArrayList<Price> priceList = new ArrayList<>();
        final String directory = path + "prices-" + priceListNum + ".csv";

        try (final CSVReader reader = new CSVReader(new FileReader(directory))) {
            while (null != (line = reader.readNext())) {
                final String product = line[0];
                final LocalDate startDate = LocalDate.parse(line[1]);
                final LocalDate endDate = LocalDate.parse(line[2]);
                final BigDecimal value = new BigDecimal(line[3]);
                priceList.add(new Price(product, startDate, endDate, value));
            }
            return priceList;
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

