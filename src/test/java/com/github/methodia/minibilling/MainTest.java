package com.github.methodia.minibilling;

import org.junit.jupiter.api.Test;

import java.net.URL;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class MainTest {

    @Test
    void testSample1() {
        //define the args array so that you pass the path to the files from sample1/input
        final String mouth = "21-03";
        final URL inputFolder = MainTest.class.getClassLoader().getResource("sample1/input/");
        final String path = inputFolder.getPath();
        final String[] args = {mouth, path};
        Main.main(args);

        //read the output and compare with the files in sample1/expected
    }
}
