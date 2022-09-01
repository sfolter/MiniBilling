package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserTest {
    @Test
    public void Sample1() {
        final ArrayList<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 5, 7), LocalDate.of(2022, 6, 8), new BigDecimal("1.5")));
        final User user = new User("Ivan Ivanov", "1", 1, priceList);

        Assertions.assertEquals("Ivan", user.getName(), "Name didn't match.");
        Assertions.assertEquals("1", user.getRef(), "Reference didn't match.");
        Assertions.assertEquals(1, user.getPriceListNumber(), "PriceListNumber didn't match.");
        Assertions.assertEquals(priceList, user.getPrice(), "Price didn't match .");

    }
}
