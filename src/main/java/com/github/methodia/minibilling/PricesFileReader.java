package com.github.methodia.minibilling;


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

public class PricesFileReader implements PricesReader {

    String path;

    PricesFileReader(String path) {
        this.path = path;
    }

    @Override
    public Map<Integer, List<Price>> read() {

        Map<Integer, List<Price>> result = new LinkedHashMap<>();
        File directoryPath = new File(path);
        String line;
        String[] contents = directoryPath.list();
        assert contents != null;
        for (String fileName : contents) {
            Pattern p = Pattern.compile("prices-(\\d+)\\.csv");
            Matcher m = p.matcher(fileName);

            if (m.find()) {
                BufferedReader br;
                int numberPricingList = Integer.parseInt(m.group(1));
                try (FileReader fileReader = new FileReader(path + "\\" + fileName)) {
                    br = new BufferedReader(fileReader);
                    while ((line = br.readLine()) != null) {
                        String[] pricesData = line.split(",");
                        String product = pricesData[0];
                        String stringBeginningDate = pricesData[1];
                        String stringEndDate = pricesData[2];

                        LocalDate start = convertStringIntoLocalDate(stringBeginningDate);
                        LocalDate end = convertStringIntoLocalDate(stringEndDate);
                        BigDecimal price = new BigDecimal(pricesData[3]);

                        List<Price> list = new ArrayList<>();
                        if (result.get(numberPricingList) == null) {
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
    private LocalDate convertStringIntoLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(date, formatter);

    }


}

