package com.example.SpringBatchExample;

import com.example.SpringBatchExample.models.Price;
import com.example.SpringBatchExample.models.PriceList;
import com.example.SpringBatchExample.models.Reading;
import com.example.SpringBatchExample.models.User;
import com.example.SpringBatchExample.repositories.ReadingRepository;
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
public class ReadingRepositoryTest {

    private final List<Reading> readingsList = new ArrayList<Reading>();
    @Mock
    private ReadingRepository readingRepository;

    @Test
    public void itFindsReadingsByRefNumber() {
        final Reading reading = getReadings();
        readingsList.add(reading);
        Mockito.when(readingRepository.findByRefNumber(reading.getRefNumber())).thenReturn(readingsList);
        final List<Reading> foundedReadings = readingRepository.findByRefNumber(reading.getRefNumber());
        Assertions.assertEquals(foundedReadings.get(0).getRefNumber(), readingsList.get(0).getRefNumber());
    }

    private static Reading getReadings() {
        final List<Price> prices = new ArrayList<>();
        final Price price = new Price("gas", ZonedDateTime.of(2021, 1, 5, 0, 0, 0, 0, ZoneId.of("Z")),
                ZonedDateTime.of(2021, 2, 15, 0, 0, 0, 0, ZoneId.of("Z").normalized()), new BigDecimal("200"));

        prices.add(price);

        final PriceList priceList = new PriceList(1, prices);

        User user = new User("Marko", 2, 1, priceList);
        return new Reading(ZonedDateTime.of(2021, 1, 5, 0, 0, 0, 0, ZoneId.of("Z")), 1, new BigDecimal("1.8"), user);

    }
}

