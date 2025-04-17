package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;

import java.io.Serializable;

public class HotelRoom extends BaseEntity implements Serializable {
    private String hotelId;
    private String roomType;
    private int roomAmount;

    public HotelRoom(String hotelId, String roomType, int roomAmount) {
        this.hotelId = hotelId;
        this.roomType = roomType;
        this.roomAmount = roomAmount;
    }
    public String getHotelId() { return hotelId; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public int getRoomAmount() { return roomAmount; }
    public void setRoomAmount(int roomAmount) { this.roomAmount = roomAmount; }
}
