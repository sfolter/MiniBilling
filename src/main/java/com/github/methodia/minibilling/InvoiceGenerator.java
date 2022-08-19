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
    private final BigDecimal VAT_PERCENTAGE = new BigDecimal("20");
    private final BigDecimal SECOND_VAT_PERCENTAGE=new BigDecimal("10");
    BigDecimal taxedAmountPercentage1=new BigDecimal("60");
    BigDecimal taxedAmountPercentage2=new BigDecimal("40");
    BigDecimal taxedAmountPercentage3=new BigDecimal("100");
    private final CurrencyConvertor currencyConvertor;
    private final String NAME = "Standing charge";
    private final String UNIT_DAYS = "days";
    private BigDecimal PRICE_FOR_TAXES = new BigDecimal("1.6");

    public InvoiceGenerator(CurrencyConvertor currencyConvertor) {
        this.currencyConvertor = currencyConvertor;
    }


    public Invoice generate(User user, Collection<Measurement> measurements, Collection<Price> prices, String yearMonthStr, String currency) throws IOException, ParseException {


        BigDecimal currencyRate = currencyConvertor.convertCurrency(currency);
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();
        YearMonth yearMonth = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yy-MM"));
        final LocalDateTime yearMonthLocalDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<VatLine> vatLines = new ArrayList<>();
        List<TaxesLine> taxesLines = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalAmountWithVat = BigDecimal.ZERO;
        int lineIndex = 0;
        int vatIndex=1;
        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            LocalDateTime end = qpp.getEnd();
            if (yearMonthLocalDate.compareTo(end) >= 0) {
                lineIndex++;

                BigDecimal quantity = qpp.getQuantity();
                LocalDateTime start = qpp.getStart();

                String product = qpp.getPrice().getProduct();
                BigDecimal price = qpp.getPrice().getValue();
                price = price.multiply(currencyRate).setScale(2, RoundingMode.HALF_EVEN);
                int priceList = user.getPriceListNumber();
                BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue());
                amount = (amount.multiply(currencyRate)).setScale(2, RoundingMode.HALF_EVEN);


                long daysQuantity = start.toLocalDate().atStartOfDay().until(end.toLocalDate().atTime(23,59), ChronoUnit.DAYS);
                PRICE_FOR_TAXES = PRICE_FOR_TAXES.multiply(currencyRate).setScale(2, RoundingMode.HALF_EVEN);
                BigDecimal amountForTaxes = (PRICE_FOR_TAXES.multiply(BigDecimal.valueOf(daysQuantity)).setScale(2, RoundingMode.HALF_EVEN));

                BigDecimal vatAmount1 = (amount.multiply(taxedAmountPercentage1)).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_EVEN);
                vatAmount1 = (vatAmount1.multiply(VAT_PERCENTAGE)).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_EVEN);
                BigDecimal vatAmount2 = (amount.multiply(taxedAmountPercentage2)).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_EVEN);
                vatAmount2 = (vatAmount2.multiply(SECOND_VAT_PERCENTAGE)).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_EVEN);
                BigDecimal vatAmount3=(amountForTaxes.multiply(VAT_PERCENTAGE)).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_EVEN);



                totalAmount = totalAmount.add(amount).add(amountForTaxes);
                totalAmountWithVat = totalAmountWithVat.add(vatAmount1).add(vatAmount2).add(vatAmount3).add(totalAmount);

                invoiceLines.add(new InvoiceLine(lineIndex, quantity, start, end, product, price, priceList, amount));
                taxesLines.add(new TaxesLine(lineIndex, lineIndex, NAME, daysQuantity, UNIT_DAYS, PRICE_FOR_TAXES, amountForTaxes));
                vatLines.add(new VatLine(vatIndex, lineIndex, 0, taxedAmountPercentage1, VAT_PERCENTAGE, vatAmount1));
                vatLines.add(new VatLine(vatIndex+++1, lineIndex, 0, taxedAmountPercentage2, SECOND_VAT_PERCENTAGE, vatAmount2));
                vatLines.add(new VatLine(vatIndex+++1, lineIndex, 1, taxedAmountPercentage3, VAT_PERCENTAGE, vatAmount3));
            }
        }

        LocalDateTime documentDate = LocalDateTime.now();
        String documentNumber = Invoice.getDocumentNumber();

        return new Invoice(documentDate, documentNumber, user, totalAmount, totalAmountWithVat, invoiceLines, vatLines, taxesLines);
    }
}