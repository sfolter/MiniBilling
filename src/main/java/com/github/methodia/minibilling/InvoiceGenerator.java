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
public class InvoiceGenerator {
    private final User user;
    private final Collection<Measurement> measurements;
    private final Collection<Price> prices;

    public InvoiceGenerator(User user, Collection<Measurement> measurements, Collection<Price> prices) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
    }

    public Invoice generate(LocalDateTime dateReportingTo) {

        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<Vat> vat = new ArrayList<>();


        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalAmountWithVat = BigDecimal.ZERO;

        List<Integer> vattedInvoiceLines = new ArrayList<>();
        String documentNumber = null;
        int index = 0;
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal vatAmount = BigDecimal.ZERO;
        BigDecimal totalAmountWIthVat = BigDecimal.ZERO;

        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            if (dateReportingTo.compareTo(qpp.getEnd()) >= 0) {
                index = invoiceLines.size() + 1;

                InvoiceLine invoiceLine = createInvoiceLine(index, qpp);
                invoiceLines.add(invoiceLine);
                totalAmount = totalAmount.add(invoiceLine.getAmount()).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

                vattedInvoiceLines.add(index);



                Vat v = createVat(invoiceLine);
                vat.add(v);
                totalAmountWIthVat = invoiceLine.getAmount().add(totalAmountWithVat).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();


            } else {
                continue;
            }

        }
        //    vat.add(new Vat(vat.size()+1,vattedInvoiceLines,percentage,vatAmount));
        documentNumber = Invoice.getDocumentNumber();

        String consumer = user.getName();
        BigDecimal totalAmountWIthVat1 = BigDecimal.ZERO;

        return new Invoice(documentNumber, consumer, user.getRef(), totalAmount, totalAmountWIthVat1.add(sum).setScale(2, RoundingMode.HALF_UP), invoiceLines, vat);

    }

    private InvoiceLine createInvoiceLine(int index, QuantityPricePeriod qpp) {
        BigDecimal quantity = qpp.getQuantity().setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        LocalDateTime lineStart = qpp.getStart();
        LocalDateTime lineEnd = qpp.getEnd();
        String product = qpp.getPrice().getProduct();
        BigDecimal price = qpp.getPrice().getValue();
        int priceList = user.getNumberPricingList();
        BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue()).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        return new InvoiceLine(index, quantity, lineStart, lineEnd, product, price, priceList, amount);
    }

    private Vat createVat(InvoiceLine invoiceLine) {
        List<Integer> linesOfVat = new ArrayList<>();
        linesOfVat.add(invoiceLine.getIndex());
        BigDecimal vatAmount = invoiceLine.getAmount().multiply(BigDecimal.valueOf(0.2)).setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros();
        int percentage = 20;


        return new Vat(invoiceLine.getIndex(), linesOfVat, percentage, vatAmount);
    }


}