package com.example.SpringBatchExample.generators;
import com.example.SpringBatchExample.QuantityPricePeriod;
import com.example.SpringBatchExample.models.InvoiceLine;
import com.example.SpringBatchExample.models.User;
import org.springframework.stereotype.Service;

@Service
public interface LineGenerator {

    InvoiceLine generateInvoiceLine(int index, QuantityPricePeriod qpp, User user);
}
