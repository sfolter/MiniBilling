package com.example.SpringBatchExample.generators;

import com.example.SpringBatchExample.models.InvoiceLine;
import com.example.SpringBatchExample.models.Tax;
import com.example.SpringBatchExample.models.Vat;
import org.springframework.stereotype.Service;

import java.util.List;


public interface VatGenerator {

    List<Vat> generateVat(InvoiceLine invoiceLine, List<Tax> taxes);
}
