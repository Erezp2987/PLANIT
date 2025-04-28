package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;

public class FinalHotel extends BaseEntity {
    private String name;
    private String price;
    private String dateDeparture;
    private String dateReturn;
    private String link;
    private Double longtitude;
    private Double latitude;
    private String description;
    private String tripId;
    public FinalHotel(String name, String price, String link, Double longtitude, Double latitude, String dateDeparture, String dateReturn, String tripId) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.dateDeparture = dateDeparture;
        this.dateReturn = dateReturn;
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDateReturn() {
        return dateReturn;
    }

    public String getDateDeparture() {
        return dateDeparture;
    }

    public String getLink() {
        return link;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getDescription() {
        return description;
    }

    public String getTripId() {
        return tripId;
    }
}
