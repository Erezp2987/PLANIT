package com.erez_p.tashtit.ADPTERS;

import com.erez_p.model.Trip;
import com.erez_p.model.TripPicture;
import com.erez_p.model.TripPictures;
import com.erez_p.model.Trips;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;

public class TripPictureAdapter extends GenericAdapter<TripPicture> {
    public TripPictureAdapter(TripPictures items, int layoutId, InitializeViewHolder initializeViewHolder, BindViewHolder<TripPicture> bindViewHolder) {
        super(items, layoutId, initializeViewHolder, bindViewHolder);
    }
}
