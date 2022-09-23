package com.github.methodia.minibilling.mainlogic;

import com.github.methodia.minibilling.currency.CurrencyCalculator;
import com.github.methodia.minibilling.Measurement;
import com.github.methodia.minibilling.entity.InvoiceLine;

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
                final String priceList = qpp.getUser().getPriceListNumber().getId();
                final BigDecimal value = qpp.getQuantity().multiply(qpp.getPrice().getValue())
                        .setScale(2, RoundingMode.HALF_UP);
                final BigDecimal amount=currencyCalculator.calculate(value,fromCurrency,toCurrency)
                        .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                totalAmountLines = totalAmountLines.add(amount);
                invoiceLines.add(new InvoiceLine(invoiceLines.size() + 1, quantity, start, end,
                        product, price, Integer.valueOf(priceList), amount));
            }
        }
        return invoiceLines;
    }
}
