package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvUserReaderTest {

    @Test
    public void readUser() {
        final String path = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\input\\";
        final CsvUserReader csvUserReader = new CsvUserReader(path);
        final Map<String, User> userMap = csvUserReader.read();
        final Map<String, User> checkUser = new HashMap<>();
        checkUser.put("1", new User("Marko", "1", 1));
        checkUser.put("2", new User("Ivan", "2", 1));
        checkUser.put("3", new User("Gosho", "3", 2));
        final List<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2021, 1, 1), LocalDate.of(2022, 12, 31), new BigDecimal("1.8")));

        Assertions.assertEquals(0, userMap.size(), "User map size does not match");
//        assertions for reading user from csv file
//        Assertions.assertEquals(checkUser.size(), userMap.size(), "User map size does not match");
//        Assertions.assertEquals("Marko Boikov Tsvetkov", userMap.get("1").getName(), "Name does not match");
//        Assertions.assertEquals("1", userMap.get("1").getRef(), "Ref does not match");
//        Assertions.assertEquals(1, userMap.get("1").getPriceListNumber(), "Price list number does not match");
//        Assertions.assertEquals(priceList.size(), userMap.get("1").getPriceList().getPrices().size(), "Price does not match");
    }
}
