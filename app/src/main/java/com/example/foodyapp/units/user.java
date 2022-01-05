package com.example.foodyapp.units;

public class user {
    private int id;
    private String name;
    private String phonenumber;
    private String user;
    private String pass;
    private String location;

    public user(int id, String name, String phonenumber, String user, String pass, String location) {
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
        this.user = user;
        this.pass = pass;
        this.location = location;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
