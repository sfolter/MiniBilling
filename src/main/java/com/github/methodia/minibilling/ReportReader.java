package com.github.methodia.minibilling;
import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ReportReader {
    public ArrayList<Report> informationForReport = new ArrayList<>();

    public ArrayList<Report> readReportToList(String directory) {
        String line = "";

        try {
            BufferedReader br =new BufferedReader (new InputStreamReader(new FileInputStream(directory), "UTF-8"));
            while ((line = br.readLine()) != null) {
                String[] report = line.split(",");
                String date = report[2];
                ZonedDateTime instant=ZonedDateTime.parse(date);
                informationForReport.add(new Report(report[0], report
                        [1], instant, Double.parseDouble(report[3])));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return informationForReport;
    }
}

