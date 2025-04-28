package com.erez_p.tashtit.ADPTERS;

import com.erez_p.model.Activities;
import com.erez_p.model.Activity;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;

import java.util.List;

public class ActivitiesAdapter extends GenericAdapter<Activity> {
    public ActivitiesAdapter(Activities items, int layoutId, InitializeViewHolder initializeViewHolder, BindViewHolder<Activity> bindViewHolder) {
        super(items, layoutId, initializeViewHolder, bindViewHolder);
    }
}
