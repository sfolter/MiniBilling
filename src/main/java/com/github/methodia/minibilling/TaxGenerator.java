package com.github.methodia.minibilling;

interface TaxGenerator {

    Tax generateTaxes(InvoiceLine invoiceLine);
}
