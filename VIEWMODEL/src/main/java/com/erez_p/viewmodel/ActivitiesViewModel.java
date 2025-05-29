package com.erez_p.viewmodel;

import android.app.Application;

import com.erez_p.model.Activities;
import com.erez_p.model.Activity;
import com.erez_p.repository.ActivitiesRepository;
import com.erez_p.repository.BASE.BaseRepository;
import com.erez_p.viewmodel.BASE.BaseViewModel;

public class ActivitiesViewModel extends BaseViewModel<Activity, Activities> {
    private ActivitiesRepository repository;
    @Override
    protected BaseRepository<Activity, Activities> createRepository(Application application) {
        repository = new ActivitiesRepository(application);
        return repository;
    }
    public ActivitiesViewModel(Application application) {
        super(Activity.class, Activities.class, application);
    }
    public void getActivitiesByTripID(String tripID) {
        getAll(repository.getCollection().whereEqualTo("tripID", tripID));
    }
    public void getActivityByActivityId(String activityId)
    {
        get(repository.getCollection().whereEqualTo("idFs",activityId));
    }

}
