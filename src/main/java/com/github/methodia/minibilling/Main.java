package com.github.methodia.minibilling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class Main {

    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {

       try{
           String dateReportingTo=args[0];
           LocalDateTime dateReportingToLDT=convertingBorderTimeIntoLDT(dateReportingTo);
           String inputPath= args[1];
           String outputPath=args[2];
        ReadingsFileReader readingsFR=new ReadingsFileReader(inputPath);
        Collection<Reading> readingCollection=readingsFR.read();
        UserFileReader userFR=new UserFileReader(inputPath);
        Map<String,User>mapOfUsers=userFR.read();

        for (int i = 0; i <mapOfUsers.size() ; i++) {
            int z = i + 1;
            User user = mapOfUsers.get(String.valueOf(z));
            //Collection<Reading> filteredReadings= readingCollection.stream().filter(reading -> reading.getUser().equals(user)).collect(Collectors.toCollection(TreeSet::new));
            //reading -> reading.getUser().equals(user)
            MeasurementGenerator mmGenerator = new MeasurementGenerator(user,readingCollection);
           Collection<Measurement> mmCollector= mmGenerator.generate();
            ProportionalMeasurementDistributor proportionalMmDistributor
                    = new ProportionalMeasurementDistributor(mmCollector, user.getPrice());

            List<QuantityPricePeriod> qppInvoiceList = proportionalMmDistributor.distribute();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user,mmCollector, user.getPrice());
            Invoice invoice = invoiceGenerator.generate(dateReportingToLDT);


//            LocalDateAdapter localDateAdapter = new LocalDateAdapter();

            //Field documentDate = Invoice.class.getDeclaredField("documentDate");
            //documentDate.setAccessible(true);
           // Object docDateObj = documentDate.get(documentDate);
            System.out.println(user.toString());

            try {
                savingFiles(outputPath,dateReportingToLDT,invoice,user,invoiceGenerator);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }




        }catch (Exception e){
           System.out.println(e);
       }
    }
    private static final class LocalDateAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public void write( final JsonWriter jsonWriter, final LocalDateTime localDate ) throws IOException {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            ZonedDateTime gmt = localDate.atZone(ZoneId.of("Z"));
            String formattedLD = gmt.format(formatter);
            jsonWriter.value(formattedLD);
        }

        @Override
        public LocalDateTime read( final JsonReader jsonReader ) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(),DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }
    private static void savingFiles(String outputPath, LocalDateTime dateReportToLDT,Invoice invoice, User user,InvoiceGenerator invoiceGenerator) throws ParseException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();
        String json = gson.toJson(invoice);

        List <InvoiceLine> invoiceLinesList= invoice.getLines().stream().sorted(Comparator.comparing(InvoiceLine::getEnd).reversed()).toList();
        LocalDate lastInvoiceDate = invoiceLinesList.get(0).getEnd().toLocalDate();
              String monthToBulgarian= getMonthOfLastInvoiceToBulgarian(lastInvoiceDate);

            int outputOfTheYear = lastInvoiceDate.getYear() % 100;
            String folderPath = outputPath + "\\" + user.getName() + "-" + user.getRef();
            File creatingFolders = new File(folderPath);
            boolean bool2 = creatingFolders.mkdirs();

            String jsonFilePath = folderPath + "\\" + invoice.getDocNumber() + "-" + monthToBulgarian + "-" + outputOfTheYear + ".json";
            File creatingFiles = new File(jsonFilePath);
            creatingFiles.createNewFile();
            try (PrintWriter out = new PrintWriter(new FileWriter(jsonFilePath))) {
                out.write(json.toString());
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
    }
    private static LocalDate convertingBorderDateToLocalDate(Date jud) throws ParseException {

        Instant instant = jud.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.of("Europe/Sofia"));
        return  zdt.toLocalDate();
    }
        private static LocalDateTime convertingBorderTimeIntoLDT(String borderDateString){
            final YearMonth yearMonth=YearMonth.parse(borderDateString,DateTimeFormatter.ofPattern("yy-MM"));
            DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            ZonedDateTime borderTimeZDT=yearMonth.atEndOfMonth().atTime(23,59,59).atZone(ZoneId.of("Z"));
            return LocalDateTime.parse(String.valueOf(borderTimeZDT),formatter);
        }
        private static String getMonthOfLastInvoiceToBulgarian( LocalDate lastInvoiceDate){

             String lastInvoiceDateInBG= lastInvoiceDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("Bg"));

             return lastInvoiceDateInBG.substring(0,1).toUpperCase()+lastInvoiceDateInBG.substring(1);
        }
}



