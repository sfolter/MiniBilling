package com.github.methodia.minibilling.saveinvoice;

import com.github.methodia.minibilling.entity.Invoice;
import com.github.methodia.minibilling.readers.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SaveInvoiceInDataBase implements SaveInvoice {
       Session session;

    public SaveInvoiceInDataBase(final Session session) {
        this.session = session;
    }

    @Override
    public void save(Invoice invoice) {


              session.persist(invoice);
//            session.save(invoice.getLines());
//            session.save(invoice.getTaxes());
//            session.save(invoice.getVat());


    }
}
