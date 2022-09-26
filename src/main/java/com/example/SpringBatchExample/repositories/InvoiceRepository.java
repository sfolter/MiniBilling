package com.example.SpringBatchExample.repositories;

import com.example.SpringBatchExample.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Override
    Invoice saveAndFlush(Invoice invoice);
}
