package com.github.methodia.minibilling;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;


public class Main {

    public static void main(String[] args) throws ParseException {
       String readingTime;
           Scanner scan = new Scanner(System.in);

           String dateForReading = scan.nextLine();
           String path = scan.nextLine();

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yy-MM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();

              LocalDate localDate = LocalDate.parse(dateForReading, formatter);

        String csvFilePrices = "src\\test\\resources\\sample1\\input\\prices-1.csv";

        ClientsReader csvUserReader = new ClientsReader();
        ReadingsReader csvReadingReader = new ReadingsReader();
        PriceReader csvPriceReader = new PriceReader();

        ArrayList<Clients> users = csvUserReader.read();

        BillingInformation billInf = new BillingInformation();
        LinesForJsonFile line = new LinesForJsonFile();

        Map<String, List<Readings>> readings = csvReadingReader.read();
        Map<String, Price> prices = csvPriceReader.read(csvFilePrices);

        int counterForDocumentNumber = 10000;
        String outputPath = "src\\test\\resources\\output";


        for (Clients c : users) {
            //folder name -> Marko Boikov Tsvetkov-1
            String folderPath = outputPath + "\\" + c.getClientName() + "-" + c.getReferenceNumber();

            // get clients ref.number and hold place for firstReading and lastReading
            List<Readings> readingsForUser = readings.get(c.getReferenceNumber());
            Readings firstReading = readings.get(c.getReferenceNumber()).get(0);
            Readings lastReading = readings.get(c.getReferenceNumber()).get(readingsForUser.size() - 1);

            prices.get("gas");

            Price price = prices.get(firstReading.getProduct()); //inf. from prices.csv
            int lastReadingForUserDateDay=lastReading.getDate().getDayOfMonth();
            Month lastReadingForUserDateMonth= Month.of(lastReading.getDate().getMonthOfYear());
            int lastReadingForUserDateYear=lastReading.getDate().getYear();
            LocalDate lastReadingForUserInLocalDate=LocalDate.of(lastReadingForUserDateYear,lastReadingForUserDateMonth,lastReadingForUserDateDay);
           if (lastReadingForUserInLocalDate.isBefore(localDate)) {

                billInf.getDocumentDate();
                billInf.documentNumber = String.valueOf(counterForDocumentNumber);
                counterForDocumentNumber++;
                billInf.consumer = c.getClientName();
                billInf.reference = c.getReferenceNumber();
                line.price = price.getPrice();
                line.quantity = lastReading.getIndication() - firstReading.getIndication();
                line.getAmount();
                billInf.totalAmount = line.getAmount();
                line.index = c.getPriceListNumber();
                line.lineStart = String.valueOf(firstReading.getDate());
                line.lineEnd = String.valueOf(lastReading.getDate());
                line.lineStart = String.valueOf(firstReading.getDate());
                line.lineEnd = String.valueOf(lastReading.getDate());
                line.product = firstReading.getProduct();
                line.priceList = c.getPriceListNumber();
                readingTime = String.valueOf(lastReading.getDate());

                billInf.lines.add(line);


                Gson gson = new GsonBuilder().setPrettyPrinting().create(); // for Json files
                String json = gson.toJson(billInf);

                Date date = new SimpleDateFormat("yy-MM-dd").parse(readingTime);
                String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(date); //bg translation
                String[] splitDate = month.split("\\s+");
                String Cyrillic = splitDate[1];
                String firstLetter = Cyrillic.substring(0, 1).toUpperCase(Locale.ROOT);// get only the month
                String nameCapitalized = firstLetter + Cyrillic.substring(1);
                String lineStart = line.lineStart;
                String yearOfIssuedInvoice = lineStart.substring(2, 4); // 2021 -> 21

                String fName = folderPath + "\\" + billInf.getDocumentNumber() + "-" + nameCapitalized + "-" + yearOfIssuedInvoice + ".json";

                createJsonFilesInClientsFolder(folderPath, json, fName);

                billInf = new BillingInformation();
                line = new LinesForJsonFile();

            }

        }
   }


    static void createJsonFilesInClientsFolder(String folderPath, String json, String fName) {
        try {
            File creatingFolders = new File(folderPath);
            File folderDirectory = new File(String.valueOf(creatingFolders));
            folderDirectory.mkdirs();
            FileWriter outputWriter = new FileWriter(fName);
            outputWriter.write(json); //write json file
            outputWriter.close();


        } catch (Exception e) {
            e.getStackTrace();


        }


    }
}


