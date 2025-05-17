package com.erez_p.viewmodel;

import android.app.Application;

import com.erez_p.model.TripPicture;
import com.erez_p.model.TripPictures;
import com.erez_p.repository.BASE.BaseRepository;
import com.erez_p.repository.TripPictureRepository;
import com.erez_p.viewmodel.BASE.BaseViewModel;

public class TripPictureViewModel extends BaseViewModel<TripPicture, TripPictures> {
    private TripPictureRepository repository;

    @Override
    protected BaseRepository<TripPicture, TripPictures> createRepository(Application application) {
        repository = new TripPictureRepository(application);
        return repository;
    }
    public TripPictureViewModel(Application application) {
        super(TripPicture.class, TripPictures.class, application);
    }
    public void getTripPicturesByTripID(String tripID) {
        getAll(repository.getCollection().whereEqualTo("tripId", tripID));
    }
}
