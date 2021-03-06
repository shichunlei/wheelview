package com.chingtech.model;

public class Street {

    private long   id;
    private long   county_id;
    private String name;
    private String zipcode;

    public Street() {
    }

    public Street(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Street(long id, String name, String zipcode) {
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

    public long getCountyId() {
        return county_id;
    }

    public void setCountyId(long county_id) {
        this.county_id = county_id;
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