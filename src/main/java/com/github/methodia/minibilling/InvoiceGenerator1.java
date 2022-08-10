package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class InvoiceGenerator1 {
    private final User user;
    private final Collection<Measurement> measurements;
    private final Collection<Price> prices;

    public InvoiceGenerator1(User user, Collection<Measurement> measurements, Collection<Price> prices) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
    }

    public Invoice generate(LocalDateTime dateReportingTo) {

        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<Vat> vat = new ArrayList<>();

        BigDecimal variable = new BigDecimal(0);
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalAmountWithVat = BigDecimal.ZERO;
        int percentage = 20;
        List <Integer> vattedInvoiceLines=new ArrayList<>();
        int index = 0;
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal vatAmount = BigDecimal.ZERO;
        String documentNumber = null;
        BigDecimal totalAmountWIthVat = BigDecimal.ZERO;
        BigDecimal totalAmount1 = BigDecimal.ZERO;
        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            if (dateReportingTo.compareTo(qpp.getEnd()) >= 0) {
                BigDecimal quantity = qpp.getQuantity().setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                index = invoiceLines.size() + 1;
                LocalDateTime lineStart = qpp.getStart();
                LocalDateTime lineEnd = qpp.getEnd();
                String product = qpp.getPrice().getProduct();
                BigDecimal price = qpp.getPrice().getValue();
                int priceList = user.getNumberPricingList();
                amount = qpp.getQuantity().multiply(qpp.getPrice().getValue()).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                totalAmount = variable.add(amount).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                totalAmount1 = totalAmount1.add(totalAmount);

                //  vattedInvoiceLines.add(index);
                vattedInvoiceLines.add(index);
                totalAmountWithVat = amount.multiply(BigDecimal.valueOf(0.2));
                vatAmount=  totalAmountWithVat.add(vatAmount).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();;
                totalAmountWIthVat = amount.add(totalAmountWithVat).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();;
                sum = sum.add( totalAmountWIthVat).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                invoiceLines.add(new InvoiceLine(index, quantity, lineStart, lineEnd, product, price, priceList, amount));

            } else {
                continue;
            }

        }
        vat.add(new Vat(vat.size()+1,vattedInvoiceLines,percentage,vatAmount));
        documentNumber = Invoice.getDocumentNumber();

        String consumer = user.getName();
        BigDecimal totalAmountWIthVat1 = BigDecimal.ZERO;

        return new Invoice(documentNumber, consumer, user.getRef(), totalAmount1, totalAmountWIthVat1.add(sum).setScale(2, RoundingMode.HALF_UP), invoiceLines,vat);

    }




}