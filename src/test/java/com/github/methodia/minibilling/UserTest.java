package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserTest {

    @Test
    public void getUser() {
        final ArrayList<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 5, 7), LocalDate.of(2022, 6, 8), new BigDecimal("1.5")));
        final User user = new User("Ivan", "1", 1, priceList);

        Assertions.assertEquals("Ivan", user.getName(), "Name did not match the expected one.");
        Assertions.assertEquals("1", user.getRef(), "Ref did not match the expected one.");
        Assertions.assertEquals(1, user.getPriceListNumber(), "PriceListNumer did not match the expected one.");
        Assertions.assertEquals(priceList, user.getPrice(), "Price did not match the expected one.");

    }
}
