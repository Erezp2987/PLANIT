package com.erez_p.repository;

import android.app.Application;

import com.erez_p.model.Flight;
import com.erez_p.model.Flights;
import com.erez_p.repository.BASE.BaseRepository;
import com.google.firebase.firestore.Query;

public class FlightRepository extends BaseRepository<Flight, Flights> {
    public FlightRepository(Application application) {
        super(Flight.class, Flights.class, application);
    }

    @Override
    protected Query getQueryForExist(Flight entity) {
        return getCollection()
                .whereEqualTo("flightNumber", entity.getFlightNumber());
    }
}
