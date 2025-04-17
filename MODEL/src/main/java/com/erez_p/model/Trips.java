package com.erez_p.model;

import com.erez_p.model.BASE.BaseList;

import java.io.Serializable;

public class Trips extends BaseList<Trip,Trips> implements Serializable {
    private String userID;
    private String tripID;
    public Trips(String userID, String tripID) {
        this.userID = userID;
        this.tripID = tripID;
    }
    public String getUserID() { return userID; }
    public String getTripID() { return tripID; }
}
