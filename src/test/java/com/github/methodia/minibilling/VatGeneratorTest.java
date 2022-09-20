package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entity.InvoiceLine;
import com.github.methodia.minibilling.entity.Tax;
import com.github.methodia.minibilling.entity.Vat;
import com.github.methodia.minibilling.mainlogic.TaxStandingGenerator;
import com.github.methodia.minibilling.mainlogic.VatGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



class VatGeneratorTest {

    @Test
    void vatForLine() {
        final InvoiceLine invoiceLine = getInvoiceLine();
        final List<InvoiceLine> invoiceLines = new ArrayList<>();
        invoiceLines.add(invoiceLine);
        final VatGenerator vatGenerator = new VatGenerator(ExampleInputInformation.vatPercentages());
        final List<Vat> vats = vatGenerator.generate(invoiceLines, Collections.emptyList());
        Assertions.assertEquals(invoiceLines.size() * 2, vats.size(),
                "Count of vats is different.");
        Assertions.assertEquals(new BigDecimal("19.2"), vats.get(0).getAmount(),
                "Amount of vat is not correct.");
        Assertions.assertEquals(new BigDecimal("6.4"), vats.get(1).getAmount(),
                "Amount of vat is not correct.");

    }

    @Test
    void vatForTaxStanding() {
        final List<InvoiceLine> invoiceLines = new ArrayList<>();
        invoiceLines.add(getInvoiceLine());
        final CurrencyCalculator currencyCalculator = (a, f, t) -> a;
        final TaxStandingGenerator taxStandingGenerator = new TaxStandingGenerator(invoiceLines, currencyCalculator,
                "BGN", "BGN");
        final List<Tax> taxes = taxStandingGenerator.generate();
        final VatGenerator vatGenerator = new VatGenerator(ExampleInputInformation.vatPercentages());
        final List<Vat> vats = vatGenerator.generate(Collections.emptyList(), taxes);
        Assertions.assertEquals(taxes.size(), vats.size(),
                "Count of vats is different.");
        Assertions.assertEquals(new BigDecimal("9.92"), vats.get(0).getAmount(),
                "Amount of vat is not correct.");
    }

    @Test
    void vatForLineAndTax() {
        final List<InvoiceLine> invoiceLines = new ArrayList<>();
        invoiceLines.add(getInvoiceLine());
        final CurrencyCalculator currencyCalculator = (a, f, t) -> a;
        final TaxStandingGenerator taxStandingGenerator = new TaxStandingGenerator(invoiceLines, currencyCalculator,
                "BGN", "BGN");
        final List<Tax> taxes = taxStandingGenerator.generate();
        final VatGenerator vatGenerator = new VatGenerator(ExampleInputInformation.vatPercentages());
        final List<Vat> vats = vatGenerator.generate(invoiceLines, taxes);
        Assertions.assertEquals(invoiceLines.size() * 2 + taxes.size(), vats.size(),
                "Count of vats is different.");
        Assertions.assertEquals(new BigDecimal("19.2"), vats.get(0).getAmount(),
                "Amount of vat is not correct.");
        Assertions.assertEquals(new BigDecimal("6.4"), vats.get(1).getAmount(),
                "Amount of vat is not correct.");
        Assertions.assertEquals(new BigDecimal("9.92"), vats.get(2).getAmount(),
                "Amount of vat is not correct.");
    }

    InvoiceLine getInvoiceLine() {
        return new InvoiceLine(1, new BigDecimal("100"),
                LocalDateTime.of(2021, Month.APRIL, 3, 20, 20, 20),
                LocalDateTime.of(2021, Month.MAY, 3, 20, 20, 20),
                "gas", new BigDecimal("1.6"), 1, new BigDecimal("160"));
    }
}