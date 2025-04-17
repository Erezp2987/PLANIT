package com.erez_p.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HotelResponse implements Serializable {
    @SerializedName("properties")
    private List<Property> properties;

    public List<Property> getProperties() {
        return properties;
    }

    @SerializedName("type")
    private String type; // Will exist if it's a specific hotel

    @SerializedName("name")
    private String name; // Specific hotel fields

    @SerializedName("description")
    private String description;

    @SerializedName("link")
    private String link;


    @SerializedName("rate_per_night")
    private Rate ratePerNight;

    @SerializedName("gps_coordinates")
    private Cordinates cordinates;
    // Getters
    public String getType() {
        return type;
    }

    public Cordinates getCordinates() {
        return cordinates;
    }

    public String getName() {
        return name;
    }
    public Rate getRatePerNight() {
        return ratePerNight;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public boolean isSpecificHotel() {
        return "hotel".equalsIgnoreCase(type);
    }
    public static class Rate {
        @SerializedName("lowest")
        private String lowest;

        public String getLowest() {
            return lowest;
        }
    }
}
