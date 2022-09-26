package com.github.methodia.minibilling.dataRead;

import com.github.methodia.minibilling.PricesFileReader;
import com.github.methodia.minibilling.ReadingsFileReader;
import com.github.methodia.minibilling.entityClasses.Price;
import com.github.methodia.minibilling.entityClasses.PriceList;
import com.github.methodia.minibilling.entityClasses.Reading;
import com.github.methodia.minibilling.entityClasses.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UserFileReader implements DataReader {

    String usersReadingPath;

    UserFileReader(final String usersReadingPath) {
        this.usersReadingPath = usersReadingPath;
    }



    @Override
    public List<User> read() {
        final PricesFileReader pricesFileReader = new PricesFileReader(usersReadingPath);
        final Map<Integer, List<Price>> pricesMap = pricesFileReader.read();
        String line;
        final List<Reading> readings = new ReadingsFileReader(usersReadingPath).read();
        final List<User> usersList = new LinkedList<>();
        try {
            final BufferedReader br = new BufferedReader(new FileReader(usersReadingPath + "\\users.csv"));
            while (null != (line = br.readLine())) {
                final String[] client = line.split(",");
                final String name = client[0];
                final String referentNumber = client[1];
                final String priceListNumber = client[2];
                final String currency = client[3];
                final List<Price> priceList = pricesMap.get(Integer.parseInt(priceListNumber));
                final List<Reading> readingsPerUser = readings.stream()
                        .filter(reading -> reading.getRefNumber().equals(referentNumber)).toList();
                usersList.add(
                        new User(referentNumber, name, new PriceList(Integer.parseInt(priceListNumber), priceList),
                                readingsPerUser, currency));
            }
            br.close();
        } catch (RuntimeException | IOException ex) {
            throw new RuntimeException(ex);
        }
        return usersList;
    }
}


