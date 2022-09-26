package com.example.SpringBatchExample;


import com.example.SpringBatchExample.generators.InvoiceGenerator;
import com.example.SpringBatchExample.generators.MeasurementGenerator;
import com.example.SpringBatchExample.models.User;
import com.example.SpringBatchExample.repositories.InvoiceRepository;
import com.example.SpringBatchExample.repositories.ReadingRepository;
import com.example.SpringBatchExample.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@SpringBootApplication
@Transactional
@EnableConfigurationProperties
@EntityScan(basePackages = "com.example.SpringBatchExample.models")
public class Main {

    public static void main(final String[] args) {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(
                Main.class, args);

        final String dateReporting = args[0];
        final LocalDateTime parseReportingDate = LocalDateTime.from(getReportingDate(dateReporting));
       // final String inputDir = args[1];
        final String outputDir = args[2];

        final UserRepository user = applicationContext.getBean(UserRepository.class);
        final List<User> findAllUsers = user.findAll();
        findAllUsers.stream().map(User::getPrices);
        final ReadingRepository reading = applicationContext.getBean(ReadingRepository.class);

        final Converter currencyConverter = new CurrencyConverter();

        final String currencyFrom = "BGN";
        final String currencyTo = "EUR";

        final MeasurementGenerator measurementGenerator = new MeasurementGenerator();
        final InvoiceGenerator invoiceGenerator = new InvoiceGenerator();

 final InvoiceRepository invoiceRepository = applicationContext.getBean(InvoiceRepository.class);
        findAllUsers.stream().map(u->measurementGenerator.generate(u, reading.findByRefNumber(u.getRefNumber())))
               .map(measurements -> invoiceGenerator.generate(parseReportingDate, measurements, currencyFrom,
                       currencyTo, currencyConverter))
               .forEach(invoice -> {invoiceRepository.saveAndFlush(invoice);
       FileMaker.save(invoice,outputDir,parseReportingDate);});

    }
    private static ZonedDateTime getReportingDate(final String dateReporting) {
        final YearMonth yearMonth = YearMonth.parse(dateReporting, DateTimeFormatter.ofPattern("yy-MM"));
        final ZonedDateTime time = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.of("Z"));
        return ZonedDateTime.from(time);
    }


}
