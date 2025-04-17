package com.erez_p.tashtit.ADPTERS;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.erez_p.model.Flight;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;
import com.erez_p.tashtit.R;

import java.util.List;

public class FlightAdapter extends GenericAdapter<Flight> {

    public FlightAdapter(List<Flight> flights, int layoutId, InitializeViewHolder initializeViewHolder, BindViewHolder<Flight> bindViewHolder) {
        super(flights, layoutId, initializeViewHolder, bindViewHolder);
    }
}
