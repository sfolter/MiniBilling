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

import static com.github.methodia.minibilling.Main.API_KEY;


public class InvoiceGenerator {
    public static final int taxedAmountPercentageVat1 = 60;
    public static final int taxedAmountPercentageVat2 = 40;
    private User user;
    private Collection<Measurement> measurements;
    private Collection<Price> prices;
    private String yearMonthStr;

    private String currency;


    public InvoiceGenerator(User user, Collection<Measurement> measurements, Collection<Price> prices, String yearMonthStr, String currency) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
        this.yearMonthStr = yearMonthStr;
        this.currency = currency;
    }
    static final int PERCENTAGE = 20;
    public Invoice generate() throws IOException, ParseException {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        List<QuantityPricePeriod> distribute = proportionalMeasurementDistributor.distribute();
        LocalDateTime yearMonthLocalDate = localDateTimeToReport();
        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<VatLine> vatLines = new ArrayList<>();
        List<TaxLines> taxesLines = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalAmountWithVat = BigDecimal.ZERO;
        CurrencyConvertor currencyConvertor = new CurrencyConvertor(currency, API_KEY);
        BigDecimal currencyValue = currencyConvertor.generateCurrency();

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

                /*
                final int indexInTaxes = index;
                final int quantityDays = (int) start.toLocalDate().atStartOfDay().until(end.toLocalDate().atTime(23, 59, 59), ChronoUnit.DAYS);
                final BigDecimal priceInTaxes = new BigDecimal("1.6").setScale(2, RoundingMode.HALF_UP);
                BigDecimal amountInTaxes = priceInTaxes.multiply(BigDecimal.valueOf(quantityDays)).setScale(2, RoundingMode.HALF_UP);
                totalAmount = totalAmount.add(amountInTaxes);
                amountInTaxes = amountInTaxes.multiply(currencyValue).setScale(2, RoundingMode.HALF_UP);
                 */

                int indexInVat = index;

                BigDecimal amountInVat = amount.multiply(new BigDecimal(20).divide(new BigDecimal(100))).setScale(2, RoundingMode.HALF_UP);
                BigDecimal amountForLineAndVat = amountInVat.add(amount);
                totalAmountWithVat = totalAmountWithVat.add(amountForLineAndVat);
                final int indexInTaxes = index;
                final int quantityInTaxes = (int) ChronoUnit.DAYS.between(start, end);
                final  int taxedAmountPercentageVat3 = taxedAmountPercentageVat1 + taxedAmountPercentageVat2;

                BigDecimal priceInTaxes = new BigDecimal("1.6").setScale(2, RoundingMode.HALF_UP);
                BigDecimal amountInTaxes = priceInTaxes.multiply(BigDecimal.valueOf(quantityInTaxes)).setScale(2, RoundingMode.HALF_UP);
                amountInTaxes=amountInTaxes.multiply(currencyValue).setScale(2, RoundingMode.HALF_UP);

                BigDecimal amountInVat1 = amount.multiply(new BigDecimal(taxedAmountPercentageVat1).divide(new BigDecimal(100),2,RoundingMode.HALF_UP))
                        .multiply(new BigDecimal(PERCENTAGE)).divide(new BigDecimal(100),2,RoundingMode.HALF_UP);;

                BigDecimal amountInVat2 = amount.multiply(new BigDecimal(taxedAmountPercentageVat2).divide(new BigDecimal(100),2,RoundingMode.HALF_UP))
                .multiply(new BigDecimal(PERCENTAGE-10)).divide(new BigDecimal(100),2,RoundingMode.HALF_UP);

                final BigDecimal amountInVat3 = amountInTaxes.multiply(new BigDecimal(PERCENTAGE)).divide(new BigDecimal(100), 2 , RoundingMode.HALF_UP);
                totalAmountWithVat = totalAmountWithVat.add(amountInVat1).add(amountInVat2).add(amountInVat3).add(amount).add(amountInTaxes);





                invoiceLines.add(new InvoiceLine(index, quantity, start, end, product, price, priceList, amount));
                taxesLines.add(new TaxLines(indexInTaxes, index, name, quantityInTaxes, unit, priceInTaxes, amountInTaxes));
                vatLines.add(new VatLine(index, index, 0, taxedAmountPercentageVat1,  PERCENTAGE, amountInVat1));
                vatLines.add(new VatLine(indexInVat+++1, index, 0, taxedAmountPercentageVat2,  PERCENTAGE-10, amountInVat2));
                vatLines.add(new VatLine(indexInVat+++1,0, indexInTaxes, taxedAmountPercentageVat3,  PERCENTAGE, amountInVat3));
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