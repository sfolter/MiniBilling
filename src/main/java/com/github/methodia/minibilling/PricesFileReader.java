package com.github.methodia.minibilling;


import com.github.methodia.minibilling.entityClasses.Price;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PricesFileReader {

    String path;

    public PricesFileReader(final String path) {
        this.path = path;
    }

    public Map<Integer, List<Price>> read() {

        final Map<Integer, List<Price>> result = new LinkedHashMap<>();
        final File directoryPath = new File(path);
        String line;
        final String[] contents = directoryPath.list();
        assert null != contents;
        for (final String fileName : contents) {
            final Pattern p = Pattern.compile("prices-(\\d+)\\.csv");
            final Matcher matcher = p.matcher(fileName);

            if (matcher.find()) {
                final BufferedReader br;
                final int numberPricingList = Integer.parseInt(matcher.group(1));
                try (final FileReader fileReader = new FileReader(path + "\\" + fileName)) {
                    br = new BufferedReader(fileReader);
                    while (null != (line = br.readLine())) {
                        final String[] pricesData = line.split(",");
                        final String product = pricesData[0];
                        final String stringBeginningDate = pricesData[1];
                        final String stringEndDate = pricesData[2];

                        final LocalDate start = convertStringIntoLocalDate(stringBeginningDate);
                        final LocalDate end = convertStringIntoLocalDate(stringEndDate);
                        final BigDecimal price = new BigDecimal(pricesData[3]);

                        final List<Price> list = new ArrayList<>();
                        if (null == result.get(numberPricingList)) {
                            list.add(new Price(product, start, end, price));
                            result.put(numberPricingList, list);
                        } else {
                            result.get(numberPricingList).add(new Price(product, start, end, price));
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        return result;
    }

    /**
     * Parsing String into LocalDate in format yyyy-MM-dd
     */
    private LocalDate convertStringIntoLocalDate(final String date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(date, formatter);

    }


}

