package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HotelItem extends BaseEntity implements Serializable {
    private String name;
    private String price;
    private String link;
    private Double longtitude;
    private Double latitude;
    private String description;
    private boolean isSpecificHotel;// New flag to indicate if it's a specific hotel

    // Constructor to initialize all fields
    public HotelItem(String name, String price, String description,String link, boolean isSpecificHotel) {
        this.name = name;
        this.price = price;
        this.link = link;
        this.description = description;
        this.isSpecificHotel = isSpecificHotel;
    }
    public HotelItem(String name, String price, String link) {
        this.name = name;
        this.link = link;
        this.price = price;
    }
    public HotelItem(String name, String price, String link,Double longtitude,Double latitude) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.longtitude=longtitude;
        this.latitude=latitude;
    }
    public HotelItem(String name, String link,Double longtitude,Double latitude) {
        this.name = name;
        this.link = link;
        this.longtitude=longtitude;
        this.latitude=latitude;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSpecificHotel() {
        return isSpecificHotel;
    }
}
