package com.erez_p.repository;

import android.app.Application;

import com.erez_p.model.Trip;
import com.erez_p.model.Trips;
import com.erez_p.repository.BASE.BaseRepository;
import com.google.firebase.firestore.Query;

public class TripsRepository extends BaseRepository<Trip, Trips> {
    public TripsRepository(Application application) {
        super(Trip.class, Trips.class, application);
    }

    @Override
    protected Query getQueryForExist(Trip entity) {
        // You might want to modify this based on your specific requirements
        return getCollection()
                .whereEqualTo("name", entity.getName())
                .whereEqualTo("dateDeparture", entity.getDateDeparture())
                .whereEqualTo("dateReturn", entity.getDateReturn());
    }
}