package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class MainTest {

    @Test
    void testSample1() throws IOException {
        final int sampleNumber = 1;
        final String yearMonth = "21-03";
        //define the args array so that you pass the path to the files from sample1/input
        final String outputDir = getOutputDir(sampleNumber);
        final String sampleInputDir = getSampleInputDir(sampleNumber);
        final String[] args = {yearMonth, sampleInputDir, outputDir};
        Main.main(args);
        final File sampleInput = new File(sampleInputDir);
        final File output = new File(outputDir);
        final File[] sampleInputFiles = sampleInput.listFiles();
        final File[] outputFiles = output.listFiles();
        Assertions.assertEquals(sampleInputFiles.length, outputFiles.length,
                String.format("The generated files (directories) do not match the expected  in sample%s", sampleNumber));
        //read the output and compare with the files in sample1/expected
    }

    private String getSampleInputDir(int sampleNumber) {
        final URL inputDir = MainTest.class.getClassLoader()
                .getResource(String.format("sample%s/input/", sampleNumber));
        return inputDir.getPath();
    }

    private String getOutputDir(int sampleNumber) {
        final String programDir = System.getProperty("user.dir");
        final String outputDir = String.format("%s/output/sample%s/", programDir, sampleNumber);
        return outputDir;
    }
}
