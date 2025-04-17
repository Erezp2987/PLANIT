package com.erez_p.model;

import com.erez_p.model.BASE.BaseList;

import java.io.Serializable;

public class Hotels extends BaseList<HotelItem,Hotels> implements Serializable {
    private String hotelID;
    private String tripID;
    public Hotels(String hotelID, String tripID) {
        this.hotelID = hotelID;
        this.tripID = tripID;
    }
    public String getHotelID() { return hotelID; }
    public String getTripID() { return tripID; }
}
