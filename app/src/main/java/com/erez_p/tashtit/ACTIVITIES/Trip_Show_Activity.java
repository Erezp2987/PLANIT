package com.erez_p.tashtit.ACTIVITIES;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
    private Flights flights;
    private Hotels hotels;
    private Activities activities;
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
        setRecyclerView();
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
                if(finalFlights != null) {
                    flights = finalFlights;
                    flightsAdapter.setItems(finalFlights);
                }
            }
        });
        hotelViewModel.getHotelByTripId(tripId);
        hotelViewModel.getLiveDataCollection().observe(this, new Observer<Hotels>() {
            @Override
            public void onChanged(Hotels finalHotels) {
                //תעדכן את רשימת המלונות
                if (finalHotels != null) {
                    hotels = finalHotels;
                    hotelsAdapter.setItems(hotels);
                }
            }
        });
        activitiesViewModel.getActivitiesByTripID(tripId);
        activitiesViewModel.getLiveDataCollection().observe(this, new Observer<Activities>() {
            @Override
            public void onChanged(Activities finalActivities) {
                //תעדכן את רשימת האטרקציות
                if (finalActivities != null) {
                    activities = finalActivities;
                }
            }
        });
    }
    private void setRecyclerView(){
        flightsAdapter = new FinalFlightAdapter(flights , R.layout.item_flight, holder -> {
            holder.putView("flightImage", holder.itemView.findViewById(R.id.airlineLogoImageView));
            holder.putView("airline", holder.itemView.findViewById(R.id.airlineTextView));
            holder.putView("flightNumber", holder.itemView.findViewById(R.id.flightNumberTextView));
            holder.putView("departure", holder.itemView.findViewById(R.id.departureTextView));
            holder.putView("arrival", holder.itemView.findViewById(R.id.arrivalTextView));
            holder.putView("duration", holder.itemView.findViewById(R.id.durationTextView));
            holder.putView("price", holder.itemView.findViewById(R.id.priceTextView));
            holder.putView("class", holder.itemView.findViewById(R.id.Class));
        },(holder, flight, position) -> {
            // Set the image using Glide or any other image loading library
            Glide.with(this)
                    .load(flight.getAirlineLogo())
                    .into((ImageView) holder.getView("flightImage"));
            ((TextView) holder.getView("airline")).setText("Airline: " + flight.getAirline());
            ((TextView) holder.getView("flightNumber")).setText("Flight No: " + flight.getFlightNumber());
            ((TextView) holder.getView("departure")).setText("Departure: " + flight.getDepartureAirport() + " at " + flight.getDeparturnLandingTime());
            ((TextView) holder.getView("arrival")).setText("Arrival: " + flight.getArrivalAirport() + " at " + flight.getArrivalLandingTime());
            ((TextView) holder.getView("duration")).setText("Duration: " + flight.getDuration() + " min");
            ((TextView) holder.getView("price")).setText("$" + flight.getPrice());
            ((TextView) holder.getView("class")).setText("Class: " + flight.getTravelClass());
        });
        rvFlights.setAdapter(flightsAdapter);
        rvFlights.setLayoutManager(new LinearLayoutManager(this));
        hotelsAdapter = new FinalHotelAdapter(hotels , R.layout.hotel_item_layout, holder -> {
            holder.putView("hotelName", holder.itemView.findViewById(R.id.hotel_name1));
            holder.putView("price", holder.itemView.findViewById(R.id.hotel_price1));
        },(holder, hotel, position) -> {
            ((TextView) holder.getView("hotelName")).setText("Hotel: " + hotel.getName());
            ((TextView) holder.getView("price")).setText("Price per night: $" + hotel.getPrice());
        });
        rvHotels.setAdapter(hotelsAdapter);
        rvHotels.setLayoutManager(new LinearLayoutManager(this));
        //צריך להכין אדפטר לאטרקציות ו לייאווט לאטרקציה
    }
}