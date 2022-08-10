package com.github.methodia.minibilling;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ReadingsTodor implements FileReaderTodor {
    UsersTodor usersTodor = new UsersTodor();
    static ArrayList<String> referentialNumberReadings = new ArrayList<>();
    static ArrayList<String> referentialNumb = new ArrayList<>();
    static ArrayList<String> product = new ArrayList<>();
    private ArrayList<ZonedDateTime> startDateParsed = new ArrayList<>();
    private ArrayList<ZonedDateTime> endDateParsed = new ArrayList<>();
    static ArrayList<String> dateString = new ArrayList<>();

    public static ArrayList<String> getEndDateString() {
        return endDateString;
    }

    static ArrayList<String> startDateString = new ArrayList<>();
    static ArrayList<String> endDateString = new ArrayList<>();
    static ArrayList<Integer> pokazanie = new ArrayList<>();
    static ArrayList<Integer> pokazanieStart = new ArrayList<>();
    static ArrayList<Integer> pokazanieEnd = new ArrayList<>();
    private ArrayList<ZonedDateTime> parsedData = new ArrayList<ZonedDateTime>();
    static ArrayList<String[]> readingsList = new ArrayList<>();
    ArrayList<Float> quantityList = new ArrayList<>();

    public ArrayList<String> getProduct() {
        return product;
    }


    ArrayList<String> refList = usersTodor.getUserRefList();

    public ArrayList<ZonedDateTime> getStartDateParsed() {
        return startDateParsed;
    }

    public ArrayList<ZonedDateTime> getEndDateParsed() {
        return endDateParsed;
    }

    public ArrayList<Float> getQuantity() {


        int j = 0;
        while (j < refList.size()) {
            for (int i = 0; i < referentialNumb.size(); i++) {
                if (refList.get(j).equals(referentialNumb.get(i))) {

                    float quantity = pokazanieEnd.get(i) - pokazanieStart.get(i);
                    quantityList.add(quantity);
                    startDateParsed.add(dateParsing(startDateString).get(i));
                    endDateParsed.add(dateParsing(endDateString).get(i));
                    j++;


                }
            }

        }
        return quantityList;
    }

    @Override
    public void reader(String path) {
        String[] readingsLineInArray;


        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {

            while ((readingsLineInArray = reader.readNext()) != null) {
                readingsList.add(readingsLineInArray);

            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < readingsList.size(); i++) {
            String[] strings = readingsList.get(i);
            referentialNumberReadings.add(strings[0]);
            product.add(strings[1]);
            dateString.add(strings[2]);
            pokazanie.add(Integer.parseInt(strings[3]));
        }
        for (int i = 0; i < readingsList.size(); i++) {
            for (int j = i + 1; j < readingsList.size(); j++) {

                if (referentialNumberReadings.get(i).equals(referentialNumberReadings.get(j))) {
                    pokazanieStart.add(Math.min(pokazanie.get(i), pokazanie.get(j)));
                    pokazanieEnd.add(Math.max(pokazanie.get(i), pokazanie.get(j)));
                    startDateString.add(dateString.get(i));
                    endDateString.add(dateString.get(j));
                    referentialNumb.add(referentialNumberReadings.get(i));
                }

            }


        }


    }

    public ArrayList<ZonedDateTime> dateParsing(ArrayList<String> dateString) {
        for (int i = 0; i < dateString.size(); i++) {
            ZonedDateTime instant = ZonedDateTime.parse(dateString.get(i));
            parsedData.add(instant);
        }
        return parsedData;
    }
}




