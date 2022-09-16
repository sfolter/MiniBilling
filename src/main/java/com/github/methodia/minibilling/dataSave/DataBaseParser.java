package com.github.methodia.minibilling.dataSave;

import com.github.methodia.minibilling.entityClasses.Invoice;
import org.hibernate.Session;

public class DataBaseParser implements SaveData {

    private final Invoice invoice;
    private final Session session;

    public DataBaseParser(Invoice invoice, Session session) {
        this.invoice = invoice;
        this.session = session;
    }

    @Override
    public void save() {
        session.persist(invoice);
        //            invoice.getLines().stream().forEach(session.persist());
        invoice.getLines().forEach(session::save);
        invoice.getTaxes().forEach(session::save);
        invoice.getVat().forEach(session::save);
    }
}
