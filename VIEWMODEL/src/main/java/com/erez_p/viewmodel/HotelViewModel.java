package com.erez_p.viewmodel;

import android.app.Application;

import com.erez_p.model.FinalHotel;
import com.erez_p.model.Flight;
import com.erez_p.model.Flights;
import com.erez_p.model.HotelItem;
import com.erez_p.model.Hotels;
import com.erez_p.repository.BASE.BaseRepository;
import com.erez_p.repository.HotelRepository;
import com.erez_p.viewmodel.BASE.BaseViewModel;
import com.google.firebase.firestore.Query;

public class HotelViewModel extends BaseViewModel<FinalHotel, Hotels> {
    private HotelRepository repository;
    public HotelViewModel(Application application) {
        super(FinalHotel.class, Hotels.class, application);
    }

    @Override
    protected BaseRepository<FinalHotel, Hotels> createRepository(Application application) {
        repository = new HotelRepository(application);
        return repository;
    }

    // Optional: Add method to get flights by specific criteria
    public void getHotelByTripId(String tripID) {
        getAll(repository.getCollection().whereEqualTo("tripId", tripID));
    }
}
