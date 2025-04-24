package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HotelItem extends BaseEntity implements Serializable {
    private String name;
    private String price;
    private String dateDeparture;
    private String dateReturn;
    private String link;
    private Double longtitude;
    private Double latitude;
    private String description;

    public HotelItem(String name, String price, String link,Double longtitude,Double latitude,String dateDeparture, String dateReturn) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.longtitude=longtitude;
        this.latitude=latitude;
        this.dateDeparture = dateDeparture;
        this.dateReturn = dateReturn;
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
    public HotelItem(String name, String link,Double longtitude,Double latitude,String dateDeparture, String dateReturn) {
        this.name = name;
        this.link = link;
        this.longtitude=longtitude;
        this.latitude=latitude;
        this.dateDeparture = dateDeparture;
        this.dateReturn = dateReturn;
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
    public String getDateDeparture() {
        return dateDeparture;
    }
    public String getDateReturn() {
        return dateReturn;
    }

    public String getDescription() {
        return description;
    }

}
