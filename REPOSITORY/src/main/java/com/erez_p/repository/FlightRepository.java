package com.erez_p.repository;

import android.app.Application;

import com.erez_p.model.FinalFlight;
import com.erez_p.model.FinalHotel;
import com.erez_p.model.Flight;
import com.erez_p.model.Flights;
import com.erez_p.repository.BASE.BaseRepository;
import com.google.firebase.firestore.Query;

public class FlightRepository extends BaseRepository<FinalFlight, Flights> {
    public FlightRepository(Application application) {
        super(FinalFlight.class, Flights.class, application);
    }

    @Override
    protected Query getQueryForExist(FinalFlight entity) {
        return getCollection()
                .whereEqualTo("flightNumber", entity.getFlightNumber());
    }
}
