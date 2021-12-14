package com.example.foodyapp;

public class MilkTea {
    private int id;
    private String name;
    private int price;
    private String location;

    public MilkTea(int id, String name, int price, String location) {
        this.id = id;
        this.name = name;
//        this.price = price;
        this.location = location;
    }

    public MilkTea(int id, String name, String location) {
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

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
