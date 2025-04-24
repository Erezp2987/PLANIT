package com.erez_p.repository;

import android.app.Application;

import com.erez_p.model.HotelItem;
import com.erez_p.model.Hotels;
import com.erez_p.repository.BASE.BaseRepository;
import com.google.firebase.firestore.Query;

public class HotelRepository extends BaseRepository<HotelItem, Hotels> {
    public HotelRepository(Application application) {
        super(HotelItem.class, Hotels.class, application);
    }

    @Override
    protected Query getQueryForExist(HotelItem entity) {
        return getCollection()
                .whereEqualTo("hotelName", entity.getName())
                .whereEqualTo("hotelDepartureDate", entity.getDateDeparture())
                .whereEqualTo("hotelReturnDate", entity.getDateReturn());
    }
}

