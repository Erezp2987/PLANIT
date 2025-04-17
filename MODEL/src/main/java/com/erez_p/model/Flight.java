package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Flight extends BaseEntity implements Serializable {
    @SerializedName("departure_airport")
    private Airport departureAirport;

    @SerializedName("arrival_airport")
    private Airport arrivalAirport;

    @SerializedName("duration")
    private int duration; // Duration in minutes

    @SerializedName("airplane")
    private String airplane;

    @SerializedName("airline")
    private String airline;

    @SerializedName("airline_logo")
    private String airlineLogo;

    @SerializedName("travel_class")
    private String travelClass;

    @SerializedName("flight_number")
    private String flightNumber;

    @SerializedName("legroom")
    private String legroom;

    @SerializedName("extensions")
    private List<String> extensions; // List of additional flight details

    @SerializedName("price")
    private int price;  // 💰 Add this field to get the price

    // Getters
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public int getDuration() {
        return duration;
    }

    public String getAirplane() {
        return airplane;
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

    public List<String> getExtensions() {
        return extensions;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}

