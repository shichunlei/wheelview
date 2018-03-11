package com.chingtech.model;

public class County {

    private long   id;
    private long   city_id;
    private String name;
    private String zipcode;

    public County() {
    }

    public County(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public County(long id, String name, String zipcode) {
        this.id = id;
        this.name = name;
        this.zipcode = zipcode;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCityId() {
        return city_id;
    }

    public void setCityId(int city_id) {
        this.city_id = city_id;
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