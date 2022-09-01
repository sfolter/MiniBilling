package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FolderGeneratorTest {

    @Test
    public void Sample1() {
        final List<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 3, 4), LocalDate.of(2022, 5, 6), new BigDecimal("2")));
        final User user = new User("Ivan Ivanov", "2", 2, priceList);
        final String path = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\output\\";
        final FolderGenerator folderGenerator = new FolderGenerator(user, path);
        final String folderPath = folderGenerator.folderGenerate();

        Assertions.assertEquals(path + user.getName() + "-" + user.getRef(), folderPath, "Path did not match");
    }
}
