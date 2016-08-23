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


    public String toFullName() {
        return "INVOICERECIEVER[id: " + id + ", name: " + name + ", adress: " + adress + ", contact: " + contact
                + ", phone: " + phone + "]";
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceReciever)) return false;

        InvoiceReciever that = (InvoiceReciever) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (adress != null ? !adress.equals(that.adress) : that.adress != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        return phone != null ? phone.equals(that.phone) : that.phone == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (adress != null ? adress.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}
