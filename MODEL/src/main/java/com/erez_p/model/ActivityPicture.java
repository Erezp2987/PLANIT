package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;

import java.io.Serializable;

public class ActivityPicture extends BaseEntity implements Serializable {
    private String picture;
    private String description;
    private String activityId;
    public ActivityPicture(String picture, String description, String activityId)
    {
        this.picture = picture;
        this.description = description;
        this.activityId = activityId;
    }
    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getActivityId() { return activityId; }
    public void setActivityId(String activityId) { this.activityId = activityId; }
}
