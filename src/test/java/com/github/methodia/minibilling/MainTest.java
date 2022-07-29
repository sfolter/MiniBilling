package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
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
        final String month = "21-03";

        final URL inputFolder = MainTest.class.getClassLoader().getResource("sample1/input/");
        final String path = inputFolder.getPath();
        final String[] args = {month, path};
        Main.main(args);
//        String expectedInvoiceFileName = "";
//        String actualInvoiceFileName = "";
//        Assertions.assertEquals(expectedInvoiceFileName, actualInvoiceFileName,
//                "The expected invoice name does not match the actual name!");
//        //read the output and compare with the files in sample1/expected
    }
}
