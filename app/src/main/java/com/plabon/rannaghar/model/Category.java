package com.plabon.rannaghar.model;

public class Category {
    String id;
    String categry;
    String cateimg;
    String token;
    String zip;

    public Category(String id, String categry, String cateimg) {
        this.id = id;
        this.categry = categry;
        this.cateimg = cateimg;
    }

    public Category(String categry, String token, String zip,String bl) {
        this.categry = categry;
        this.token = token;
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategry() {
        return categry;
    }

    public void setCategry(String categry) {
        this.categry = categry;
    }

    public String getCateimg() {
        return cateimg;
    }

    public void setCateimg(String cateimg) {
        this.cateimg = cateimg;
    }
}
