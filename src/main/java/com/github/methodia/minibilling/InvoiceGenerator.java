package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;


public class InvoiceGenerator {
    final private User user;
    final private Collection<Measurement> measurements;

    public InvoiceGenerator(User user, Collection<Measurement> measurements) {
        this.user = user;
        this.measurements = measurements;
    }
    /**Looping through MMs and Prices and comparing the end date of measurements dates with pricing dates and if
     * its needed, dividing them into different lines,also geting the data(,quantity,price,start of the line,
     * end of the line etc. */
    public Invoice generate(LocalDateTime dateReportingTo) {

        ProportionalMeasurementDistributor proportionalMeasurementDistributor =
                new ProportionalMeasurementDistributor(getMeasurements(), user.getPrice());
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<Integer> vatLineIndices = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        String product=null;

        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            if (dateReportingTo.compareTo(qpp.getEnd()) >= 0) {
                int lineIndex = invoiceLines.size() + 1;
                InvoiceLine invoiceLine = createInvoiceLine(lineIndex, qpp);
                invoiceLines.add(invoiceLine);
                vatLineIndices.add(lineIndex);
                totalAmount = totalAmount.add(invoiceLine.getAmount());
                product= qpp.getProduct();
            } else {
                break;
            }
        }
        String documentNumber = Invoice.getDocumentNumber();
        String userName = getUser().getName();

        BigDecimal vatAmount = totalAmount.multiply(BigDecimal.valueOf(0.2)).setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros();
        BigDecimal totalAmountWithVat = totalAmount.add(vatAmount).stripTrailingZeros();

        List<Vat> vat = createVat(invoiceLines,product,totalAmount);


        return new Invoice(documentNumber, userName, getUser().getRef(), totalAmount, totalAmountWithVat, invoiceLines, vat);
    }
    private List<Vat> createVat(List<InvoiceLine> invoiceLines,String product,BigDecimal totalAmount){
        List<InvoiceLine>invoiceLineList=invoiceLines.stream()
                .filter(invoiceLine1 -> invoiceLine1.getProduct().equals(product)).toList();

        List<Integer> vattedLines=new ArrayList<>();
        List<Vat> vatList =new ArrayList<>();
        for(InvoiceLine invoiceLine:invoiceLineList){
                vattedLines.add(invoiceLine.getIndex());
        }
        BigDecimal vatAmount = totalAmount.multiply(BigDecimal.valueOf(0.2)).setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros();


        vatList.add(new Vat(vatList.size()+1,vattedLines,vatAmount));
    return vatList;}

    private InvoiceLine createInvoiceLine(int lineIndex, QuantityPricePeriod qpp) {
        BigDecimal quantity = qpp.getQuantity();
        LocalDateTime start = qpp.getStart();
        LocalDateTime end = qpp.getEnd();
        BigDecimal price = qpp.getPrice();
        BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice())
                .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        //TODO add product to qpp; remove the following code
        String product = qpp.getProduct();

        return new InvoiceLine(lineIndex, quantity, start, end,
                product, price, getUser().getNumberPricingList(), amount);
    }


    private User getUser() {
        return user;
    }

    private Collection<Measurement> getMeasurements() {
        return measurements;
    }
}