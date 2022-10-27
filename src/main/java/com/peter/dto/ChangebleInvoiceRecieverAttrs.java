package com.peter.dto;

/**
 * Created by andreajacobsson on 2016-09-28.
 */
public class ChangebleInvoiceRecieverAttrs {

    private final String address;
    private final String contact;
    private final String phone;
    private final String email;

    public ChangebleInvoiceRecieverAttrs(String address, String contact, String phone, String email) {
        this.address = address;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
