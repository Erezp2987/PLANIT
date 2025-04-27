package com.erez_p.tashtit.ACTIVITIES;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import androidx.lifecycle.ViewModelProvider;

import com.erez_p.model.Flight;
import com.erez_p.model.HotelItem;
import com.erez_p.model.Trip;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.R;
import com.erez_p.viewmodel.TripsViewModel;

import java.util.Calendar;

public class Trip_Plan_Activity extends BaseActivity {
    private Button confirm, returnBack, chooseFlight, chooseHotel;
    private TextView flightText, hotelText;
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
        Intent intent = getIntent();
        tripId = intent.getStringExtra("tripId");
        hotelText.setText("Selected Hotel: ");
        flightText.setText("Selected Flight: ");
    }

    @Override
    protected void setListeners() {
        //Delete/Cancel TRIP
        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hotelText.getText()!=null && !hotelText.getText().toString().equals(""))
                {
                    //cancel after hotel selected
                }
                if(flightText.getText()!=null && !flightText.getText().toString().equals(""))
                {
                    //cancel after flight selected
                }
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Create new Trip object with all the collected information
                    Intent intent1 = new Intent(Trip_Plan_Activity.this, Home_Screen.class);
                    startActivity(intent1);
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
                }
            }
    );

    @Override
    protected void setViewModel()
    {

    }
}