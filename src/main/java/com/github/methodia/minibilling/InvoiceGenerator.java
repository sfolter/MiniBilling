package com.github.methodia.minibilling;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvoiceGenerator {
    private User user;
    private Collection<Measurement> measurements;
    private Collection<Price> prices;
    private String yearMonthStr;

    private String currency;

    private String key;

    public InvoiceGenerator(User user, Collection<Measurement> measurements, Collection<Price> prices, String yearMonthStr, String currency, String key) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
        this.yearMonthStr = yearMonthStr;
        this.currency = currency;
        this.key = key;
    }

    public Invoice generate() throws IOException, ParseException {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        List<QuantityPricePeriod> distribute = proportionalMeasurementDistributor.distribute();
        LocalDateTime yearMonthLocalDate = localDateTimeToReport();
        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<VAT> vatLines = new ArrayList<>();
        List<Taxes> taxesLines = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalAmountWithVat = BigDecimal.ZERO;
        CurrencyGenerator currencyGenerator = new CurrencyGenerator(currency, key);
        BigDecimal currencyValue = currencyGenerator.generateCurrency();

        int percentage = 20;
        String name = "Standing charge";
        String unit = "days";

        int index = 1;

        for (QuantityPricePeriod qpp : distribute) {
            LocalDateTime end = qpp.getEnd();
            if (yearMonthLocalDate.compareTo(end) >= 0) {
                BigDecimal quantity = qpp.getQuantity();
                LocalDateTime start = qpp.getStart();
                String product = qpp.getPrice().getProduct();
                BigDecimal price = qpp.getPrice().getValue();
                int priceList = user.getPriceListNumber();
                BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue()).setScale(2, RoundingMode.HALF_UP);
                amount = amount.multiply(currencyValue).setScale(2, RoundingMode.HALF_UP);
                totalAmount = totalAmount.add(amount);
                int indexInVat = index;
                BigDecimal amountInVat = amount.multiply(new BigDecimal(20).divide(new BigDecimal(100))).setScale(2, RoundingMode.HALF_UP);
                BigDecimal amountForLineAndVat = amountInVat.add(amount);
                totalAmountWithVat = totalAmountWithVat.add(amountForLineAndVat);
                int indexInTaxes = index;
                int quantityinTaxes = (int) ChronoUnit.DAYS.between(start, end);
                BigDecimal priceinTaxes = new BigDecimal("1.6").setScale(2, RoundingMode.HALF_UP);
                BigDecimal amountInTaxes = priceinTaxes.multiply(BigDecimal.valueOf(quantityinTaxes)).setScale(2, RoundingMode.HALF_UP);
                amountInTaxes=amountInTaxes.multiply(currencyValue).setScale(2, RoundingMode.HALF_UP);

                invoiceLines.add(new InvoiceLine(index, quantity, start, end, product, price, priceList, amount));
                taxesLines.add(new Taxes(indexInTaxes, index, name, quantityinTaxes, unit, priceinTaxes, amountInTaxes));
                vatLines.add(new VAT(indexInVat, index, percentage, amountInVat));
                index++;
            }
        }
        LocalDateTime documentDate = LocalDateTime.now();
        String documentNumber = Invoice.getDocumentNumber();

        return new Invoice(documentDate, documentNumber, user, totalAmount, totalAmountWithVat, invoiceLines, vatLines, taxesLines);
    }

    public LocalDateTime localDateTimeToReport(){
        YearMonth yearMonth = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yy-MM"));
        final LocalDateTime yearMonthLocalDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return yearMonthLocalDate;
    }
}