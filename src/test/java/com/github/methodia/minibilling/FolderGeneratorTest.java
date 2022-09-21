package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FolderGeneratorTest {

    @Test
    public void generateFolder() {
        final List<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 2, 2), new BigDecimal("2")));
        final User user = new User("Gosho", "2", 2);
        final String path = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\output\\";
        final FolderGenerator folderGenerator = new FolderGenerator(user, path);
        final String folderPath = folderGenerator.folderGenerate();

        Assertions.assertEquals(path + user.getName() + "-" + user.getRef(), folderPath, "Path does not match");
    }
}
