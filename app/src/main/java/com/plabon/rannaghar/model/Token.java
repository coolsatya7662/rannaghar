package com.plabon.rannaghar.model;

public class Token {
    String token;
    String zip;
    String search;

    public Token(String token,String zip) {
        this.token = token;
        this.zip = zip;
    }

    public Token(String token,String zip,String search) {
        this.token = token;
        this.zip = zip;
        this.search = search;
    }

    public Token(String token) {
        this.token = token;
    }

    public String getZip() {
        return zip;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
