package com.example.omniandroid;

public class Payload {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String date;
    private String value;
    private String id;


    public Payload(String date, String value, String id) {
        this.date = date;
        this.value = value;
        this.id = id;
    }
}
