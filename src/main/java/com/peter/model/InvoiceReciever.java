package com.peter.model;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class InvoiceReciever {

    private int id;
    private String name;
    private String adress;
    private String contact;
    private String phone;

    public InvoiceReciever(int id, String name, String adress, String contact, String phone) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.contact = contact;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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
}
