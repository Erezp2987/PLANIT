package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;

public class TripPicture extends BaseEntity {
    private String tripId;
    private String pictureUrl;
    private String description;
    public TripPicture()
    {

    }
    public TripPicture(String tripId, String pictureUrl, String description) {
        this.tripId = tripId;
        this.pictureUrl = pictureUrl;
        this.description = description;
    }
    public String getTripId() {
        return tripId;
    }
    public String getPictureUrl() {
        return pictureUrl;
    }
    public String getDescription() {
        return description;
    }
}
