package com.erez_p.tashtit.ADPTERS;

import com.erez_p.model.Trip;
import com.erez_p.model.Trips;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;

import java.util.List;

public class TripAdapter extends GenericAdapter<Trip>{
    public TripAdapter(Trips items, int layoutId, InitializeViewHolder initializeViewHolder, BindViewHolder<Trip> bindViewHolder) {
        super(items, layoutId, initializeViewHolder, bindViewHolder);
    }
}
