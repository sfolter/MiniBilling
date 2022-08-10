package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvFilePriceReader implements PricesReader {

    @Override
    public List<Price> read(int priceListNum, String path) throws IOException {
        String[] line;
        final ArrayList<Price> priceList = new ArrayList<>();
        String directory = path + "prices-" + priceListNum + ".csv";

        try (CSVReader reader = new CSVReader(new java.io.FileReader(directory))) {
            while ((line = reader.readNext()) != null) {
                String product = line[0];
                LocalDate startDate = LocalDate.parse(line[1]);
                LocalDate endDate = LocalDate.parse(line[2]);
                BigDecimal value = new BigDecimal(line[3]);
                priceList.add(new Price(product, startDate, endDate, value));
            }
            return priceList;
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

