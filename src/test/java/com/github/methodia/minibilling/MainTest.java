package com.github.methodia.minibilling;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        testSample(sampleNumber, yearMonth);
    }

    @Test
    void testSample2() throws IOException {
        final int sampleNumber = 2;
        final String yearMonth = "21-03";
        testSample(sampleNumber, yearMonth);
    }

    private void testSample(int sampleNumber, String yearMonth) throws IOException {
        final String outputDir = getOutputDir(sampleNumber);
        final String sampleInputDir = getSampleInputDir(sampleNumber);
        final String[] args = {yearMonth, sampleInputDir, outputDir};
        Main.main(args);
        final File expectedFiles = new File(getExpectedDir(sampleNumber));
        final File output = new File(outputDir);
        checkDirectories(expectedFiles, output, sampleNumber);
    }
    private void checkDirectories(File sampleDir, File outputDir, int sampleNumber) throws IOException {
        final File[] sampleInputFiles = sampleDir.listFiles();
        final File[] outputFiles = outputDir.listFiles();
        final Map<String, File> outputFilesByName = Arrays.stream(outputFiles)
                .collect(Collectors.toMap(File::getName, file -> file));
        Assertions.assertEquals(sampleInputFiles.length, outputFiles.length,
                String.format("The generated files (directories) do not match the expected  in sample%s",
                        sampleNumber));
        for (File sampleInputFile : sampleInputFiles) {
            final String sampleInputFileName = sampleInputFile.getName();
            Assertions.assertTrue(outputFilesByName.containsKey(sampleInputFileName));
            final File outputFile = outputFilesByName.get(sampleInputFileName);
            if (sampleInputFile.isDirectory()) {
                checkDirectories(sampleInputFile, outputFile, sampleNumber);
            } else {
                assertInvoiceJsonFilesEqual(sampleInputFile, outputFile, sampleNumber);
            }
        }
    }

    private void assertInvoiceJsonFilesEqual(File sampleJson, File outputJson, int sampleNumber) throws IOException {
        final String sampleJsonStr = Files.readString(sampleJson.toPath());
        final String outputJsonStr = Files.readString(outputJson.toPath());
        final JSONObject sampleJsonObject = new JSONObject(sampleJsonStr);
        final JSONObject outputJsonObject = new JSONObject(outputJsonStr);
        final Set<String> invoiceJsonProps = sampleJsonObject.keySet();
        for (String invoiceJsonProp : invoiceJsonProps) {
            assertPropertiesEqual(sampleJsonObject, outputJsonObject, invoiceJsonProp, sampleNumber);
        }
    }

    private void assertPropertiesEqual(JSONObject sampleJsonObject, JSONObject outputJsonObject, String key,
                                       int sampleNumber) {
        final Object sampleInputProp = sampleJsonObject.get(key);
        final Object outputProp = outputJsonObject.get(key);

        if (sampleInputProp instanceof JSONArray) {
            final JSONArray sampleInputPropArray = (JSONArray) sampleInputProp;
            Assertions.assertTrue(outputProp instanceof JSONArray);
            for (int i = 0; i < sampleInputPropArray.length(); i++) {
                Object sampleInputPropArrayElement = sampleInputPropArray.get(i);
                Object outputPropArrayElement = ((JSONArray) outputProp).get(i);
                Assertions.assertTrue(sampleInputPropArrayElement instanceof JSONObject);
                Assertions.assertTrue(outputPropArrayElement instanceof JSONObject);
                ((JSONObject) sampleInputPropArrayElement).keys().forEachRemaining((innerKey) -> assertPropertiesEqual(
                        (JSONObject) sampleInputPropArrayElement, (JSONObject) outputPropArrayElement, innerKey,
                        sampleNumber));

            }
        } else {
            Assertions.assertEquals(sampleInputProp, outputProp, String.format("Sample %s fails.", sampleNumber));
        }
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

    private String getExpectedDir(int sampleNumber) {
        final URL inputDir = MainTest.class.getClassLoader()
                .getResource(String.format("sample%s/expected/", sampleNumber));
        return inputDir.getPath();
    }


}
