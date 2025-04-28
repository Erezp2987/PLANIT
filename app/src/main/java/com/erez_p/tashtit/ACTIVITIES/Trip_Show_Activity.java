package com.erez_p.tashtit.ACTIVITIES;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.R;

public class Trip_Show_Activity extends BaseActivity {
    TextView tvTripName,
            tvDepartureDate,
            tvReturnDate;
    RecyclerView rvFlights,
            rvHotels,
            rvActivities;
    Button btnDiscardChanges,
            btnSaveChanges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trip_show);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
        setViewModel();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        tvTripName = findViewById(R.id.tvTripName);
        tvDepartureDate = findViewById(R.id.tvDepartureDate);
        tvReturnDate = findViewById(R.id.tvReturnDate);
        rvFlights = findViewById(R.id.rvFlights);
        rvHotels = findViewById(R.id.rvHotels);
        rvActivities = findViewById(R.id.rvActivities);
        btnDiscardChanges = findViewById(R.id.btnDiscardChanges);
        btnSaveChanges = findViewById(R.id.btnSaveChangess);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void setViewModel() {

    }
}