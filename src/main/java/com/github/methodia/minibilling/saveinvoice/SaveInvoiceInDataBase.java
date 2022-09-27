package com.github.methodia.minibilling.saveinvoice;

import com.github.methodia.minibilling.entity.Invoice;
import org.hibernate.Session;

public class SaveInvoiceInDataBase implements SaveInvoice {

    Session session;

    public SaveInvoiceInDataBase(final Session session) {
        this.session = session;
    }

    @Override
    public void save(Invoice invoice) {
        //session.beginTransaction();
        session.persist(invoice);
        //session.getTransaction().commit();
    }
}
