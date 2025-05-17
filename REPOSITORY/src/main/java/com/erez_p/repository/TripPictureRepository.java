package com.erez_p.repository;

import android.app.Application;

import com.erez_p.model.TripPicture;
import com.erez_p.model.TripPictures;
import com.erez_p.repository.BASE.BaseRepository;
import com.google.firebase.firestore.Query;

public class TripPictureRepository extends BaseRepository<TripPicture, TripPictures> {
    public TripPictureRepository(Application application) {
        super(TripPicture.class, TripPictures.class, application);
    }
    @Override
    protected Query getQueryForExist(TripPicture entity) {
        return getCollection().whereEqualTo("tripId", entity.getTripId())
                .whereEqualTo("pictureUrl", entity.getPictureUrl())
                .whereEqualTo("description", entity.getDescription());
    }
}
