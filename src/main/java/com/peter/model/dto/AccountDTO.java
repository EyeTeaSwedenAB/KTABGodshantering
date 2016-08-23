package com.peter.model.dto;

/**
 * Created by andreajacobsson on 2016-08-23.
 */
public class AccountDTO {

    private int id;
    private String name;

    public AccountDTO(int id, String name) {
        this.id = id;
        this.name = name;
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


    @Override
    public String toString() {
        return "ACCOUNT[id: " + id + ", name: " + name + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDTO)) return false;

        AccountDTO accountDTO = (AccountDTO) o;

        if (id != accountDTO.id) return false;
        return name != null ? name.equals(accountDTO.name) : accountDTO.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
