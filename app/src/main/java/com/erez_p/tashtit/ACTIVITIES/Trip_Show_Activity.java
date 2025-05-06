package com.erez_p.tashtit.ACTIVITIES;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.erez_p.model.Activity;
import com.erez_p.model.FinalFlight;
import com.erez_p.model.FinalHotel;
import com.erez_p.model.Flights;
import com.erez_p.model.Hotels;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.ADPTERS.ActivitiesAdapter;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;
import com.erez_p.tashtit.ADPTERS.FinalFlightAdapter;
import com.erez_p.tashtit.ADPTERS.FinalHotelAdapter;
import com.erez_p.tashtit.R;
import com.erez_p.viewmodel.ActivitiesViewModel;
import com.erez_p.viewmodel.FlightViewModel;
import com.erez_p.viewmodel.HotelViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
                Intent intent = new Intent(Trip_Show_Activity.this, ActivityAddition_Activity.class);
                intent.putExtra("tripId", tripId);
                resultLauncher.launch(intent);
                activitiesAdapter.notifyDataSetChanged();
            }
        });
        btnAddFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //כאן תוסיף טיסה לטיול
                Intent intent = new Intent(Trip_Show_Activity.this, ActivityAddition_Activity.class);
                intent.putExtra("tripId", tripId);
                resultLauncher.launch(intent);
                flightsAdapter.notifyDataSetChanged();
            }
        });
        btnAddHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //כאן תוסיף מלון לטיול
                Intent intent = new Intent(Trip_Show_Activity.this, ActivityAddition_Activity.class);
                intent.putExtra("tripId", tripId);
                resultLauncher.launch(intent);
                hotelsAdapter.notifyDataSetChanged();
            }
        });
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //כאן תשמור את השינויים בטיול
                finish();
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
                    activitiesAdapter.setItems(activities);
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
        flightsAdapter.setOnItemLongClickListener(new GenericAdapter.OnItemLongClickListener<FinalFlight>() {
            @Override
            public boolean onItemLongClick(FinalFlight item, int position) {
                new MaterialAlertDialogBuilder(Trip_Show_Activity.this)
                        .setMessage("Delete " + item.getFlightNumber() + " ?")
                        .setIcon(R.drawable.trashcan)
                        .setCancelable(true)
                        .setTitle("Delete")
                        .setNegativeButton("No", (dialog, which) -> {
                            // Do nothing
                        })
                        .setPositiveButton("Yes", (dialog, which) -> {
                            flightViewModel.delete(item);
                            flightsAdapter.notifyDataSetChanged();
                        })
                        .show();
                return false;
            }
        });
        rvFlights.setAdapter(flightsAdapter);
        rvFlights.setLayoutManager(new LinearLayoutManager(this));
        hotelsAdapter = new FinalHotelAdapter(hotels , R.layout.hotel_item_layout, holder -> {
            holder.putView("hotelName", holder.itemView.findViewById(R.id.hotel_name1));
            holder.putView("price", holder.itemView.findViewById(R.id.hotel_price1));
        },(holder, hotel, position) -> {
            ((TextView) holder.getView("hotelName")).setText("Hotel: " + hotel.getName());
            ((TextView) holder.getView("price")).setText("Price per night: " + hotel.getPrice());
        });
        hotelsAdapter.setOnItemLongClickListener(new GenericAdapter.OnItemLongClickListener<FinalHotel>() {
            @Override
            public boolean onItemLongClick(FinalHotel item, int position) {
                new MaterialAlertDialogBuilder(Trip_Show_Activity.this)
                        .setMessage("Delete " + item.getName() + " ?")
                        .setIcon(R.drawable.trashcan)
                        .setCancelable(true)
                        .setTitle("Delete")
                        .setNegativeButton("No", (dialog, which) -> {
                            // Do nothing
                        })
                        .setPositiveButton("Yes", (dialog, which) -> {
                            hotelViewModel.delete(item);
                            hotelsAdapter.notifyDataSetChanged();
                        })
                        .show();
                return false;
            }
        });
        rvHotels.setAdapter(hotelsAdapter);
        rvHotels.setLayoutManager(new LinearLayoutManager(this));
        //צריך להכין אדפטר לאטרקציות ו לייאווט לאטרקציה
        activitiesAdapter = new ActivitiesAdapter(activities, R.layout.activity_item_layout, holder -> {
            holder.putView("activityName", holder.itemView.findViewById(R.id.activity_name));
            holder.putView("activityPrice", holder.itemView.findViewById(R.id.activity_price));
            holder.putView("activityDate", holder.itemView.findViewById(R.id.activity_date));
            holder.putView("activityTime", holder.itemView.findViewById(R.id.activity_time));
            holder.putView("activityDuration", holder.itemView.findViewById(R.id.activity_duration));
        }, (holder, activity, position) -> {
            ((TextView) holder.getView("activityName")).setText(activity.getActivityName());
            ((TextView) holder.getView("activityPrice")).setText("$" + activity.getActivityPrice());
            ((TextView) holder.getView("activityDate")).setText(activity.getActivityDate());
            ((TextView) holder.getView("activityTime")).setText(""+activity.getActivityTime());
            ((TextView) holder.getView("activityDuration")).setText("" + activity.getActivityDuration() + " hours");
        });
        activitiesAdapter.setOnItemLongClickListener(new GenericAdapter.OnItemLongClickListener<Activity>() {
            @Override
            public boolean onItemLongClick(Activity item, int position) {
                new MaterialAlertDialogBuilder(Trip_Show_Activity.this)
                        .setMessage("Delete " + item.getActivityName() + " ?")
                        .setIcon(R.drawable.trashcan)
                        .setCancelable(true)
                        .setTitle("Delete")
                        .setNegativeButton("No", (dialog, which) -> {
                            // Do nothing
                        })
                        .setPositiveButton("Yes", (dialog, which) -> {
                            activitiesViewModel.delete(item);
                            activitiesAdapter.notifyDataSetChanged();
                        })
                        .show();
                return false;
            }
        });
        rvActivities.setAdapter(activitiesAdapter);
        rvActivities.setLayoutManager(new LinearLayoutManager(this));
    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 200) {
                        //hotel
                        Intent intent = result.getData();
                        String hotelname = intent.getStringExtra("HotelItem");
                        if (hotelname != null) {
                            Toast.makeText( Trip_Show_Activity.this, hotelname + "added", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(result.getResultCode() == 100)
                    {
                        // flight
                        Intent intent = result.getData();
                        String flightname = intent.getStringExtra("Flight");
                        if (flightname != null) {
                            Toast.makeText( Trip_Show_Activity.this, flightname + "added", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(result.getResultCode() == 300)
                    {
                        // activity
                        Intent intent = result.getData();
                        String Activityname = intent.getStringExtra("Activity");
                        if (Activityname != null) {

                            Toast.makeText( Trip_Show_Activity.this, Activityname + "added", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );
}