package com.erez_p.tashtit.ACTIVITIES;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import com.erez_p.model.Trip;
import com.erez_p.model.Trips;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;
import com.erez_p.tashtit.ADPTERS.BASE.SwipeCallback;
import com.erez_p.tashtit.ADPTERS.BASE.SwipeConfig;
import com.erez_p.tashtit.ADPTERS.TripAdapter;
import com.erez_p.tashtit.R;
import com.erez_p.viewmodel.TripsViewModel;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home_Screen extends BaseActivity {

    private FloatingActionButton newTrip;
    private RecyclerView tripRV;
    private TripAdapter adapter;
    private TripsViewModel viewModel;
    private String userId;

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

    }

    @Override
    protected void setListeners() {
        newTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Screen.this,Start_Trip_Activity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setViewModel() {
        viewModel = new ViewModelProvider(Home_Screen.this).get(TripsViewModel.class);
        viewModel.getLiveDataCollection().observe(Home_Screen.this, new Observer<Trips>() {
            @Override
            public void onChanged(Trips trips) {
                Log.d("BULBUL", "onChanged: "+trips);
               adapter.setItems(trips);
            }});
        viewModel.getTripsByUserID(userId);//כאן הבעיה
    }
    private void setRecyclerView()
    {
        adapter = new TripAdapter(null,R.layout.single_trip_layout,
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

        SwipeConfig config = new SwipeConfig();
        config.rightBackgroundColor= Color.BLUE;
        config.leftBackgroundColor=Color.RED;
        config.rightText = "Edit";
        config.leftText = "Delete";
        SwipeCallback<Trip> swipeCallback = new SwipeCallback<>(adapter, this, config);
        adapter.setOnItemSwipeListener(new GenericAdapter.OnItemSwipeListener<Trip>() {


            //EDIT TRIP HERE
            @Override
            public void onItemSwipeRight(Trip item, int position)
            {

            }

            //DELETE TRIP
            @Override
            public void onItemSwipeLeft(Trip item, int position) {
                new MaterialAlertDialogBuilder(Home_Screen.this)
                        .setMessage("Delete " + item.getName() + " ?")
                        .setIcon(R.drawable.trashcan)
                        .setCancelable(true)
                        .setTitle("Delete")
                        .setNegativeButton("No", (dialog, which) -> {
                            adapter.notifyItemChanged(position);
                        })
                        .setPositiveButton("Yes", (dialog, which) -> {
                            showProgressDialog("Members", "Deleting " + item.getName() + "...");
                            viewModel.delete(item);
                            adapter.getItems().remove(position);
                            adapter.notifyItemRemoved(position);
                        })
                        .show();
            }
        });
    }

}