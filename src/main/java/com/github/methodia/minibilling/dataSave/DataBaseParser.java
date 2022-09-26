package com.github.methodia.minibilling.dataSave;

import com.github.methodia.minibilling.entityClasses.Invoice;
import org.hibernate.Session;

public class DataBaseParser implements SaveData {

    private final Invoice invoice;
    private final Session session;

    public DataBaseParser(final Invoice invoice,final Session session) {
        this.invoice = invoice;
        this.session = session;
    }

    @Override
    public void save() {
        session.persist(invoice);

    }
}
