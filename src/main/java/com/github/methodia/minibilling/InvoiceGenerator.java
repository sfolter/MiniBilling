package com.github.methodia.minibilling;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvoiceGenerator {
    private CurrencyConvertor currencyConvertor = new CurrencyConvertor();
    private final BigDecimal percentage = BigDecimal.valueOf(20);

    public InvoiceGenerator(CurrencyConvertor currencyConvertor) {
        this.currencyConvertor=currencyConvertor;

    }

    public Invoice generate(User user, Collection<Measurement> measurements, Collection<Price> prices, String yearMonthStr, String currency)
            throws IOException, ParseException {
        BigDecimal currencyRate = currencyConvertor.convertCurrency(currency);
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();
        YearMonth yearMonth = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yy-MM"));
        final LocalDateTime yearMonthLocalDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<VatLine> vatLines = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalAmountWithVat = BigDecimal.ZERO;
        int counter = 1;
        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            LocalDateTime end = qpp.getEnd();
            if (yearMonthLocalDate.compareTo(end) >= 0) {
                int index = counter++;
                BigDecimal quantity = qpp.getQuantity();
                LocalDateTime start = qpp.getStart();

                String product = qpp.getPrice().getProduct();
                BigDecimal price = qpp.getPrice().getValue();
                price = price.multiply(currencyRate).setScale(2, RoundingMode.HALF_EVEN);
                int priceList = user.getPriceListNumber();
                BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue());
                amount = amount.multiply(currencyRate).setScale(2, RoundingMode.HALF_EVEN);
                totalAmount = totalAmount.add(amount);

                BigDecimal vatAmount = (amount.multiply(percentage)).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_EVEN);
                BigDecimal amountWithVat = vatAmount.add(amount);
                totalAmountWithVat = totalAmountWithVat.add(amountWithVat);
                invoiceLines.add(new InvoiceLine(index, quantity, start, end, product, price, priceList, amount));
                vatLines.add(new VatLine(index, index, percentage, vatAmount));
            }
        }

        LocalDateTime documentDate = LocalDateTime.now();
        String documentNumber = Invoice.getDocumentNumber();
        User consumer = user;

        return new Invoice(documentDate, documentNumber, consumer, totalAmount, totalAmountWithVat, invoiceLines, vatLines);
    }
}