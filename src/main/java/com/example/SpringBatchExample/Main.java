package com.example.SpringBatchExample;


import com.example.SpringBatchExample.generators.InvoiceController;
import com.example.SpringBatchExample.generators.InvoiceGenerator;
import com.example.SpringBatchExample.generators.InvoiceService;
import com.example.SpringBatchExample.generators.MeasurementGenerator;
import com.example.SpringBatchExample.models.User;
import com.example.SpringBatchExample.repositories.InvoiceRepository;
import com.example.SpringBatchExample.repositories.ReadingRepository;
import com.example.SpringBatchExample.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@SpringBootApplication
@ComponentScan(basePackageClasses= InvoiceController.class)
public class Main {

//   InvoiceRepository invoiceRepository;
////
//static InvoiceService invoiceService;
//@Autowired
//    public Main(final InvoiceService invoiceService,  InvoiceRepository invoiceRepository) {
//        this.invoiceService = invoiceService;
//        this.invoiceRepository = invoiceRepository;
//    }

    public static void main(final String[] args) {

        final ConfigurableApplicationContext applicationContext = SpringApplication.run(
                Main.class, args);


        final String dateReporting = "22-03";
        final LocalDateTime parseReportingDate = LocalDateTime.from(getReportingDate(dateReporting));
        final String outputDir = "C:\\Users\\user\\Desktop\\MiniBilling-Postgres\\MiniBilling\\output";

        final UserRepository user = applicationContext.getBean(UserRepository.class);
        final List<User> findAllUsers = user.findAll();
        final ReadingRepository reading = applicationContext.getBean(ReadingRepository.class);

        final Converter currencyConverter = new CurrencyConverter();

        final String currencyFrom = "BGN";
        final String currencyTo = "EUR";
        MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();

        final InvoiceRepository invoiceRepository = applicationContext.getBean(InvoiceRepository.class);
        findAllUsers.stream().map(u -> measurementGenerator.generate(u, reading.findByRefNumber(u.getRefNumber())))
                .map(measurements -> invoiceGenerator.generate(parseReportingDate, measurements, currencyFrom,
                        currencyTo, currencyConverter))
                .forEach(invoice -> {
                    invoiceRepository.saveAndFlush(invoice);
                    FileMaker.save(invoice, outputDir, parseReportingDate);
                });

        InvoiceService invoiceService = new InvoiceService();
      //  List<Invoice> invoiceList = invoiceService.listAll();


//        for (Invoice i : invoiceList) {
//            System.out.println(i);
//        }

    }

    //    @Bean
    //    public InvoiceGenerator invoiceGenerator2(InvoiceLineGenerator invoiceLineGenerator, InvoiceVatGenerator vatGenerator, InvoiceTaxGenerator taxGenerator) {
    //
    //        return new InvoiceGenerator(invoiceLineGenerator, vatGenerator, taxGenerator);
    //    }

    private static ZonedDateTime getReportingDate(final String dateReporting) {
        final YearMonth yearMonth = YearMonth.parse(dateReporting, DateTimeFormatter.ofPattern("yy-MM"));
        final ZonedDateTime time = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.of("Z"));
        return ZonedDateTime.from(time);
    }



}
