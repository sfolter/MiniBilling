package com.github.methodia.minibilling;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
       // String resourceDirector = scanner.nextLine();
      //  String dateToReporting = scanner.nextLine();

//        DateTimeFormatter formatterBorderTime = new DateTimeFormatterBuilder()
//                .appendPattern("yy-MM")
//                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
//                .toFormatter();
//        LocalDate borderTime = LocalDate.parse(dateToReporting, formatterBorderTime);

        //directories
        String resourceDirectory = "C:\\Users\\user\\IdeaProjects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\";
        String directoryClients = resourceDirectory + "users.csv";
        String directoryReport = resourceDirectory + "readings.csv";

        Bill bill = new Bill();
//
//        ArrayList<Client> clients = userReader.readClientsToList(directoryClients);
//        Map<String, List<Report>> readings = reportReader.readReportForGasToMap(directoryReport);
//
//        String reportTime = "";
//
//        for (Client client : clients) {
//            //class connecting
//            List<Report> readingsForUser = readings.get(client.getReferenseNumber());
//            Report firstReport = readingsForUser.get(0);
//            Report lastReport = readingsForUser.get(readingsForUser.size() - 1);
//
//            String pricesReadingPath = resourceDirectory + "prices-" + client.getNumberOfPriceList() + ".csv";
//            Map<String, List<Prices>> prices = pricesReader.readPricesToMap(pricesReadingPath);
//            List<Prices> priceForUser = prices.get(firstReport.getProduct());
//
//            LocalDate first = firstReport.getData().toLocalDate();
//            LocalDate last = lastReport.getData().toLocalDate();
//            if (first.isBefore(borderTime)) {
//                LocalDate start = prices.get("gas").get(0).getStart();
//                LocalDate end = prices.get("gas").get(0).getEnd();
//                int compareFirst = first.compareTo(start);
//                int compareLast = last.compareTo(end);
//                if ((first.compareTo(start)>=0 && (last.compareTo(end))<=0)) {
//
//                    Line line = new Line();
//                    line.index = client.getNumberOfPriceList();
//                    line.quantity = lastReport.getValue() - firstReport.getValue();
//                    line.lineStart = String.valueOf(firstReport.getData());//
//                    line.lineEnd = String.valueOf(lastReport.getData());//
//                    line.product = readings.get(client.getReferenseNumber()).get(0).getProduct();
//                    line.price = prices.get("gas").get(0).getPrice();
//                    line.priceList = client.getNumberOfPriceList();
//                    line.amount = line.quantity * prices.get("gas").get(0).getPrice(); //
//
//                    bill.lines.add(line);
//
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
//                    bill.setDocumentDate(ZonedDateTime.now().format(formatter));
//                    bill.documentNumber = Bill.getDocumentNumber();
//                    bill.consumer = client.getName();
//                    bill.reference = client.getReferenseNumber();
//                    double totalAmountCounter = 0;
//                    for (int j = 0; j < bill.lines.size(); j++) {
//                        totalAmountCounter += line.amount;
//                    }
//                    bill.totalAmount = totalAmountCounter;
//                }
//            }
//            reportTime = String.valueOf(lastReport.getData());
//
//            //---------------------------------------
//            Gson save = new Gson();
//            String json = save.toJson(bill);
//            try {
//                Date jud = new SimpleDateFormat("yy-MM-dd").parse(reportTime);
//                String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
//                String[] splitDate = month.split("\\s+");
//                String monthInCyrilic = splitDate[1];
//                int ReadingForUserDateYear = lastReport.getData().getYear();
//                int outputOfTheYear = ReadingForUserDateYear % 100;
//
//                String folderPath = resourceDirectory + client.getName() + "-" + client.getReferenseNumber();
//                String jsonFilePath = folderPath + "\\" + bill.documentNumber + "-" + monthInCyrilic + "-" + outputOfTheYear + ".json";

        //make folder
//                File theDir = new File(folderPath);
//                theDir.mkdirs();
//
//                //make json
//                FileWriter myWriter = new FileWriter(jsonFilePath);
//                myWriter.write(json);
//                myWriter.close();
//
//            } catch (IOException | ParseException e) {
//                e.printStackTrace();
//            }
//            bill = new Bill();
//        }
////       List<Price> price =pricesReader.read("C:\\Users\\user\\IdeaProjects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\prices-1.csv");
//       List<User> users=userReader.read("C:\\Users\\user\\IdeaProjects\\MiniBilling\\MiniBilling\\src\\test\\resources\\sample1\\input\\");
//        for (User user:users)
//        {
//            System.out.println(user);
//        }
//        System.out.println(users);
        final UserReader userReader = new UserReader();
        final PriceReader pricesReader = new PriceReader();
        final ReadingReader readingReader = new ReadingReader();
        List<User> userList = userReader.read(resourceDirectory);
        Collection<Reading> readingCollection = readingReader.read(resourceDirectory);

        for (User user : userList) {

            List<Price> priceList = pricesReader.read(resourceDirectory, user.getPriceListNumber());


            MeasurementGenerator measurementGenerator = new MeasurementGenerator(user, readingCollection);
            Collection<Measurement> measurementCollection = measurementGenerator.generate();
            System.out.println(measurementCollection);
            ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurementCollection, priceList);
            List<QuantityPricePeriod> quantityPricePeriodList = proportionalMeasurementDistributor.distribute();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, measurementCollection, priceList);
            Invoice invoice = invoiceGenerator.generate();

        }
    }
}


