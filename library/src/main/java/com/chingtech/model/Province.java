package com.chingtech.model;

public class Province {

    private long   id;
    private String name;
    private String zipcode;

    public Province() {
    }

    public Province(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Province(long id, String name, String zipcode) {
        this.id = id;
        this.name = name;
        this.zipcode = zipcode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}