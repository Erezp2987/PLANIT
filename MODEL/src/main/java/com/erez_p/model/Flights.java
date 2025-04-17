package com.erez_p.model;

import com.erez_p.model.BASE.BaseList;

import java.io.Serializable;

public class Flights extends BaseList<Flight,Flights> implements Serializable
{
    private String tripID;
    private String flightID;
    public Flights(String tripID, String flightID)
    {
        this.tripID = tripID; this.flightID = flightID;
    }
    public String getTripID() { return tripID; }
    public String getFlightID() { return flightID; }
}