package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;

import java.io.Serializable;

public class Trip extends BaseEntity implements Serializable {
    private String name;
    private long dateDeparture;
    private long dateReturn;
    private String flightId;
    private String hotelId;

    public Trip(String name, long dateDeparture, long dateReturn) {
        this.name = name;
        this.dateDeparture = dateDeparture;
        this.dateReturn = dateReturn;
    }
    public Trip(String name,long dateDeparture, long dateReturn,String flightId,String hotelId)
    {
        this.name = name;
        this.dateDeparture = dateDeparture;
        this.dateReturn = dateReturn;
        this.flightId=flightId;
        this.hotelId=hotelId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public long getDateDeparture() { return dateDeparture; }
    public void setDateDeparture(long dateDeparture) { this.dateDeparture = dateDeparture; }

    public long getDateReturn() { return dateReturn; }
    public void setDateReturn(long dateReturn) { this.dateReturn = dateReturn; }
}
