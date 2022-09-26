package com.example.SpringBatchExample.generators;

import com.example.SpringBatchExample.models.InvoiceLine;
import com.example.SpringBatchExample.models.Tax;
import org.springframework.stereotype.Service;

@Service
interface TaxGenerator {

    Tax generateTaxes(InvoiceLine invoiceLine);
}
