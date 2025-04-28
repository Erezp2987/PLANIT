package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;

import java.io.Serializable;

public class Activity extends BaseEntity implements Serializable {
    private String tripID;
    private String activityName;
    private double activityPrice;
    private String activityDate;
    private long activityTime;
    private long activityDuration;
    public Activity() {
    }

    public Activity(String tripID, String activityName, double activityPrice, String activityDate, long activityTime,long activityDuration) {
        this.tripID=tripID;
        this.activityName = activityName;
        this.activityPrice = activityPrice;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
        this.activityDuration=activityDuration;
    }
    public String getTripID() { return tripID; }

    public long getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(long activityDuration) {
        this.activityDuration = activityDuration;
    }

    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }

    public double getActivityPrice() { return activityPrice; }
    public void setActivityPrice(double activityPrice) { this.activityPrice = activityPrice; }

    public String getActivityDate() { return activityDate; }
    public void setActivityDate(String activityDate) { this.activityDate = activityDate; }

    public long getActivityTime() { return activityTime; }
    public void setActivityTime(long activityTime) { this.activityTime = activityTime; }
}
