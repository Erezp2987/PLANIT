package com.erez_p.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RatePerNight implements Serializable {
    @SerializedName("lowest")
    private String lowest;

    public String getLowest() {
        return lowest;
    }
}
