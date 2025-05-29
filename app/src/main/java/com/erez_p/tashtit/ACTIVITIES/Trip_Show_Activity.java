package com.erez_p.tashtit.ACTIVITIES;

import static android.app.ProgressDialog.show;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.erez_p.model.Trip;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.ADPTERS.ActivitiesAdapter;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;
import com.erez_p.tashtit.ADPTERS.FinalFlightAdapter;
import com.erez_p.tashtit.ADPTERS.FinalHotelAdapter;
import com.erez_p.tashtit.R;
import com.erez_p.viewmodel.ActivitiesViewModel;
import com.erez_p.viewmodel.FlightViewModel;
import com.erez_p.viewmodel.HotelViewModel;
import com.erez_p.viewmodel.TripsViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;

public class Trip_Show_Activity extends BaseActivity {
    private EditText tvTripName,
            tvDepartureDate,
            tvReturnDate;
    private RecyclerView rvFlights,
            rvHotels,
            rvActivities;
    private Button
            btnSaveChanges, btnAddFlight, btnAddHotel, btnAddActivity;
    private FinalFlightAdapter flightsAdapter;
    private FinalHotelAdapter hotelsAdapter;
    private ActivitiesAdapter activitiesAdapter;
    private String tripId;
    private FlightViewModel flightViewModel;
    private HotelViewModel hotelViewModel;
    private ActivitiesViewModel activitiesViewModel;
    private TripsViewModel tripViewModel;
    private Flights flights;
    private Hotels hotels;
    private Activities activities;
    private Trip Usertrip;
    private String departureDate, returnDate;
    private Button btnTripPictures;
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
        tvDepartureDate.setFocusable(false);
        tvReturnDate = findViewById(R.id.etReturnDate);
        tvReturnDate.setFocusable(false);
        tvReturnDate.setEnabled(false);
        rvFlights = findViewById(R.id.rvFlights);
        rvHotels = findViewById(R.id.rvHotels);
        rvActivities = findViewById(R.id.rvActivities);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        tripId = getIntent().getStringExtra("tripId");
        btnAddFlight = findViewById(R.id.btnAddFlight);
        btnAddHotel = findViewById(R.id.btnAddHotel);
        btnAddActivity = findViewById(R.id.btnAddActivity);
        btnTripPictures = findViewById(R.id.btnViewAlbum);
    }

    @Override
    protected void setListeners() {
        btnTripPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trip_Show_Activity.this, Album_Activity.class);
                intent.putExtra("tripId", tripId);
                startActivity(intent);
            }
        });
        tvDepartureDate.setOnClickListener(v -> showDatePickerDialog(true));
        tvReturnDate.setOnClickListener(v -> showDatePickerDialog(false));

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
                Intent intent = new Intent(Trip_Show_Activity.this, Flight_Activity.class);
                intent.putExtra("tripId", tripId);
                resultLauncher.launch(intent);
                flightsAdapter.notifyDataSetChanged();
            }
        });
        btnAddHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //כאן תוסיף מלון לטיול
                Intent intent = new Intent(Trip_Show_Activity.this, Hotel_Activity.class);
                intent.putExtra("tripId", tripId);
                resultLauncher.launch(intent);
                hotelsAdapter.notifyDataSetChanged();
            }
        });
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //כאן תשמור את השינויים בטיול
                //את השמירה של הנתונים שהשתננו כמו שם תאריך יציאה ותאריך חזרהתוסיף
                if (!Usertrip.getName().equals("")) {
                    if (!Usertrip.getName().equals(tvTripName.getText().toString().trim())) {
                        Usertrip.setName(tvTripName.getText().toString().trim());
                    }
                    if (!Usertrip.getDateDeparture().equals(tvDepartureDate.getText().toString().trim())) {
                        Usertrip.setDateDeparture(tvDepartureDate.getText().toString().trim());
                    }
                    if (!Usertrip.getDateReturn().equals(tvReturnDate.getText().toString().trim())) {
                        Usertrip.setDateReturn(tvReturnDate.getText().toString().trim());
                    }
                    tripViewModel.save(Usertrip);
                    finish();
                } else {
                    Toast.makeText(Trip_Show_Activity.this, "Please fill in the trip name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void setViewModel() {
        flightViewModel = new ViewModelProvider(this).get(FlightViewModel.class);
        hotelViewModel = new ViewModelProvider(this).get(HotelViewModel.class);
        activitiesViewModel = new ViewModelProvider(this).get(ActivitiesViewModel.class);
        tripViewModel = new ViewModelProvider(this).get(TripsViewModel.class);
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
        tripViewModel.getTripsByTripID(tripId);
        tripViewModel.getLiveDataEntity().observe(this, new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                if(trip != null) {
                    Usertrip= trip;
                    tvTripName.setText(trip.getName());
                    tvTripName.setTextColor(getResources().getColor(R.color.white));
                    tvDepartureDate.setText(trip.getDateDeparture());
                    tvDepartureDate.setTextColor(getResources().getColor(R.color.white));
                    tvReturnDate.setText(trip.getDateReturn());
                    tvReturnDate.setTextColor(getResources().getColor(R.color.white));
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
        flightsAdapter.setOnItemClickListener(new GenericAdapter.OnItemClickListener<FinalFlight>() {
            @Override
            public void onItemClick(FinalFlight item, int position) {
                String url = "https://www.google.com/search?q=" + item.getAirline() + "+" + item.getFlightNumber() + "+flight+status";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
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
        hotelsAdapter.setOnItemClickListener(new GenericAdapter.OnItemClickListener<FinalHotel>() {
            @Override
            public void onItemClick(FinalHotel item, int position) {
                String url = "https://www.google.com/maps?q=" + item.getLatitude() + "," + item.getLongtitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
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
        activitiesAdapter.setOnItemClickListener(new GenericAdapter.OnItemClickListener<Activity>() {
            @Override
            public void onItemClick(Activity item, int position) {
                Intent intent = new Intent(Trip_Show_Activity.this,ActivityAddition_Activity.class);
                intent.putExtra("tripId",tripId);
                intent.putExtra("activityId",item.getIdFs());
                startActivity(intent);
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
                            Toast.makeText( Trip_Show_Activity.this, hotelname + " added", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(result.getResultCode() == 100)
                    {
                        // flight
                        Intent intent = result.getData();
                        String flightname = intent.getStringExtra("Flight");
                        if (flightname != null) {
                            Toast.makeText( Trip_Show_Activity.this, flightname + " added", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(result.getResultCode() == 300)
                    {
                        // activity
                        Intent intent = result.getData();
                        String Activityname = intent.getStringExtra("Activity");
                        if (Activityname != null) {

                            Toast.makeText( Trip_Show_Activity.this, Activityname + " added", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );








    private void showDatePickerDialog(final boolean isDeparture) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        if (isDeparture) {
                            departureDate = selectedDate;
                            tvDepartureDate.setText("Departure: " + selectedDate);
                            tvReturnDate.setEnabled(true);
                        } else {
                            returnDate = selectedDate;
                            tvReturnDate.setText("Return: " + selectedDate);
                        }
                    }
                },
                year,
                month,
                day
        );

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        // Set min date for return date picker if departure date is already selected
        if (!isDeparture && departureDate != null && !departureDate.isEmpty()) {
            try {
                String[] dateParts = departureDate.split("-");
                if (dateParts.length == 3) {
                    Calendar minDate = Calendar.getInstance();
                    minDate.set(Integer.parseInt(dateParts[0]),
                            Integer.parseInt(dateParts[1]) - 1,
                            Integer.parseInt(dateParts[2]));
                    datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        datePickerDialog.show();
    }
}