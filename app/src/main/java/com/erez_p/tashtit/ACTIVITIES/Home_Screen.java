package com.erez_p.tashtit.ACTIVITIES;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erez_p.helper.LoginPreference;
import com.erez_p.model.Activities;
import com.erez_p.model.FinalFlight;
import com.erez_p.model.Flights;
import com.erez_p.model.Hotels;
import com.erez_p.model.Trip;
import com.erez_p.model.Trips;
import com.erez_p.model.User;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;
import com.erez_p.tashtit.ADPTERS.BASE.SwipeCallback;
import com.erez_p.tashtit.ADPTERS.BASE.SwipeConfig;
import com.erez_p.tashtit.ADPTERS.TripAdapter;
import com.erez_p.tashtit.R;
import com.erez_p.viewmodel.ActivitiesViewModel;
import com.erez_p.viewmodel.FlightViewModel;
import com.erez_p.viewmodel.HotelViewModel;
import com.erez_p.viewmodel.TripPictureViewModel;
import com.erez_p.viewmodel.TripsViewModel;
import com.erez_p.viewmodel.UsersViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Iterator;

public class Home_Screen extends BaseActivity {

    private FloatingActionButton newTrip;
    private Button btnLogOut;
    private RecyclerView tripRV;
    private TripAdapter adapter;
    private TripsViewModel viewModel;
    private String userId, userEmail;
    private ActivitiesViewModel activitiesViewModel;
    private FlightViewModel flightsViewModel;
    private HotelViewModel hotelViewModel;
    private UsersViewModel usersViewModel;
    private Trips trips=new Trips();
    private User thisUser;
    private Activities userActivities;
    private Flights userFlights;
    private Hotels userHotels;
    private LoginPreference loginPreference;
    private TripPictureViewModel tripPictureViewModel;
    private Button btnEditUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
        setListeners();
        setRecyclerView();
        setViewModel();
    }

    @Override
    protected void initializeViews() {
        newTrip = findViewById(R.id.btnAddTrip);
        tripRV = findViewById(R.id.rvTrips);
        Intent intent = getIntent();
        userId = intent.getStringExtra("idUser");
        userEmail = intent.getStringExtra("userEmail");
        btnLogOut = findViewById(R.id.btnLogout);
        btnEditUser = findViewById(R.id.btnEditUser);
    }

    @Override
    protected void setListeners() {
        newTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thisUser != null) {
                    Intent intent = new Intent(Home_Screen.this, Start_Trip_Activity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPreference = new LoginPreference(Home_Screen.this);
                if(loginPreference.getEmail() != null && loginPreference.getPassword() != null)
                {
                   loginPreference.clearLoginCredentials();
                }
                finish();
                Intent intent = new Intent(Home_Screen.this, Login_Activity.class);
                startActivity(intent);
            }
        });
        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Screen.this, Register_Activity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setViewModel() {
        activitiesViewModel = new ViewModelProvider(Home_Screen.this).get(ActivitiesViewModel.class);
        flightsViewModel = new ViewModelProvider(Home_Screen.this).get(FlightViewModel.class);
        hotelViewModel = new ViewModelProvider(Home_Screen.this).get(HotelViewModel.class);
        usersViewModel = new ViewModelProvider(Home_Screen.this).get(UsersViewModel.class);
        viewModel = new ViewModelProvider(Home_Screen.this).get(TripsViewModel.class);
        tripPictureViewModel = new ViewModelProvider(Home_Screen.this).get(TripPictureViewModel.class);
        viewModel.getTripsByUserID(userId);
        viewModel.getLiveDataCollection().observe(Home_Screen.this, new Observer<Trips>() {
            @Override
            public void onChanged(Trips trips) {
                Home_Screen.this.trips = trips;
                adapter.setItems(trips);
            }
        });
        usersViewModel.getUserById(userId);
        usersViewModel.getLiveDataEntity().observe(Home_Screen.this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null)
                {
                    thisUser = user;
                }
            }
        });
        activitiesViewModel.getLiveDataCollection().observe(Home_Screen.this, new Observer<Activities>() {
            @Override
            public void onChanged(Activities activities) {
                userActivities = activities;
            }
        });
        flightsViewModel.getLiveDataCollection().observe(Home_Screen.this, new Observer<Flights>() {
            @Override
            public void onChanged(Flights flights) {
                userFlights = flights;
            }
        });
        hotelViewModel.getLiveDataCollection().observe(Home_Screen.this, new Observer<Hotels>() {
            @Override
            public void onChanged(Hotels hotels) {
                userHotels = hotels;
            }
        });
    }
    private void setRecyclerView()
    {
        adapter = new TripAdapter(trips,R.layout.single_trip_layout,
                holder -> {
                    holder.putView("nameTrip",holder.itemView.findViewById(R.id.tvTripName));
                    holder.putView("departureDateTrip",holder.itemView.findViewById(R.id.tvDepartureDate));
                    holder.putView("returnDateTrip",holder.itemView.findViewById(R.id.tvReturnDate));
                },
                (((holder, item, position) -> {
                    ((TextView)holder.getView("nameTrip")).setText(item.getName());
                    ((TextView)holder.getView("departureDateTrip")).setText(""+item.getDateDeparture());
                    ((TextView)holder.getView("returnDateTrip")).setText(""+item.getDateReturn());
                })

                ));
        tripRV.setAdapter(adapter);
        tripRV.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new GenericAdapter.OnItemClickListener<Trip>() {
            @Override
            public void onItemClick(Trip item, int position) {
                Intent intent = new Intent(Home_Screen.this, Trip_Show_Activity.class);
                intent.putExtra("tripId", item.getIdFs());
                startActivity(intent);
            }
        });
        adapter.setOnItemLongClickListener(new GenericAdapter.OnItemLongClickListener<Trip>() {
            @Override
            public boolean onItemLongClick(Trip item, int position) {
                if(thisUser!=null ) {
                    //Delete trip
                    new MaterialAlertDialogBuilder(Home_Screen.this)
                            .setMessage("Delete " + item.getName() + " ?")
                            .setIcon(R.drawable.trashcan)
                            .setCancelable(true)
                            .setTitle("Delete")
                            .setNegativeButton("No", (dialog, which) -> {
                                adapter.notifyItemChanged(position);
                            })
                            .setPositiveButton("Yes", (dialog, which) -> {
                                //תוסיף מחיקה של כל המלונות בטיול וכל הטיסות הטיול וכל הפעילויות בטיול
                                viewModel.delete(item);
                                activitiesViewModel.getActivitiesByTripID(item.getIdFs());
                                flightsViewModel.getFlightByTripID(item.getIdFs());
                                hotelViewModel.getHotelByTripId(item.getIdFs());
                                tripPictureViewModel.getTripPicturesByTripID(item.getIdFs());
                                // מחיקה לא עובדת של אטרקציה
                                activitiesViewModel.getLiveDataCollection().observe(Home_Screen.this, activities -> {
                                    if (activities != null) {
                                        for (int i = 0; i < activities.size(); i++) {
                                            activitiesViewModel.delete(activities.get(i));
                                        }
                                    }
                                });

                                flightsViewModel.getLiveDataCollection().observe(Home_Screen.this, flights -> {
                                    if (flights != null) {
                                        for (int i = 0; i < flights.size(); i++) {
                                            flightsViewModel.delete(flights.get(i));
                                        }
                                    }
                                });
                                //לא עובד המחיקה של המלונות
                                hotelViewModel.getLiveDataCollection().observe(Home_Screen.this, hotels -> {
                                    if (hotels != null) {
                                        for (int i = 0; i < hotels.size(); i++) {
                                            hotelViewModel.delete(hotels.get(i));
                                        }
                                    }
                                });
                                tripPictureViewModel.getLiveDataCollection().observe(Home_Screen.this, tripPictures -> {;
                                    if (tripPictures != null) {
                                        for (int i = 0; i < tripPictures.size(); i++) {
                                            tripPictureViewModel.delete(tripPictures.get(i));
                                        }
                                    }
                                });
                                adapter.getItems().remove(position);
                                adapter.notifyItemRemoved(position);
                            })
                            .show();
                }
                return false;
            }
        });
    }

}