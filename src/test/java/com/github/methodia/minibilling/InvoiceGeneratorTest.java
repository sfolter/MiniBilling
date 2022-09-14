//package com.github.methodia.minibilling;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//public class InvoiceGeneratorTest {
//
//    @Test
//    void generateInvoice() {
//
//        List<Price> priceList = new ArrayList<>();
////        priceList.add(new Price("gas", LocalDate.of(2020, 4, 5)
////                , LocalDate.of(2022, 5, 5), new BigDecimal("2.50"), 1));
////
////        User user = new User("Georgi Ivanov Simeonov", "2", 1, priceList, "EUR");
//
//        Collection<Measurement> measurementCollection = new ArrayList<>();
//        measurementCollection.add(new Measurement(LocalDateTime.of(2021, 4, 4, 11, 12, 13),
//                LocalDateTime.of(2021, 4, 24, 20, 20, 4), new BigDecimal("500"), user));
//        measurementCollection.add(new Measurement(LocalDateTime.of(2021, 4, 24, 20, 20, 5),
//                LocalDateTime.of(2021, 5, 4, 20, 20, 4), new BigDecimal("500"), user));
//
//        LocalDateTime borderLDT = LocalDateTime.of(2022, 4, 4, 4, 4, 4);
//
//        List<VatPercentages> vatPercentageList = new ArrayList<>();
//        vatPercentageList.add(new VatPercentages(new BigDecimal("60"), new BigDecimal("20")));
//        vatPercentageList.add(new VatPercentages(new BigDecimal("40"), new BigDecimal("10")));
//
//        BigDecimal currencyRate= new BigDecimal("2");
//        CurrencyConverter currencyConverter = (val) -> currencyRate;
//
//
//        InvoiceGenerator invoiceGenerator = new InvoiceGenerator(currencyConverter);
//        Invoice invoice = invoiceGenerator.generate(user, measurementCollection, borderLDT, vatPercentageList);
//
//
//
//        Assertions.assertEquals("10000", invoice.getDocNumber(), "DocumentNumber is incorrect.");
//        Assertions.assertEquals("Georgi Ivanov Simeonov", invoice.getConsumer(), "Consumer is incorrect.");
//        Assertions.assertEquals("2", invoice.getReference(), "Reference is incorrect.");
//        Assertions.assertEquals(new BigDecimal("2546.40").multiply(currencyRate).setScale(2,
//                RoundingMode.HALF_UP), invoice.getTotalAmount(), "TotalAmount is incorrect");
//        Assertions.assertEquals(new BigDecimal("2955.68").multiply(currencyRate).stripTrailingZeros(),
//                invoice.getTotalAmountWithVat(), "TotalAmountWithVat is incorrect");
//
//        Assertions.assertEquals(2, invoice.getLines().size(), "InvoiceLinesList's size is incorrect.");
//
//    }
//}
