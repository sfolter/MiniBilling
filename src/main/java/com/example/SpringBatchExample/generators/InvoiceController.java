package com.example.SpringBatchExample.generators;

import com.example.SpringBatchExample.models.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("invoice")
    public List<Invoice> list() {
        List<Invoice> invoiceList = invoiceService.listAll();
        return invoiceList;
    }


    @GetMapping(path = "{ref_number}")
    public Invoice getInvoice(@PathVariable("ref_number") int refNumber) throws Exception {
        return invoiceService.getInvoice(refNumber);
    }
}
