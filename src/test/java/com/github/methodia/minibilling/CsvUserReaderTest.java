package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CsvUserReaderTest {

    @Test
    public void readUser() {
        final String path = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\input\\";
        final CsvUserReader csvUserReader = new CsvUserReader(path);
        final Map<String, User> userMap = csvUserReader.read();
        final Map<String, User> checkUser = new HashMap<>();
        checkUser.put("1", new User("Marko", "1", 1, Collections.emptyList()));
        checkUser.put("2", new User("Ivan", "2", 1, Collections.emptyList()));
        checkUser.put("3", new User("Gosho", "3", 2, Collections.emptyList()));

        Assertions.assertEquals(checkUser.size(), userMap.size(), "User map size does not match");
    }
}
