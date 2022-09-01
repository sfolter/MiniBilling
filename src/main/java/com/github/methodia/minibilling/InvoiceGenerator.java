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
import java.util.concurrent.TimeUnit;

public class InvoiceGenerator {

    private final User user;
    private final Collection<Measurement> measurements;
    private final Collection<Price> prices;
    private final String yearMonthStr;

    private final String currency;

    private final String key;


    public InvoiceGenerator(final User user, final Collection<Measurement> measurements, final Collection<Price> prices,
                            final String yearMonthStr, final String currency, final String key) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
        this.yearMonthStr = yearMonthStr;
        this.currency = currency;
        this.key = key;
    }

    BigDecimal totalAmount = BigDecimal.ZERO;
    BigDecimal totalAmountWithVat = BigDecimal.ZERO;

    static final int PERCENTAGE = 20;
    static final String NAME = "Standing charge";
    static final String UNIT = "days";

    public Invoice generate(final int taxedAmountPercentageVat1) throws IOException, ParseException {
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                measurements, prices);
        final List<QuantityPricePeriod> distribute = proportionalMeasurementDistributor.distribute();
        final LocalDateTime yearMonthLocalDate = localDateTimeToReport();
        final List<InvoiceLine> invoiceLines = new ArrayList<>();
        final List<Vat> vatLines = new ArrayList<>();
        final List<Taxes> taxesLines = new ArrayList<>();
        final CurrencyGenerator currencyGenerator = new CurrencyGenerator(currency, key);
        final BigDecimal currencyValue = currencyGenerator.generateCurrency();

        int index = 1;

        for (final QuantityPricePeriod qpp : distribute) {
            final LocalDateTime end = qpp.getEnd();
            if (0 <= yearMonthLocalDate.compareTo(end)) {
                //              Invoice Line
                final BigDecimal quantity = qpp.getQuantity();
                final LocalDateTime start = qpp.getStart();
                final String product = qpp.getPrice().getProduct();
                final BigDecimal price = qpp.getPrice().getValue();
                final int priceList = user.getPriceListNumber();
                BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue())
                        .setScale(2, RoundingMode.HALF_UP);
                amount = amount.multiply(currencyValue).setScale(2, RoundingMode.HALF_UP);
                totalAmount = totalAmount.add(amount);
                //              Taxes line
                final int indexInTaxes = index;
                final int quantityDays = (int) start.toLocalDate().atStartOfDay()
                        .until(end.toLocalDate().atTime(23, 59, 59), ChronoUnit.DAYS);
                final BigDecimal priceInTaxes = new BigDecimal("1.6").setScale(2, RoundingMode.HALF_UP);
                BigDecimal amountInTaxes = priceInTaxes.multiply(BigDecimal.valueOf(quantityDays))
                        .setScale(2, RoundingMode.HALF_UP);
                totalAmount = totalAmount.add(amountInTaxes);
                amountInTaxes = amountInTaxes.multiply(currencyValue).setScale(2, RoundingMode.HALF_UP);
                //              Vat Line
                int indexInVat = 1;
                final BigDecimal amountInVat = amount.multiply(
                        new BigDecimal(20).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
                final BigDecimal amountForLineAndVat = amountInVat.add(amount);
                totalAmountWithVat = totalAmountWithVat.add(amountForLineAndVat);
                final int taxesIndexForVat = index;
                //                final int taxedAmountPercentageVat1 = 60;
                final int taxedAmountPercentageVat2 = 100 - taxedAmountPercentageVat1;
                final int taxedAmountPercentageVat3 = taxedAmountPercentageVat1 + taxedAmountPercentageVat2;
                BigDecimal amountInVat1 = amount.multiply(
                        new BigDecimal(taxedAmountPercentageVat1).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
                amountInVat1 = amountInVat1.multiply(new BigDecimal(PERCENTAGE))
                        .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                BigDecimal amountInVat2 = amount.multiply(
                        new BigDecimal(taxedAmountPercentageVat2).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
                amountInVat2 = amountInVat2.multiply(new BigDecimal(PERCENTAGE - 10))
                        .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                final BigDecimal amountInVat3 = amountInTaxes.multiply(new BigDecimal(PERCENTAGE))
                        .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                totalAmountWithVat = totalAmountWithVat.add(amountInVat1).add(amountInVat2).add(amountInVat3)
                        .add(amount).add(amountInTaxes);

                invoiceLines.add(new InvoiceLine(index, quantity, start, end, product, price, priceList, amount));
                taxesLines.add(new Taxes(indexInTaxes, index, NAME, quantityDays, UNIT, priceInTaxes, amountInTaxes));
                vatLines.add(new Vat(indexInVat, index, 0, taxedAmountPercentageVat1, PERCENTAGE, amountInVat1));
                vatLines.add(
                        new Vat(indexInVat++ + 1, index, 0, taxedAmountPercentageVat2, PERCENTAGE - 10, amountInVat2));
                vatLines.add(new Vat(indexInVat++ + 1, 0, taxesIndexForVat, taxedAmountPercentageVat3, PERCENTAGE,
                        amountInVat3));
                index++;
            }
        }
        final LocalDateTime documentDate = LocalDateTime.now();
        final String documentNumber = Invoice.getDocumentNumber();

        return new Invoice(documentDate, documentNumber, user, totalAmount, totalAmountWithVat, invoiceLines, vatLines,
                taxesLines);
    }

    public LocalDateTime localDateTimeToReport() {
        final YearMonth yearMonth = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yy-MM"));
        final LocalDateTime yearMonthLocalDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return yearMonthLocalDate;
    }

}