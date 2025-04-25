package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;

import java.io.Serializable;

public class Trip extends BaseEntity implements Serializable {
    private String name;
    private String dateDeparture;
    private String dateReturn;
    private String flightDepartueId;
    private String flightReturnId;
    private String userId;
    private String hotelId;

    public Trip(String name, String dateDeparture, String dateReturn) {
        this.name = name;
        this.dateDeparture = dateDeparture;
        this.dateReturn = dateReturn;
    }
    public Trip(String name,String dateDeparture, String dateReturn,String flightDepartueId,String flightReturnId,String hotelId, String userId) {
        this.name = name;
        this.dateDeparture = dateDeparture;
        this.dateReturn = dateReturn;
        this.flightDepartueId=flightDepartueId;
        this.flightReturnId=flightReturnId;
        this.hotelId=hotelId;
        this.userId=userId;
    }

    public String getHotelId() {
        return hotelId;
    }


    public String getFlightDepartueId() {
        return flightDepartueId;
    }

    public String getFlightReturnId() {
        return flightReturnId;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDateDeparture() { return dateDeparture; }
    public void setDateDeparture(String dateDeparture) { this.dateDeparture = dateDeparture; }

    public String getUserId() {
        return userId;
    }

    public String getDateReturn() { return dateReturn; }
    public void setDateReturn(String dateReturn) { this.dateReturn = dateReturn; }
}
