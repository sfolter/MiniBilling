package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.Invoice;
import com.github.methodia.minibilling.entityClasses.InvoiceLine;
import com.github.methodia.minibilling.entityClasses.Vat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceTest {
    @Test
    void invoiceGetters(){
        final List<InvoiceLine> invoiceLineList=new ArrayList<>();
        invoiceLineList.add(new InvoiceLine(1,new BigDecimal("40"),
                LocalDateTime.of(2022,1,1,1,1,1),
                LocalDateTime.of(2022,2,2,2,2,2),"gas",new BigDecimal("2.50"),
                2,new BigDecimal("3")));


        final List<Integer>linesList=new ArrayList<>();
        linesList.add(1);
        linesList.add(2);

        final List<Vat>vatList=new ArrayList<>();
        vatList.add(new Vat(2,linesList,new BigDecimal("60"),"30",new BigDecimal("3")));
        final Invoice invoice = new Invoice(10001, "Milcho Georgiev Georgiev", "2", new BigDecimal("100"),
                new BigDecimal("120"), invoiceLineList, vatList, new ArrayList<>());
        Assertions.assertEquals( "Milcho Georgiev Georgiev", invoice.getConsumer(),
                "Invoice getConsumer method returns incorrect information");
        Assertions.assertEquals(10001,invoice.getDocNumber(),"Invoice getDocNumber returns incorrect information.");
        Assertions.assertEquals(invoiceLineList,invoice.getLines(),"Invoice getLines method returns incorrect information.");
        Assertions.assertEquals( "2",invoice.getReference(),"Invoice getReference method returns incorrect information.");
        Assertions.assertEquals(new BigDecimal("100"),invoice.getTotalAmount(),"Invoice getTotalAmount method returns incorrect information.");
        Assertions.assertEquals(new BigDecimal("120"), invoice.getTotalAmountWithVat(),"Invoice getTotalAmountWithVat returns incorrect information.");

    }
}
