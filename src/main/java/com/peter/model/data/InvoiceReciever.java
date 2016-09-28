package com.peter.model.data;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class InvoiceReciever {

    private int id;
    private String company;
    private String address;
    private String contact;
    private String phone;
    private String email;

    public InvoiceReciever() {
    }

    public InvoiceReciever(int id, String company, String address, String contact, String phone, String email) {
        this.id = id;
        this.company = company;
        this.address = address;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean hasRegisteredMail(){
        return this.email != "";
    }
    @Override
    public String toString() {
        return company;
    }
}
