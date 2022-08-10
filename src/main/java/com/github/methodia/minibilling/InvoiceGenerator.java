package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvoiceGenerator {
    private final User user;
    private final Collection<Measurement> measurements;
    private final Collection<Price> prices;

    public InvoiceGenerator(User user, Collection<Measurement> measurements, Collection<Price> prices) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
    }

    public Invoice generate(long documentNumber, String borderTime) {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<Vat> vat = new ArrayList<>();
        int counter = 1;
        BigDecimal totalBigDecimal = BigDecimal.ZERO;
        BigDecimal totalAmountWithVat = BigDecimal.ZERO;
        LocalDate borderDate = Formatter.parseBorder(borderTime);

        for (QuantityPricePeriod qpp : quantityPricePeriods) {

            if (qpp.getEnd().toLocalDate().isBefore(borderDate)) {

                int index = counter++;
                InvoiceLine invoiceLine = createInvoiceLine(index, qpp);
                totalBigDecimal = totalBigDecimal.add(invoiceLine.getAmount()).stripTrailingZeros();
                BigDecimal vatAmount = totalBigDecimal.multiply(BigDecimal.valueOf(0.2)).setScale(2, RoundingMode.HALF_UP)
                        .stripTrailingZeros();
                totalAmountWithVat = totalBigDecimal.add(vatAmount).stripTrailingZeros();
                invoiceLines.add(invoiceLine);
                Vat vat1=createVat(invoiceLine);
                vat.add(vat1);
            }
        }
        LocalDateTime documentDate = LocalDateTime.now();
        String docNumber = String.valueOf(documentNumber);
        String consumer = user.getName();
        String reference = user.getRef();


        return new Invoice(documentDate, docNumber, consumer, reference, totalBigDecimal, totalAmountWithVat, invoiceLines, vat);
    }

    private InvoiceLine createInvoiceLine(int lineIndex, QuantityPricePeriod qpp) {

        BigDecimal quantity = qpp.getQuantity();
        LocalDateTime start = qpp.getStart().toLocalDateTime();
        LocalDateTime end = qpp.getEnd().toLocalDateTime();
        String product = qpp.getPrice().getProduct();
        BigDecimal price = qpp.getPrice().getValue();
        int priceList = user.getPriceListNumber();
        BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue()).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        return new InvoiceLine(lineIndex, quantity, start, end,
                product, price, priceList, amount);
    }

    private Vat createVat(InvoiceLine invoiceLine) {

        List<Integer> vattedLines = new ArrayList<>();
        vattedLines.add(invoiceLine.getIndex());

        BigDecimal vatAmount = invoiceLine.getAmount().multiply(BigDecimal.valueOf(0.2)).setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros();

        return new Vat(invoiceLine.getIndex(), vattedLines, vatAmount);
    }
}
