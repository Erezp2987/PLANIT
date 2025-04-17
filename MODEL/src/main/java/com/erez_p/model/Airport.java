package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Airport implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("time")
    private String time;

    // Getters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
