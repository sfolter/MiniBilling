package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CSVUserReaderTest {

        @Test
        public void readUser() {
            final String path = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\src\\test\\resources\\sample1\\input\\";

            final CSVUserReader csvUserReader = new CSVUserReader(path);
            final Map<String, User> userMap = csvUserReader.read();
            final Map<String, User> checkUser = new HashMap<>();
            checkUser.put("1", new User("Tosho", "1", 1, Collections.emptyList()));
            checkUser.put("2", new User("Mitko", "2", 2, Collections.emptyList()));
            checkUser.put("3", new User("Gosho", "3", 3, Collections.emptyList()));

            Assertions.assertEquals(checkUser.size(), userMap.size(), "User map size doesn't match");
        }
    }

