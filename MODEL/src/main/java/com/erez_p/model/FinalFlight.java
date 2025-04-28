package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;

public class FinalFlight extends BaseEntity {
    private String departureAirport;
    private String arrivalAirport;
    private int duration;
    private String airPlane;
    private String airline;
    private String airlineLogo;
    private String travelClass;
    private String flightNumber;
    private String legroom;
    private int price;
    private String tripId;
    private String dateDeparture;
    public FinalFlight() {
    }
    public FinalFlight(String departureAirport, String arrivalAirport, int duration,
                    String airPlane, String airline, String airlineLogo,
                    String travelClass, String flightNumber, String legroom,
                    int price, String tripId, String dateDeparture) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.duration = duration;
        this.airPlane = airPlane;
        this.airline = airline;
        this.airlineLogo = airlineLogo;
        this.travelClass = travelClass;
        this.flightNumber = flightNumber;
        this.legroom = legroom;
        this.price = price;
        this.tripId = tripId;
        this.dateDeparture = dateDeparture;
    }

    public String getDateDeparture() {
        return dateDeparture;
    }


    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public int getDuration() {
        return duration;
    }

    public String getAirPlane() {
        return airPlane;
    }

    public String getAirline() {
        return airline;
    }

    public String getAirlineLogo() {
        return airlineLogo;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getLegroom() {
        return legroom;
    }

    public int getPrice() {
        return price;
    }

    public String getTripId() {
        return tripId;
    }
}
