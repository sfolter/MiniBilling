package com.github.methodia.minibilling;

import java.util.List;

public interface VatGenerator {

    List<Vat> generateVat(InvoiceLine invoiceLine);
}
