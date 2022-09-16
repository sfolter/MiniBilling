package com.github.methodia.minibilling.dataRead;

import com.github.methodia.minibilling.PricesFileReader;
import com.github.methodia.minibilling.ReadingsFileReader;
import com.github.methodia.minibilling.entityClasses.Price;
import com.github.methodia.minibilling.entityClasses.PriceList;
import com.github.methodia.minibilling.entityClasses.Reading;
import com.github.methodia.minibilling.entityClasses.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UserFileReader implements DataReader {

    String usersReadingPath;

    UserFileReader(String usersReadingPath) {
        this.usersReadingPath = usersReadingPath;
    }



    @Override
    public List<User> read() {
        PricesFileReader pricesFileReader = new PricesFileReader(usersReadingPath);
        Map<Integer, List<Price>> pricesMap = pricesFileReader.read();
        String line;
        List<Reading> readings = new ReadingsFileReader(usersReadingPath).read();
        List<User> usersList = new LinkedList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(usersReadingPath + "\\users.csv"));
            while ((line = br.readLine()) != null) {
                String[] client = line.split(",");
                String name = client[0];
                String referentNumber = client[1];
                String priceListNumber = client[2];
                String currency = client[3];
                List<Price> priceList = pricesMap.get(Integer.parseInt(priceListNumber));
                List<Reading> readingsPerUser = readings.stream()
                        .filter(reading -> reading.getRefNumber().equals(referentNumber)).toList();
                usersList.add(
                        new User(referentNumber, name, new PriceList(Integer.parseInt(priceListNumber), priceList),
                                readingsPerUser, currency));
            }
            br.close();
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }
}


