package com.erez_p.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OtherFlight implements Serializable {
    @SerializedName("flights")
    private List<Flight> flights;

    @SerializedName("price")
    private int price; // 💰 Correctly map the price here

    public List<Flight> getFlights() {
        return flights;
    }

    public int getPrice() {
        return price;
    }
}
