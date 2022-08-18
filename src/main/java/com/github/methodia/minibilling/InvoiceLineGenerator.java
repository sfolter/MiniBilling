package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvoiceLineGenerator {
    private BigDecimal totalAmountLines = BigDecimal.ZERO;

    public BigDecimal getTotalAmountLines() {
        return totalAmountLines;
    }

    public List<InvoiceLine> createInvoiceLine(List<Measurement> measurements, LocalDate borderDate) {

        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();
        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            if (qpp.getEnd().toLocalDate().isBefore(borderDate)) {
                BigDecimal quantity = qpp.getQuantity();
                LocalDateTime start = qpp.getStart().toLocalDateTime();
                LocalDateTime end = qpp.getEnd().toLocalDateTime();
                String product = qpp.getPrice().getProduct();
                BigDecimal price = qpp.getPrice().getValue();
                int priceList = qpp.getUser().getPriceListNumber();
                BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue()).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                totalAmountLines = totalAmountLines.add(amount);
                invoiceLines.add(new InvoiceLine(invoiceLines.size() + 1, quantity, start, end,
                        product, price, priceList, amount));
            }
        }
        return invoiceLines;
    }

}
