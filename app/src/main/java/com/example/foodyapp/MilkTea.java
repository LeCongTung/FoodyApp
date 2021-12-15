package com.example.foodyapp;

public class MilkTea {
    private int idMT;
    private String nameMT;
    private int priceMT;
    private String locationMT;

    public MilkTea(int idMT, String nameMT, int priceMT, String locationMT) {
        this.idMT = idMT;
        this.nameMT = nameMT;
        this.priceMT = priceMT;
        this.locationMT = locationMT;
    }

    public String getLocationMT() {
        return locationMT;
    }

    public void setLocationMT(String locationMT) {
        this.locationMT = locationMT;
    }


    public int getIdMT() {
        return idMT;
    }

    public void setIdMT(int idMT) {
        this.idMT = idMT;
    }

    public String getNameMT() {
        return nameMT;
    }

    public void setNameMT(String nameMT) {
        this.nameMT = nameMT;
    }

    public int getPriceMT() {
        return priceMT;
    }

    public void setPriceMT(int priceMT) {
        this.priceMT = priceMT;
    }




}
