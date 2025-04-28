package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;

import java.io.Serializable;

public class Trip extends BaseEntity implements Serializable {
    private String name;
    private String dateDeparture;
    private String dateReturn;
    private String userId;

    public Trip() {
    }
    public Trip(String name, String dateDeparture, String dateReturn,String userId) {
        this.name = name;
        this.dateDeparture = dateDeparture;
        this.dateReturn = dateReturn;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDateDeparture() { return dateDeparture; }
    public void setDateDeparture(String dateDeparture) { this.dateDeparture = dateDeparture; }

    public String getDateReturn() { return dateReturn; }
    public void setDateReturn(String dateReturn) { this.dateReturn = dateReturn; }
}
