package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entity.Invoice;
import com.github.methodia.minibilling.readers.SessionFactoryUtil;
import org.hibernate.Session;

public class SaveInvoiceInDataBase implements SaveInvoice {

    @Override
    public void save(Invoice invoice) {
        try (final Session session = SessionFactoryUtil.getSessionFactory()) {
            session.persist(invoice);
            session.persist(invoice.getLines());
            session.persist(invoice.getTaxes());
            session.persist(invoice.getVat());
        }
    }
}
