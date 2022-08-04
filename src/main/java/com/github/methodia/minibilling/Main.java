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
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        //WELCOME TO MINI BILLING!
        //check args and print an error to the user if there are no arguments
        Scanner scanner=new Scanner(System.in);
        String inputPath=scanner.nextLine();
        PriceReader pr = new PriceReader();
        pr.read();
        UsersReader ur = new UsersReaders(inputPath);
        ur.read();
        ReadingsReader rr = new ReadingsReader(inputPath);

        Map<String, User> ll = ur.read();
        for (int i = 0; i < ll.size(); i++) {

            int k = i + 1;

            User user = ll.get(String.valueOf(k));

            MeasurementGenerator mmGenerator = new MeasurementGenerator(user, rr.read());
            mmGenerator.generate();

            ProportionalMeasurementDistributor proportionalMmDistributor
                    = new ProportionalMeasurementDistributor(mmGenerator.generate(), user.getPrice());
            proportionalMmDistributor.distribute();

              List<QuantityPricePeriod> qppInvoiceList=proportionalMmDistributor.distribute();

            InvoiceGenerator invoiceGenerator = new InvoiceGenerator(user, mmGenerator.generate(), user.getPrice());
            Invoice invoice = invoiceGenerator.generate();

//            System.out.println(invoice);
//            System.out.println();

            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();
            String json = gson.toJson(invoice);
            String folderPath = "src\\test\\resources\\sample1\\input" + "\\" + user.getName() + "-" + user.getRef();
            File creatingFolders = new File(folderPath);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            Date date = new SimpleDateFormat("yy-MM-dd").parse(qppInvoiceList.get(0).getEnd().format(formatter));
            String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(date);
            String[] splitDate = month.split("\\s+");
            String Cyrillic = splitDate[1];
            String fName = folderPath + "\\" + invoice.getDocumentNumber() + "-" + Cyrillic + "-" + user.getRef() + ".json";
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
            jsonWriter.value(localDate.toString());
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString());
        }

    }
}