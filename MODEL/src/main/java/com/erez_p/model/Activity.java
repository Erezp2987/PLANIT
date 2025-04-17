package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;

import java.io.Serializable;

public class Activity extends BaseEntity implements Serializable {
    private String tripID;
    private String activityName;
    private double activityPrice;
    private String activityTicketUrl;
    private String activityLocation;
    private long activityDate;
    private long activityTime;

    public Activity(String tripID, String activityName, double activityPrice, String activityTicketUrl, String activityLocation, long activityDate, long activityTime) {
        this.tripID=tripID;
        this.activityName = activityName;
        this.activityPrice = activityPrice;
        this.activityTicketUrl = activityTicketUrl;
        this.activityLocation = activityLocation;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
    }
    public String getTripID() { return tripID; }

    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }

    public double getActivityPrice() { return activityPrice; }
    public void setActivityPrice(double activityPrice) { this.activityPrice = activityPrice; }

    public String getActivityTicketUrl() { return activityTicketUrl; }
    public void setActivityTicketUrl(String activityTicketUrl) { this.activityTicketUrl = activityTicketUrl; }

    public String getActivityLocation() { return activityLocation; }
    public void setActivityLocation(String activityLocation) { this.activityLocation = activityLocation; }

    public long getActivityDate() { return activityDate; }
    public void setActivityDate(long activityDate) { this.activityDate = activityDate; }

    public long getActivityTime() { return activityTime; }
    public void setActivityTime(long activityTime) { this.activityTime = activityTime; }
}
