package com.github.methodia.minibilling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.*;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class Main {

    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {

        Scanner scanner=new Scanner(System.in);
       try{
//        String inputPath=args[1];
//
//        String dateReportingTo=args[0];
//
//        String outputPath=args[2];
           String dateReportingTo=args[0];
           String inputPath= args[1];
           String outputPath=args[2];
        ReadingsFileReader readingsFR=new ReadingsFileReader(inputPath);
        readingsFR.read();
        UserFileReader userFR=new UserFileReader(inputPath);
        Map<String,User>mapOfUsers=userFR.read();

        for (int i = 0; i <mapOfUsers.size() ; i++) {
            int z = i + 1;
            User user = mapOfUsers.get(String.valueOf(z));
            MeasurementGenerator mmGenerator = new MeasurementGenerator(user, readingsFR.read());
            mmGenerator.generate();
            ProportionalMeasurementDistributor proportionalMmDistributor
                    = new ProportionalMeasurementDistributor(mmGenerator.generate(), user.getPrice());
            proportionalMmDistributor.distribute();
            List<QuantityPricePeriod> qppInvoiceList = proportionalMmDistributor.distribute();
            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, mmGenerator.generate(), user.getPrice());
            Invoice invoice = invoiceGenerator.generate();


//            LocalDateAdapter localDateAdapter = new LocalDateAdapter();

            //Field documentDate = Invoice.class.getDeclaredField("documentDate");
            //documentDate.setAccessible(true);
           // Object docDateObj = documentDate.get(documentDate);
            System.out.println(user.toString());

            try {
                savingFiles(outputPath,dateReportingTo,invoice,user,invoiceGenerator);
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
            ZonedDateTime gmt = localDate.atZone(ZoneId.of("GMT"));
            String formattedLD = gmt.format(formatter);
            jsonWriter.value(formattedLD);
        }

        @Override
        public LocalDateTime read( final JsonReader jsonReader ) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(),DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }
    private static void savingFiles(String outputPath, String dateReportTo,Invoice invoice, User user,InvoiceGenerator invoiceGenerator) throws ParseException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();
        String json = gson.toJson(invoice);


        Date jud = new SimpleDateFormat("yy-MM").parse(dateReportTo);
        LocalDate borderDateLD = convertingBorderDateToLocalDate(jud);
        LocalDate lastInvoiceDate = invoice.getLines().get(0).getEnd().toLocalDate();
        if (borderDateLD.isAfter(lastInvoiceDate)) {
            String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
            String[] splitDate = month.split("\\s+");
            String monthInCyrilic = splitDate[1];


            int outputOfTheYear = lastInvoiceDate.getYear() % 100;
            String folderPath = outputPath + "\\" + user.getName() + "-" + user.getRef();
            File creatingFolders = new File(folderPath);
            boolean bool2 = creatingFolders.mkdirs();

            String jsonFilePath = folderPath + "\\" + invoice.getDocNumber() + "-" + monthInCyrilic + "-" + outputOfTheYear + ".json";
            File creatingFiles = new File(jsonFilePath);
            creatingFiles.createNewFile();
            try (PrintWriter out = new PrintWriter(new FileWriter(jsonFilePath))) {
                out.write(json.toString());
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }
    }

    private static LocalDate convertingBorderDateToLocalDate(Date jud) throws ParseException {

        Instant instant = jud.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.of("Europe/Sofia"));
        return  zdt.toLocalDate();
    }


}



