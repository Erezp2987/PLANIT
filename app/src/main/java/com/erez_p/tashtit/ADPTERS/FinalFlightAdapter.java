package com.erez_p.tashtit.ADPTERS;

import com.erez_p.model.FinalFlight;
import com.erez_p.model.Flights;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;

import java.util.List;

public class FinalFlightAdapter extends GenericAdapter<FinalFlight> {
    public FinalFlightAdapter(Flights items, int layoutId, InitializeViewHolder initializeViewHolder, BindViewHolder<FinalFlight> bindViewHolder) {
        super(items, layoutId, initializeViewHolder, bindViewHolder);
    }
}
