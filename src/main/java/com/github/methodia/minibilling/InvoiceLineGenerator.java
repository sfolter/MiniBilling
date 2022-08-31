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

    public List<InvoiceLine> createInvoiceLine(final List<Measurement> measurements, final LocalDate borderDate,
                                               final CurrencyCalculator currencyCalculator,
                                               final String fromCurrency, final String toCurrency) {

        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                measurements);
        final Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        final List<InvoiceLine> invoiceLines = new ArrayList<>();
        for (final QuantityPricePeriod qpp : quantityPricePeriods) {
            if (qpp.getEnd().toLocalDate().isBefore(borderDate)) {
                final BigDecimal quantity = qpp.getQuantity();
                final LocalDateTime start = qpp.getStart().toLocalDateTime();
                final LocalDateTime end = qpp.getEnd().toLocalDateTime();
                final String product = qpp.getPrice().getProduct();
                final BigDecimal price = qpp.getPrice().getValue();
                final int priceList = qpp.getUser().getPriceListNumber();
                final BigDecimal value = qpp.getQuantity().multiply(qpp.getPrice().getValue())
                        .setScale(2, RoundingMode.HALF_UP);
                final BigDecimal amount=currencyCalculator.calculate(value,fromCurrency,toCurrency)
                        .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                totalAmountLines = totalAmountLines.add(amount);
                invoiceLines.add(new InvoiceLine(invoiceLines.size() + 1, quantity, start, end,
                        product, price, priceList, amount));
            }
        }
        return invoiceLines;
    }
}
