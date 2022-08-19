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
    private static final BigDecimal VAT_PERCENTAGE = new BigDecimal("20");
    private static final BigDecimal SECOND_VAT_PERCENTAGE=new BigDecimal("10");
    final BigDecimal taxedAmountPercentage1=new BigDecimal("60");
    final BigDecimal taxedAmountPercentage2=new BigDecimal("40");
    final BigDecimal taxedAmountPercentage3=new BigDecimal("100");
    private final CurrencyConvertor currencyConvertor;
    private static final String NAME = "Standing charge";
    private static final String UNIT_DAYS = "days";
    private BigDecimal priceForTaxes = new BigDecimal("1.6");

    public InvoiceGenerator(final CurrencyConvertor currencyConvertor) {
        this.currencyConvertor = currencyConvertor;
    }


    public Invoice generate(final User user, final Collection<Measurement> measurements, final Collection<Price> prices, final String yearMonthStr, final String currency) throws IOException, ParseException {


        final BigDecimal currencyRate = currencyConvertor.convertCurrency(currency);
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        final Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();
        final YearMonth yearMonth = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yy-MM"));
        final LocalDateTime yearMonthLocalDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        final List<InvoiceLine> invoiceLines = new ArrayList<>();
        final List<VatLine> vatLines = new ArrayList<>();
        final List<TaxesLine> taxesLines = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalAmountWithVat = BigDecimal.ZERO;
        int lineIndex = 0;
        int vatIndex=1;
        for (final QuantityPricePeriod qpp : quantityPricePeriods) {
            final LocalDateTime end = qpp.end();
            if (0 <= yearMonthLocalDate.compareTo(end)) {
                lineIndex++;

                final BigDecimal quantity = qpp.quantity();
                final LocalDateTime start = qpp.start();

                final String product = qpp.price().product();
                BigDecimal price = qpp.price().value();
                price = price.multiply(currencyRate).setScale(2, RoundingMode.HALF_EVEN);
                final int priceList = user.priceListNumber();
                BigDecimal amount = qpp.quantity().multiply(qpp.price().value());
                amount = amount.multiply(currencyRate).setScale(2, RoundingMode.HALF_EVEN);


                final long daysQuantity = start.toLocalDate().atStartOfDay().until(end.toLocalDate().atTime(23,59), ChronoUnit.DAYS);
                priceForTaxes = priceForTaxes.multiply(currencyRate).setScale(2, RoundingMode.HALF_EVEN);
                final BigDecimal amountForTaxes = priceForTaxes.multiply(BigDecimal.valueOf(daysQuantity)).setScale(2, RoundingMode.HALF_EVEN);

                BigDecimal vatAmount1 = amount.multiply(taxedAmountPercentage1)
                        .divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_EVEN);
                vatAmount1 = vatAmount1.multiply(VAT_PERCENTAGE).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_EVEN);
                BigDecimal vatAmount2 = amount.multiply(taxedAmountPercentage2)
                        .divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_EVEN);
                vatAmount2 = vatAmount2.multiply(SECOND_VAT_PERCENTAGE).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_EVEN);
                final BigDecimal vatAmount3= amountForTaxes.multiply(VAT_PERCENTAGE)
                        .divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_EVEN);



                totalAmount = totalAmount.add(amount).add(amountForTaxes);
                totalAmountWithVat = totalAmountWithVat.add(vatAmount1).add(vatAmount2).add(vatAmount3).add(totalAmount);

                invoiceLines.add(new InvoiceLine(lineIndex, quantity, start, end, product, price, priceList, amount));
                taxesLines.add(new TaxesLine(lineIndex, lineIndex, NAME, daysQuantity, UNIT_DAYS, priceForTaxes, amountForTaxes));
                vatLines.add(new VatLine(vatIndex, lineIndex, 0, taxedAmountPercentage1, VAT_PERCENTAGE, vatAmount1));
                vatLines.add(new VatLine(vatIndex+++1, lineIndex, 0, taxedAmountPercentage2, SECOND_VAT_PERCENTAGE, vatAmount2));
                vatLines.add(new VatLine(vatIndex+++1, lineIndex, 1, taxedAmountPercentage3, VAT_PERCENTAGE, vatAmount3));
            }
        }

        final LocalDateTime documentDate = LocalDateTime.now();
        final String documentNumber = Invoice.getDocumentNumber();

        return new Invoice(documentDate, documentNumber, user, totalAmount, totalAmountWithVat, invoiceLines, vatLines, taxesLines);
    }
}