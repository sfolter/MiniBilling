package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.InvoiceLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class VatGeneratorTest {
    @Test
    void generateVats(){
        final List<VatPercentages> vatPercentages=new ArrayList<>();
        vatPercentages.add(new VatPercentages(new BigDecimal(60),new BigDecimal("40")));
        vatPercentages.add(new VatPercentages(new BigDecimal(40),new BigDecimal("20")));


        final LocalDateTime ldtStart = LocalDateTime.of(2021, 3, 15, 13, 23, 12);
        final LocalDateTime ldtEnd = LocalDateTime.of(2021, 3, 17, 15, 45, 28);
        final List<InvoiceLine> invoiceLineList=new ArrayList<>();
        invoiceLineList.add(new InvoiceLine(1,new BigDecimal("150"),ldtStart,ldtEnd,"gas",
                new BigDecimal("2"),1,new BigDecimal("2")));

//        invoiceLineList.add(new InvoiceLine(2,new BigDecimal("30"),))
    }
}
