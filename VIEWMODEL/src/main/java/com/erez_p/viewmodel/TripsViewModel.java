package com.erez_p.viewmodel;

import android.app.Application;

import com.erez_p.model.Trip;
import com.erez_p.model.Trips;
import com.erez_p.repository.BASE.BaseRepository;
import com.erez_p.repository.TripsRepository;
import com.erez_p.viewmodel.BASE.BaseViewModel;
import com.google.firebase.firestore.Query;

public class TripsViewModel extends BaseViewModel<Trip, Trips> {
    private TripsRepository repository;

    public TripsViewModel(Application application) {
        super(Trip.class, Trips.class, application);
    }

    @Override
    protected BaseRepository<Trip, Trips> createRepository(Application application) {
        repository = new TripsRepository(application);
        return repository;
    }
    // Optional: Add method to get trips by specific criteria
    public void getTripsByUserID(String userID) {
        getAll(repository.getCollection().whereEqualTo("userId", userID));
    }
    public void getTripsByTripID(String tripID) {
        get(repository.getCollection().whereEqualTo("tripId", tripID));
    }
}