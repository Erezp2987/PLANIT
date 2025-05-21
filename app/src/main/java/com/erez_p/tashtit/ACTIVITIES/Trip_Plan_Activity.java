package com.erez_p.tashtit.ACTIVITIES;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.R;

public class Trip_Plan_Activity extends BaseActivity {
    private Button confirm, returnBack, chooseFlight, chooseHotel, chooseActivity;
    private TextView flightText, hotelText, activityText;
    private String tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trip_plan);
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
        confirm = findViewById(R.id.confirmButton);
        returnBack = findViewById(R.id.returnButton);
        chooseHotel = findViewById(R.id.btnAddHotel);
        chooseFlight = findViewById(R.id.btnAddFlight);
        flightText = findViewById(R.id.flightsSelectedText);
        hotelText = findViewById(R.id.hotelsSelectedText);
        activityText = findViewById(R.id.activitiesSelectedText);
        chooseActivity = findViewById(R.id.btnAddActivity);
        Intent intent = getIntent();
        tripId = intent.getStringExtra("tripId");
        activityText.setText("Selected Activities: ");
        hotelText.setText("Selected Hotel: ");
        flightText.setText("Selected Flight: ");
    }

    @Override
    protected void setListeners() {
        //Delete/Cancel TRIP
        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Create new Trip object with all the collected information
                    finish();
            }
        });
        // ADD HOTEL TO TRIP
        chooseHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trip_Plan_Activity.this, Hotel_Activity.class);
                intent.putExtra("tripId", tripId);
                resultLauncher.launch(intent);
            }
        });
        //ADD FLIGHT TO TRIP
        chooseFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trip_Plan_Activity.this, Flight_Activity.class);
                intent.putExtra("tripId", tripId);
                resultLauncher.launch(intent);
            }
        });
        //ADD ACTIVITY TO TRIP
        chooseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trip_Plan_Activity.this, ActivityAddition_Activity.class);
                intent.putExtra("tripId", tripId);
                resultLauncher.launch(intent);
            }
        });
        // Set listeners for new date picker buttons
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
                            hotelText.setText(hotelText.getText().toString() + " "+hotelname);
                        }
                    }
                    if(result.getResultCode() == 100)
                    {
                        // flight
                        Intent intent = result.getData();
                        String flightname = intent.getStringExtra("Flight");
                        if (flightname != null) {

                                flightText.setText(flightText.getText().toString()+" "+flightname);
                        }
                    }
                    if(result.getResultCode() == 300)
                    {
                        // activity
                        Intent intent = result.getData();
                        String Activityname = intent.getStringExtra("Activity");
                        if (Activityname != null) {

                            activityText.setText(activityText.getText().toString()+" "+Activityname);
                        }
                    }
                }
            }
    );

    @Override
    protected void setViewModel()
    {
    }
}