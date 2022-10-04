package com.example.SpringBatchExample.repositories;

import com.example.SpringBatchExample.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Override
    Invoice saveAndFlush(Invoice invoice);

    @Override
    List<Invoice> findAll();

    Invoice findByReference(int reference);


}
