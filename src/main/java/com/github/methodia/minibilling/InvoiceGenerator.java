package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class InvoiceGenerator {

    final private CurrencyConverter currencyConverter;

    public InvoiceGenerator(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    /**
     * Looping through MMs and Prices and comparing the end date of measurements dates with pricing dates and if
     * its needed, dividing them into different lines,also geting the data(,quantity,price,start of the line,
     * end of the line etc.
     */
    public Invoice generate(User user, Collection<Measurement> measurements, LocalDateTime dateReportingTo) {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor =
                new ProportionalMeasurementDistributor(measurements, user.getPrice());
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();

        Map<String, List<Object>> productMap = new LinkedHashMap<>();


        List<Tax> taxList = new ArrayList<>();

        for (QuantityPricePeriod qpp : quantityPricePeriods) {


            if (dateReportingTo.compareTo(qpp.getEnd()) >= 0) {
                int lineIndex = invoiceLines.size() + 1;
                InvoiceLine invoiceLine = createInvoiceLine(lineIndex, qpp, user);
                invoiceLines.add(invoiceLine);
                String product = qpp.getProduct();
                productMap.put(product, Collections.singletonList(invoiceLines));
                int taxListSize = taxList.size();
                taxList.add(createTax(invoiceLine, taxListSize));
                product = "Standing charge";
                productMap.put(product, Collections.singletonList(taxList));
            } else {
                break;
            }
        }

        String documentNumber = Invoice.getDocumentNumber();
        String userName = user.getName();
        List<Vat> vat = productMap.entrySet().stream().map(a -> createVat(a.getValue())).toList();
        //productMap.keySet().forEach(a ->  createVat(invoiceLines));

        BigDecimal totalAmount = invoiceLines.stream().map(InvoiceLine::getAmount).reduce(new BigDecimal(0)
                , BigDecimal::add);
        BigDecimal totalAmountWithVat = vat.stream().map(Vat::getAmount).reduce(totalAmount, BigDecimal::add);
        return new Invoice(documentNumber, userName, user.getRef(), totalAmount, totalAmountWithVat, invoiceLines,
                vat, taxList);
    }

    private Tax createTax(InvoiceLine invoiceLine, int taxListSize) {

        List<Integer> invoiceIndex = new ArrayList<>();
        invoiceIndex.add(invoiceLine.getIndex());
        BigDecimal quantity = new BigDecimal(ChronoUnit.DAYS.between(invoiceLine.getStart(), invoiceLine.getEnd()));
        BigDecimal amount = quantity.multiply(new BigDecimal("1.6"));
        return new Tax(taxListSize + 1, invoiceIndex, quantity, amount);
    }

    private void createVat(List<Object> invoiceLines, String product) {
        List<InvoiceLine> invoiceLines1=invoiceLines;
        if(product.equals("gas")||product.equals("elec")){
        }
        List<Integer> vattedLines = invoiceLines.stream().map(InvoiceLine::getIndex).toList();
        int counter = 0;
        counter += 1;
        BigDecimal amount = invoiceLines.stream().map(InvoiceLine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add).multiply(new BigDecimal("0.2"))
                .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        Vat vat=new Vat(counter, vattedLines, "20", amount);
    }

    private InvoiceLine createInvoiceLine(int lineIndex, QuantityPricePeriod qpp, User user) {
        BigDecimal quantity = qpp.getQuantity();
        LocalDateTime start = qpp.getStart();
        LocalDateTime end = qpp.getEnd();
        BigDecimal price = qpp.getPrice();
        BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice())
                .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        // add the following method in order to set up your currency converter
        // .multiply(currencyConverter.getCurrencyValue(user.getCurrency()))
        //TODO add product to qpp; remove the following code
        String product = qpp.getProduct();
        return new InvoiceLine(lineIndex, quantity, start, end,
                product, price, user.getNumberPricingList(), amount);
    }


}