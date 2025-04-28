package com.erez_p.viewmodel;

import android.app.Application;

import com.erez_p.model.FinalFlight;
import com.erez_p.model.Flight;
import com.erez_p.model.Flights;
import com.erez_p.repository.BASE.BaseRepository;
import com.erez_p.repository.FlightRepository;
import com.erez_p.viewmodel.BASE.BaseViewModel;
import com.google.firebase.firestore.Query;

public class FlightViewModel extends BaseViewModel<FinalFlight, Flights> {
    private FlightRepository repository;

    public FlightViewModel(Application application) {
        super(FinalFlight.class, Flights.class, application);
    }

    @Override
    protected BaseRepository<FinalFlight, Flights> createRepository(Application application) {
        repository = new FlightRepository(application);
        return repository;
    }
    // Optional: Add method to get flights by specific criteria
    public void getFlightsByFlightNumber(String flightNumber) {
        get(repository.getCollection().whereEqualTo("flightNumber", flightNumber));
    }
    public void getFlightByTripID(String tripID) {
        getAll(repository.getCollection().whereEqualTo("tripId", tripID));
    }
}
