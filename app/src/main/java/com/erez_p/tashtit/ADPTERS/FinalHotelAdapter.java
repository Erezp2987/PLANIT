package com.erez_p.tashtit.ADPTERS;

import com.erez_p.model.FinalHotel;
import com.erez_p.model.Hotels;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;

public class FinalHotelAdapter extends GenericAdapter<FinalHotel> {
    public FinalHotelAdapter(Hotels items, int layoutId, InitializeViewHolder initializeViewHolder, BindViewHolder<FinalHotel> bindViewHolder) {
        super(items, layoutId, initializeViewHolder, bindViewHolder);
    }
}
