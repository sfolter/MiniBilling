package com.example.SpringBatchExample.generators;

import com.example.SpringBatchExample.models.Invoice;
import com.example.SpringBatchExample.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;


    public List<Invoice> listAll() {
        List<Invoice> all = invoiceRepository.findAll();
        System.out.println(all);
        return all;
    }

    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoice(int refNumber) throws Exception {

        return invoiceRepository.findByReference(refNumber);


    }


}
