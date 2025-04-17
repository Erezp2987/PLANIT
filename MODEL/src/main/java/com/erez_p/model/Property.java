package com.erez_p.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Property implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("rate_per_night")
    private RatePerNight ratePerNight;

    @SerializedName("link")
    private String link;

    @SerializedName("description")
    private String description;
    @SerializedName("gps_coordinates")
    private Cordinates cordinates;

    // Constructor
    public Property(String name, RatePerNight ratePerNight, String link, String description) {
        this.name = name;
        this.ratePerNight = ratePerNight;
        this.link = link;
        this.description = description;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Cordinates getCordinates() {
        return cordinates;
    }

    public RatePerNight getRatePerNight() {
        return ratePerNight;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }
}
