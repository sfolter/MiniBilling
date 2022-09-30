package com.example.SpringBatchExample;

import com.example.SpringBatchExample.models.Price;
import com.example.SpringBatchExample.models.PriceList;
import com.example.SpringBatchExample.models.User;
import com.example.SpringBatchExample.repositories.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest

public class UserRepositoryTest {

    private final List<User> userList = new ArrayList<>();
    @Mock
    private UserRepository userRepository;



    @Test
    public void itFindsAllUsers() {
        final User user = getUser();
        userList.add(user);
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        final List<User> foundedUsers = userRepository.findAll();
        Assertions.assertEquals(1, foundedUsers.size());
    }

    private static User getUser() {
        final List<Price> prices = new ArrayList<>();
        final Price price = new Price("gas", ZonedDateTime.of(2021, 1, 5, 0, 0, 0, 0, ZoneId.of("Z")),
                ZonedDateTime.of(2021, 2, 15, 0, 0, 0, 0, ZoneId.of("Z").normalized()), new BigDecimal("200"));

        prices.add(price);

        final PriceList priceList = new PriceList(1, prices);

        return new User("Marko", 2, 1, priceList);
    }



}
