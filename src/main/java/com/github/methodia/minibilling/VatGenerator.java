package com.github.methodia.minibilling;

public interface VatGenerator {
    Vat generateVat(InvoiceLine invoiceLine);
}
