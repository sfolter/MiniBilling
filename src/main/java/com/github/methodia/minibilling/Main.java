package com.github.methodia.minibilling;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

import static com.github.methodia.minibilling.Invoice.getDocumentNumber;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        //WELCOME TO MINI BILLING!
        //check args and print an error to the user if there are no arguments


        String inputDIR=args[1];
        String dateReporting=args[0];
        final YearMonth yearMonth = YearMonth.parse(dateReporting,DateTimeFormatter.ofPattern("yy-MM"));
        DateTimeFormatter fm =  DateTimeFormatter.ISO_ZONED_DATE_TIME;
        ZonedDateTime btz = yearMonth.atEndOfMonth().atTime(23,59,59).atZone(ZoneId.of("Z"));
        LocalDateTime parseReportingDate = LocalDateTime.parse(String.valueOf(btz), fm);
        String outputDIR =args[2];
        UsersReader ur = new UsersReaders(inputDIR);

        ReadingsReader rr = new ReadingsReader(inputDIR);
        List<Reading> readingList = rr.read();

        Map<String, User> ll = ur.read();
        for (int i = 0; i < ll.size(); i++) {

            int k = i + 1;

            User user = ll.get(String.valueOf(k));


            MeasurementGenerator mmGenerator = new MeasurementGenerator(user, readingList);
            Collection<Measurement> mmCollector = mmGenerator.generate();

            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, mmCollector, user.getPrice());
            Invoice invoice = invoiceGenerator.generate(parseReportingDate);



            Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();
            String json = gson.toJson(invoice);
            String folderPath = outputDIR + "\\" + user.getName() + "-" + user.getRef();
            File creatingFolders = new File(folderPath);


            String month = parseReportingDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("bg"));
            String Month = month.substring(0,1).toUpperCase()+ month.substring(1);


            String fName = folderPath + "\\" + invoice.getDocumentNumber() + "-" + Month+ "-" + user.getRef() + ".json";
            try {
                File directory = new File(String.valueOf(creatingFolders));
                directory.mkdirs();
                FileWriter myWriter = new FileWriter(fName);
                myWriter.write(json);
                myWriter.close();


            } catch (Exception e) {
                e.getStackTrace();


            }

        }


    }

    private static final class LocalDateAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            ZonedDateTime gmt = localDate.atZone(ZoneId.of("Z"));
            String formattedLD = gmt.format(formatter);
            jsonWriter.value(formattedLD);
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

    }
}