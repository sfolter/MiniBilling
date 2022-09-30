package com.example.SpringBatchExample.generators;

import com.example.SpringBatchExample.models.InvoiceLine;
import com.example.SpringBatchExample.models.Tax;


interface TaxGenerator {

    Tax generateTaxes(InvoiceLine invoiceLine);
}
