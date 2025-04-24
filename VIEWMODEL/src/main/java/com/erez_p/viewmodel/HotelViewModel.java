package com.erez_p.viewmodel;

import android.app.Application;

import com.erez_p.model.Flight;
import com.erez_p.model.Flights;
import com.erez_p.model.HotelItem;
import com.erez_p.model.Hotels;
import com.erez_p.repository.BASE.BaseRepository;
import com.erez_p.repository.HotelRepository;
import com.erez_p.viewmodel.BASE.BaseViewModel;
import com.google.firebase.firestore.Query;

public class HotelViewModel extends BaseViewModel<HotelItem, Hotels> {
    private HotelRepository repository;
    public HotelViewModel(Application application) {
        super(HotelItem.class, Hotels.class, application);
    }

    @Override
    protected BaseRepository<HotelItem, Hotels> createRepository(Application application) {
        repository = new HotelRepository(application);
        return repository;
    }
    public void getAll() {
        getAll(Query.Direction.ASCENDING);
    }
    public void getAll(Query.Direction direction) {
        getAll(repository.getCollection().orderBy("hotelName", direction));
    }

    // Optional: Add method to get flights by specific criteria
    public void getHotelByNameAndDates(String flightNumber, String dateDeparture, String dateReturn) {
        get(repository.getCollection().whereEqualTo("hotelName", flightNumber)
                .whereEqualTo("hotelDepartureDate", dateDeparture)
                .whereEqualTo("hotelReturnDate", dateReturn));
    }
}
