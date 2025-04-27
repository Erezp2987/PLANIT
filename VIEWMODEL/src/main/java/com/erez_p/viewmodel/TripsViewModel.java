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

    public void getAll() {
        getAll(Query.Direction.ASCENDING);
    }

    public void getAll(Query.Direction direction) {
        getAll(repository.getCollection().orderBy("name", direction));
    }

    // Optional: Add method to get trips by specific criteria
    public void getTripsByUserID(String userID) {
        get(repository.getCollection().whereEqualTo("userId", userID));
    }
}