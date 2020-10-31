package com.avarice.webservice;

public class Student {
    private int id;
    private String name;
    private int born;
    private String address;

    public Student() {
    }

    public Student(int id, String name, int born, String address) {
        this.id = id;
        this.name = name;
        this.born = born;
        this.address = address;
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

    public int getBorn() {
        return born;
    }

    public void setBorn(int born) {
        this.born = born;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
