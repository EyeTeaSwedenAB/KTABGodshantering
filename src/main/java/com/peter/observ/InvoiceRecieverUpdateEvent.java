package com.peter.observ;

import java.util.List;

/**
 * Created by andreajacobsson on 2016-08-28.
 */
public class InvoiceRecieverUpdateEvent implements UpdateEvent<List<String>> {

    private List<String> invoiceRecievers;

    public InvoiceRecieverUpdateEvent(List<String> invoiceRecievers){
        this.invoiceRecievers = invoiceRecievers;
    }

    @Override
    public List<String> getObject() {
        return invoiceRecievers;
    }
}
