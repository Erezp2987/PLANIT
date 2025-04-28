package com.erez_p.tashtit.ACTIVITIES;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.erez_p.model.Activities;
import com.erez_p.model.Flights;
import com.erez_p.model.Hotels;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.ADPTERS.ActivitiesAdapter;
import com.erez_p.tashtit.ADPTERS.FinalFlightAdapter;
import com.erez_p.tashtit.ADPTERS.FinalHotelAdapter;
import com.erez_p.tashtit.R;
import com.erez_p.viewmodel.ActivitiesViewModel;
import com.erez_p.viewmodel.FlightViewModel;
import com.erez_p.viewmodel.HotelViewModel;

public class Trip_Show_Activity extends BaseActivity {
    private EditText tvTripName,
            tvDepartureDate,
            tvReturnDate;
    private RecyclerView rvFlights,
            rvHotels,
            rvActivities;
    private Button btnDiscardChanges,
            btnSaveChanges, btnAddFlight, btnAddHotel, btnAddActivity;
    private FinalFlightAdapter flightsAdapter;
    private FinalHotelAdapter hotelsAdapter;
    private ActivitiesAdapter activitiesAdapter;
    private String tripId;
    private FlightViewModel flightViewModel;
    private HotelViewModel hotelViewModel;
    private ActivitiesViewModel activitiesViewModel;
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
        tvTripName = findViewById(R.id.etTripName);
        tvDepartureDate = findViewById(R.id.etDepartureDate);
        tvReturnDate = findViewById(R.id.etReturnDate);
        rvFlights = findViewById(R.id.rvFlights);
        rvHotels = findViewById(R.id.rvHotels);
        rvActivities = findViewById(R.id.rvActivities);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        tripId = getIntent().getStringExtra("tripId");
        btnAddFlight = findViewById(R.id.btnAddFlight);
        btnAddHotel = findViewById(R.id.btnAddHotel);
        btnAddActivity = findViewById(R.id.btnAddActivity);
    }

    @Override
    protected void setListeners() {
        btnAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //כאן תוסיף פעילות לטיול
            }
        });
        btnAddFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //כאן תוסיף טיסה לטיול
            }
        });
        btnAddHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //כאן תוסיף מלון לטיול
            }
        });
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //כאן תשמור את השינויים בטיול
            }
        });
    }

    @Override
    protected void setViewModel() {
        flightViewModel = new ViewModelProvider(this).get(FlightViewModel.class);
        hotelViewModel = new ViewModelProvider(this).get(HotelViewModel.class);
        activitiesViewModel = new ViewModelProvider(this).get(ActivitiesViewModel.class);
        flightViewModel.getFlightByTripID(tripId);
        flightViewModel.getLiveDataCollection().observe(this, new Observer<Flights>() {
            @Override
            public void onChanged(Flights finalFlights) {
                //תעדכן את רשימת הטיסות
            }
        });
        hotelViewModel.getHotelByTripId(tripId);
        hotelViewModel.getLiveDataCollection().observe(this, new Observer<Hotels>() {
            @Override
            public void onChanged(Hotels finalHotels) {
                //תעדכן את רשימת המלונות
            }
        });
        activitiesViewModel.getActivitiesByTripID(tripId);
        activitiesViewModel.getLiveDataCollection().observe(this, new Observer<Activities>() {
            @Override
            public void onChanged(Activities finalActivities) {
                //תעדכן את רשימת האטרקציות
            }
        });
    }
}