package com.github.methodia.minibilling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.lang.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String inputPath = scanner.nextLine();
        String dateToReporting=scanner.nextLine();
        LocalDate borderTime = getLocalDate(dateToReporting);
        String outputPath=scanner.nextLine();


        final String readingsPath = inputPath + "\\" + "readings.csv";

        String reportTime = "";
        final UsersFileReading usersFileRead = new UsersFileReading();
        final PricesFileReader pricesFileRead = new PricesFileReader();
        final ReadingsFileReader readingsRead = new ReadingsFileReader(readingsPath);

        ArrayList<User> users = usersFileRead.parseToArrayList();
        Map<String, List<Readings>> readings = readingsRead.parseToMap();
        int i=1;
        for (User user : users) {

            OutputClass outputClass = new OutputClass();
            List<Readings> readingsForUser = readings.get(user.getReferentNumber());

            final String pricesReadingPath = inputPath + "\\" + "prices-" + user.getNumberPricingList() + ".csv";
            Map<String, List<Prices>> prices=pricesFileRead.parseToArrayList(pricesReadingPath);

            Readings firstReadingForUser = readingsForUser.get(0);
            Readings lastReadingForUser = readingsForUser.get(readingsForUser.size() - 1);
            List<Prices> pricesList= prices.get(firstReadingForUser.getProduct());

            LocalDate firstReadingDate=convertingZonedDateTimeToLocalDate(firstReadingForUser.getDate());
            LocalDate lastReadingDate=convertingZonedDateTimeToLocalDate(lastReadingForUser.getDate());
            LocalDate firstDateAfterChangePrice;
            if(lastReadingDate.isBefore(borderTime)) {

                double quantity = lastReadingForUser.getMetrics() - firstReadingForUser.getMetrics();

                Lines line = new Lines();
                int lineIndexCounter=1;
                for(Prices price:pricesList){

                    if(firstReadingDate.compareTo(price.getStartingDate())<=0 && lastReadingDate.compareTo(price.getEndDate())<=0){
                       line.index = lineIndexCounter;
                       line.quantity = quantity;
                       line.lineStart = String.valueOf(firstReadingForUser.getDate());
                       line.lineEnd = String.valueOf(lastReadingForUser.getDate());
                       line.product = firstReadingForUser.getProduct();
                       line.price = price.getPrice();
                       line.priceList = user.getNumberPricingList();
                       line.amount = quantity * price.getPrice();
                       outputClass.lines.add(line);
                        lineIndexCounter++;
                   }else if(price.getStartingDate().compareTo(firstReadingDate)>0 && price.getEndDate().compareTo(firstReadingDate)>0){
                       double beginningMetrics=lastReadingForUser.getMetrics();
                       double endMetrics=firstReadingForUser.getMetrics();
                       double metricsPerDay=endMetrics-beginningMetrics;
                       long daysBeforeChangingPrice = ChronoUnit.DAYS.between(firstReadingDate,price.getEndDate());


                       line.index = lineIndexCounter;
                       line.quantity = metricsPerDay*daysBeforeChangingPrice;
                       line.lineStart = String.valueOf(firstReadingForUser.getDate());
                       line.lineEnd = String.valueOf(price.getEndDate());
                       line.product = firstReadingForUser.getProduct();
                       line.price = price.getPrice();
                       line.priceList = user.getNumberPricingList();
                       line.amount = metricsPerDay*daysBeforeChangingPrice*price.getPrice();
                       firstDateAfterChangePrice=price.getEndDate();
                       outputClass.lines.add(line);
                        lineIndexCounter++;


                       long daysAfterChangingPrice = ChronoUnit.DAYS.between(firstDateAfterChangePrice,price.getEndDate());

                        beginningMetrics=lastReadingForUser.getMetrics();
                        endMetrics=firstReadingForUser.getMetrics();
                        metricsPerDay=endMetrics-beginningMetrics;
                       double metricsForPeriod= metricsPerDay*daysAfterChangingPrice;
                       line.index = 1;
                       line.quantity = metricsForPeriod;
                       line.lineStart = String.valueOf(firstDateAfterChangePrice);
                       line.lineEnd = String.valueOf(price.getEndDate());
                       line.product = firstReadingForUser.getProduct();
                       line.price = price.getPrice();
                       line.priceList = user.getNumberPricingList();
                       line.amount = metricsForPeriod*price.getPrice();
                       outputClass.lines.add(line);
                       lineIndexCounter++;
                   }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    outputClass.documentDate = ZonedDateTime.now().format(formatter);

                    int documentNumber = i * 10000;
                    outputClass.documentNumber = String.valueOf(documentNumber);
                    outputClass.consumer = user.getName();
                    outputClass.reference = user.getReferentNumber();
                    outputClass.totalAmount =outputClass.totalAmount+line.amount;


                }
                lineIndexCounter=1;

                reportTime = String.valueOf(lastReadingForUser.getDate());
                savingFiles(outputPath, reportTime, user, outputClass, (convertingZonedDateTimeToLocalDate(lastReadingForUser.getDate()).getYear())%100);

            }

            outputClass = new OutputClass();
            i++;
        }
    }




    private static void savingFiles(String outputPath, String reportTime, User user, OutputClass outputClass, int lastReadingForUserDateYear) throws ParseException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(outputClass);
        String folderPath = outputPath+ "\\" + user.getName() + "-" + user.getReferentNumber();
        File creatingFolders = new File(folderPath);
        boolean bool2 = creatingFolders.mkdirs();

        Date jud = new SimpleDateFormat("yy-MM").parse(reportTime);
        String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
        String[] splitDate = month.split("\\s+");
        String monthInCyrilic = splitDate[1];
        int outputOfTheYear= lastReadingForUserDateYear %100;
        String jsonFilePath = folderPath + "\\" + outputClass.documentNumber + "-" + monthInCyrilic + "-"+outputOfTheYear+".json";
        File creatingFiles = new File(jsonFilePath);
        creatingFiles.createNewFile();
        try (PrintWriter out = new PrintWriter(new FileWriter(jsonFilePath))) {
            out.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static LocalDate getLocalDate(String dateToReporting) {
        DateTimeFormatter formatterBorderTime = new DateTimeFormatterBuilder()
                .appendPattern("yy-MM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        LocalDate borderTime=LocalDate.parse(dateToReporting,formatterBorderTime);
        return borderTime;
    }
    private static LocalDate convertingZonedDateTimeToLocalDate(ZonedDateTime time){

        int day=time.getDayOfMonth();
        Month month=time.getMonth();
        int year=time.getYear();

    return LocalDate.of(year,month,day);

    }

    private static class OutputClass {
        String documentDate;
        String documentNumber;
        String consumer;
        String reference;
        Double totalAmount= Double.valueOf(0);
        List<Lines> lines = new ArrayList<>();

    }

    private static class Lines {
        private double index;
        private double quantity;
        private String lineStart;
        private String lineEnd;
        private String product;
        private Double price;
        private int priceList;
        private Double amount;
    }
}

