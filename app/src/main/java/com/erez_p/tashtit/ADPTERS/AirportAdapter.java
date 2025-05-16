package com.erez_p.tashtit.ADPTERS;

import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;

import java.util.List;

public class AirportAdapter extends GenericAdapter<String> {
    public AirportAdapter(List<String> items, int layoutId, InitializeViewHolder initializeViewHolder, BindViewHolder<String> bindViewHolder) {
        super(items, layoutId, initializeViewHolder, bindViewHolder);
    }
}
