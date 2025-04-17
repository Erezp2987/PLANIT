package com.erez_p.tashtit.ADPTERS;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erez_p.model.HotelItem;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;
import com.erez_p.tashtit.R;

import java.util.List;

public class HotelAdapter extends GenericAdapter<HotelItem> {

    public HotelAdapter(List<HotelItem> items, int layoutId, InitializeViewHolder initializeViewHolder, BindViewHolder<HotelItem> bindViewHolder) {
        super(items, layoutId, initializeViewHolder, bindViewHolder);
    }
}
