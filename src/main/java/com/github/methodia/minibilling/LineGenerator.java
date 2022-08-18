package com.github.methodia.minibilling;

public interface LineGenerator {
    InvoiceLine generateInvoiceLine(int index, QuantityPricePeriod qpp, User user);
}
