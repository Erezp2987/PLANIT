package com.erez_p.viewmodel;

import android.app.Application;

import com.erez_p.model.Flight;
import com.erez_p.model.Flights;
import com.erez_p.repository.BASE.BaseRepository;
import com.erez_p.repository.FlightRepository;
import com.erez_p.viewmodel.BASE.BaseViewModel;
import com.google.firebase.firestore.Query;

public class FlightViewModel extends BaseViewModel<Flight, Flights> {
    private FlightRepository repository;

    public FlightViewModel(Application application) {
        super(Flight.class, Flights.class, application);
    }

    @Override
    protected BaseRepository<Flight, Flights> createRepository(Application application) {
        repository = new FlightRepository(application);
        return repository;
    }

    public void getAll() {
        getAll(Query.Direction.ASCENDING);
    }

    public void getAll(Query.Direction direction) {
        getAll(repository.getCollection().orderBy("flightNumber", direction));
    }

    // Optional: Add method to get flights by specific criteria
    public void getFlightsByFlightNumber(String flightNumber) {
        get(repository.getCollection().whereEqualTo("flightNumber", flightNumber));
    }
}
