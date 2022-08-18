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
    private final User user;
    private final Collection<Measurement> measurements;
    private final Collection<Price> prices;
    private final String yearMonthStr;

    private final String currency;

    private final String key;

    public InvoiceGenerator(User user, Collection<Measurement> measurements, Collection<Price> prices, String yearMonthStr, String currency, String key) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
        this.yearMonthStr = yearMonthStr;
        this.currency = currency;
        this.key = key;
    }

    BigDecimal totalAmount = BigDecimal.ZERO;
    BigDecimal totalAmountWithVat = BigDecimal.ZERO;

    public Invoice generate() throws IOException, ParseException {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        List<QuantityPricePeriod> distribute = proportionalMeasurementDistributor.distribute();
        LocalDateTime yearMonthLocalDate = localDateTimeToReport();
        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<Vat> vatLines = new ArrayList<>();
        List<Taxes> taxesLines = new ArrayList<>();

        CurrencyGenerator currencyGenerator = new CurrencyGenerator(currency, key);
        BigDecimal currencyValue = currencyGenerator.generateCurrency();

        int PERCENTAGE = 20;
        String NAME = "Standing charge";
        String UNIT = "days";

        int index = 1;

        for (QuantityPricePeriod qpp : distribute) {
            LocalDateTime end = qpp.getEnd();
            if (yearMonthLocalDate.compareTo(end) >= 0) {
//              Invoice Line
                BigDecimal quantity = qpp.getQuantity();
                LocalDateTime start = qpp.getStart();
                String product = qpp.getPrice().getProduct();
                BigDecimal price = qpp.getPrice().getValue();
                int priceList = user.getPriceListNumber();
                BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue()).setScale(2, RoundingMode.HALF_UP);
                amount = amount.multiply(currencyValue).setScale(2, RoundingMode.HALF_UP);
                totalAmount = totalAmount.add(amount);
//              Taxes line
                int indexInTaxes = index;
                int quantityInTaxes = (int) ChronoUnit.DAYS.between(start, end);
                BigDecimal priceInTaxes = new BigDecimal("1.6").setScale(2, RoundingMode.HALF_UP);
                BigDecimal amountInTaxes = priceInTaxes.multiply(BigDecimal.valueOf(quantityInTaxes)).setScale(2, RoundingMode.HALF_UP);
                amountInTaxes = amountInTaxes.multiply(currencyValue).setScale(2, RoundingMode.HALF_UP);
//              Vat Line
                int indexInVat = index;
//                BigDecimal amountInVat = amount.multiply(new BigDecimal(20).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
//                BigDecimal amountForLineAndVat = amountInVat.add(amount);
//                totalAmountWithVat = totalAmountWithVat.add(amountForLineAndVat);

                int taxesIndexForVat = index;
                int taxedAmountPercentageVat1 = 60;
                int taxedAmountPercentageVat2 = 100-taxedAmountPercentageVat1;
                int taxedAmountPercentageVat3 = taxedAmountPercentageVat1+taxedAmountPercentageVat2;
                BigDecimal amountInVat1 = amount.multiply(new BigDecimal(taxedAmountPercentageVat1).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
                amountInVat1=(amountInVat1.multiply(new BigDecimal(PERCENTAGE)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
                BigDecimal amountInVat2 = amount.multiply(new BigDecimal(taxedAmountPercentageVat2).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
                amountInVat2=(amountInVat2.multiply(new BigDecimal(PERCENTAGE)).divide(new BigDecimal(100), 2 , RoundingMode.HALF_UP));
                BigDecimal amountInVat3 = (amountInTaxes.multiply(new BigDecimal(PERCENTAGE)).divide(new BigDecimal(100), 2 , RoundingMode.HALF_UP));
                totalAmountWithVat = totalAmountWithVat.add(amountInVat1);
                totalAmountWithVat = totalAmountWithVat.add(amountInVat2);
                totalAmountWithVat = totalAmountWithVat.add(amountInVat3);
                totalAmountWithVat = totalAmountWithVat.add(amount);
                totalAmountWithVat = totalAmountWithVat.add(amountInTaxes);

                invoiceLines.add(new InvoiceLine(index, quantity, start, end, product, price, priceList, amount));
                taxesLines.add(new Taxes(indexInTaxes, index, NAME, quantityInTaxes, UNIT, priceInTaxes, amountInTaxes));
                vatLines.add(new Vat(indexInVat, index, 0, taxedAmountPercentageVat1,  PERCENTAGE, amountInVat1));
                vatLines.add(new Vat(indexInVat, index, 0, taxedAmountPercentageVat2,  PERCENTAGE, amountInVat2));
                vatLines.add(new Vat(indexInVat,0, taxesIndexForVat, taxedAmountPercentageVat3,  PERCENTAGE, amountInVat3));
                index++;
            }
        }
        LocalDateTime documentDate = LocalDateTime.now();
        String documentNumber = Invoice.getDocumentNumber();

        return new Invoice(documentDate, documentNumber, user, totalAmount, totalAmountWithVat, invoiceLines, vatLines, taxesLines);
    }

    public LocalDateTime localDateTimeToReport() {
        YearMonth yearMonth = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yy-MM"));
        final LocalDateTime yearMonthLocalDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return yearMonthLocalDate;
    }
}