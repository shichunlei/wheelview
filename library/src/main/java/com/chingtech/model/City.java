package com.chingtech.model;

public class City {

    private long   id;
    private long   province_id;
    private String name;
    private String zipcode;

    public City() {
    }

    public City(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public City(long id, String name, String zipcode) {
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

    public long getProvinceId() {
        return province_id;
    }

    public void setProvinceId(long province_id) {
        this.province_id = province_id;
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