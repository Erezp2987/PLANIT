package com.erez_p.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cordinates implements Serializable {
    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
