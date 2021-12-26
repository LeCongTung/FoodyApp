package com.example.foodyapp.units;

public class milktea {
    private int id;
    private String name;
    private int price;
    private String type;
    private String location;
    private String description;
    private byte[] image;

    public milktea(int id, String name, int price, String type, String location, String description, byte[] image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.location = location;
        this.description = description;
        this.image = image;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
