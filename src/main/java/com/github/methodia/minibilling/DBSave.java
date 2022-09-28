package com.github.methodia.minibilling;

import org.hibernate.Session;

public class DBSave implements SaveData {

    private final Session session;
    private final Invoice invoice;

    public DBSave(final Session session, final Invoice invoice) {
        this.session = session;
        this.invoice = invoice;
    }

    @Override
    public void save() {
        session.beginTransaction();
        session.persist(invoice);
        invoice.getLines().forEach(session::save);
        invoice.getTaxesLines().forEach(session::save);
        invoice.getVatsLines().forEach(session::save);
        session.getTransaction().commit();
    }
}
