package com.erez_p.repository;

import com.erez_p.model.Activities;
import com.erez_p.model.Activity;
import com.erez_p.repository.BASE.BaseRepository;
import com.google.firebase.firestore.Query;

import android.app.Application;

public class ActivitiesRepository extends BaseRepository<Activity, Activities> {
    public ActivitiesRepository(Application application) {
        super(Activity.class, Activities.class, application);
    }

    @Override
    protected Query getQueryForExist(Activity entity) {
        return getCollection()
                .whereEqualTo("tripID", entity.getTripID())
                .whereEqualTo("activityName", entity.getActivityName());
    }
}
