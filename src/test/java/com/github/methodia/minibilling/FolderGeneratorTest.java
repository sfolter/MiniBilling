package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Todor Todorov
 * @Date 30.08.2022
 * Methodia Inc.
 */
public class FolderGeneratorTest {
    @Test
    public void generateFolder(){
        final List<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 2, 2), new BigDecimal("2")));
        final User user = new User("Gosho", "2", priceList, 2);
        final String path = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\output\\";
        final FolderGenerator folderGenerator = new FolderGenerator();
        final String folderPath = folderGenerator.generate(user, path);

        Assertions.assertEquals(path+user.name()+"-"+user.ref(), folderPath, "Path did not match");
    }

}
