package com.github.methodia.minibilling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Miroslav Kovachev
 * 21.07.2022
 * Methodia Inc.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner=new Scanner(System.in);
        String inputPath=scanner.nextLine();
        String outputPath=scanner.nextLine();
        ReadingsFileReader readingsFR=new ReadingsFileReader(inputPath);
        readingsFR.read();
        UserFileReader userFR=new UserFileReader(inputPath);
        Map<String,User>mapOfUsers=userFR.read();

        for (int i = 0; i <mapOfUsers.size() ; i++) {
            int z=i+1;
        User user= mapOfUsers.get(String.valueOf(z));
            MeasurementGenerator mmGenerator=new MeasurementGenerator(user ,readingsFR.read());
            mmGenerator.generate();
            ProportionalMeasurementDistributor proportionalMmDistributor
                    =new ProportionalMeasurementDistributor(mmGenerator.generate(),user.getPrice());
           proportionalMmDistributor.distribute();
           List<QuantityPricePeriod> qppInvoiceList=proportionalMmDistributor.distribute();
           InvoiceGenerator invoiceGenerator=new InvoiceGenerator(user,mmGenerator.generate(),user.getPrice());
        Invoice invoice=invoiceGenerator.generate();

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String json;
//            json = gson.toJson(invoice);
            System.out.println(invoice);
//            try {
//                savingFiles(outputPath,user,invoiceGenerator);
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }


        }
    }

    private static void savingFiles(String outputPath, User user,InvoiceGenerator invoiceGenerator) throws ParseException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Invoice invoice=invoiceGenerator.generate();
        String json = gson.toJson(invoice);
        List<Invoice> invoices= Collections.singletonList(invoiceGenerator.generate());
        String folderPath = outputPath+ "\\" + user.getName() + "-" + user.getRef();
        File creatingFolders = new File(folderPath);
        boolean bool2 = creatingFolders.mkdirs();

        Date jud = new SimpleDateFormat("yy-MM").parse(String.valueOf(invoice.getLines().get(0).getEnd()));
        String month = DateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale("bg")).format(jud);
        String[] splitDate = month.split("\\s+");
        String monthInCyrilic = splitDate[1];
      int outputOfTheYear= Integer.parseInt(month) %100;
        String jsonFilePath = folderPath + "\\" + invoice.getDocumentDate()+ "-" + monthInCyrilic + "-"+outputOfTheYear+".json";
        File creatingFiles = new File(jsonFilePath);
        creatingFiles.createNewFile();
        try (PrintWriter out = new PrintWriter(new FileWriter(jsonFilePath))) {
            out.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



