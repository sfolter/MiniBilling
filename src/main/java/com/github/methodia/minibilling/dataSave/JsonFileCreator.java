package com.github.methodia.minibilling.dataSave;

import com.github.methodia.minibilling.entityClasses.Invoice;
import com.github.methodia.minibilling.entityClasses.InvoiceLine;
import com.github.methodia.minibilling.entityClasses.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class JsonFileCreator implements SaveData {

    Invoice invoice;
    private final User user;
    private final String outputPath;

    public JsonFileCreator(final Invoice invoice, final User user, final String outputPath) {
        this.invoice = invoice;
        this.user = user;
        this.outputPath = outputPath;
    }

    @Override
    public void save() {
        //Parsing Invoice class into Json format using GSON library
        final Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new JsonFileCreator.LocalDateAdapter()).create();
        final String json = gson.toJson(invoice);

        //Sorting InvoiceLine list as we would like to get the last invoice end date
        final List<InvoiceLine> invoiceLinesList = invoice.getLines().stream()
                .sorted(Comparator.comparing(InvoiceLine::getEnd).reversed()).toList();
        if (invoiceLinesList.isEmpty()) {
            System.out.println("There is no invoices");
        } else {
            final LocalDate lastInvoiceDate = invoiceLinesList.get(0).getEnd().toLocalDate();
            final String monthToBulgarian = getMonthOfLastInvoiceToBulgarian(lastInvoiceDate);
            final int outputOfTheYear = lastInvoiceDate.getYear() % 100;

            //Creating folder in the following path
            final String folderPath = outputPath + "\\" + user.getName() + "-" + user.getRefNumber();
            createFolder(folderPath);


            final String jsonFilePath =
                    folderPath + "\\" + invoice.getDocNumber() + "-" + monthToBulgarian + "-" + outputOfTheYear
                            + ".json";
            try {
                creatingJsonFIle(json, jsonFilePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private static final class LocalDateAdapter extends TypeAdapter<LocalDateTime> {

        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
            final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            final ZonedDateTime gmt = localDate.atZone(ZoneId.of("Z"));
            final String formattedLD = gmt.format(formatter);
            jsonWriter.value(formattedLD);
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    private static String getMonthOfLastInvoiceToBulgarian(final LocalDate lastInvoiceDate) {
        final String lastInvoiceDateInBG = lastInvoiceDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("Bg"));
        return lastInvoiceDateInBG.substring(0, 1).toUpperCase() + lastInvoiceDateInBG.substring(1);
    }

    /**
     * Checking if JsonFile by the exists with the format documentNumber-Month(translated to bulgarian language),
     * and the last two numbers of the year on Last reporting date, in case not,
     * the following algorithm will create the JsonFiles in the format below.
     */
    private static void creatingJsonFIle(final String json, final String jsonFilePath) throws IOException {
        final File creatingFiles = new File(jsonFilePath);
        final boolean newFile = creatingFiles.createNewFile();
        try (final PrintWriter out = new PrintWriter(new FileWriter(jsonFilePath))) {
            out.write(json);
        } catch (DateTimeParseException e) {
            throw new IOException(e);
        }
    }

    /**
     * Checking if folders by the following format - username-referent number, in case the folders doesn't exist
     * the following algorithm will create the folders in the format below.
     */
    private static void createFolder(final String folderPath) {
        final File creatingFolders = new File(folderPath);
        final boolean mkdirs = creatingFolders.mkdirs();

    }
}
