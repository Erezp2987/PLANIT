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

import com.erez_p.model.Flight;
import com.erez_p.model.HotelItem;
import com.erez_p.model.Trip;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.R;

import java.util.Calendar;

public class Trip_Plan_Activity extends BaseActivity {
    private Button confirm, returnBack, chooseFlight, chooseHotel, departureDateButton, returnDateButton;
    private TextView flightText, hotelText;
    private EditText tripNameEditText;
    HotelItem hotel;
    Flight flight;
    private String departureDate, returnDate;

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
        chooseHotel = findViewById(R.id.selectHotelButton);
        chooseFlight = findViewById(R.id.selectFlightButton);
        flightText = findViewById(R.id.flightSelectionText);
        hotelText = findViewById(R.id.hotelSelectionText);

        // Initialize new UI elements
        tripNameEditText = findViewById(R.id.tripNameEditText);
        departureDateButton = findViewById(R.id.departureDateButton);
        returnDateButton = findViewById(R.id.returnDateButton);
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
        //Confirm TRIP
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripName = tripNameEditText.getText().toString().trim();
                if (tripName.isEmpty()) {
                    tripNameEditText.setError("Please enter a trip name");
                    return;
                }

                if (departureDate == null || departureDate.isEmpty()) {
                    Toast.makeText(Trip_Plan_Activity.this, "Please select a departure date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (returnDate == null || returnDate.isEmpty()) {
                    Toast.makeText(Trip_Plan_Activity.this, "Please select a return date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(flight!=null && hotel!=null)
                {
                    // Create new Trip object with all the collected information
                    // Trip newTrip = new Trip(tripName, departureDate, returnDate, flight, hotel);
                    // Save or use the trip as needed

                }
                else {
                    Toast.makeText(Trip_Plan_Activity.this, "Please select both flight and hotel", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // ADD HOTEL TO TRIP
        chooseHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trip_Plan_Activity.this, Hotel_Activity.class);
                resultLauncher.launch(intent);
            }
        });
        //ADD FLIGHT TO TRIP
        chooseFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trip_Plan_Activity.this, Flight_Activity.class);
                resultLauncher.launch(intent);
            }
        });

        // Set listeners for new date picker buttons
        departureDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(true);
            }
        });

        returnDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(false);
            }
        });
    }

    // Method to show date picker dialog
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
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        if (isDeparture) {
                            departureDate = selectedDate;
                            departureDateButton.setText("Departure: " + selectedDate);
                        } else {
                            returnDate = selectedDate;
                            returnDateButton.setText("Return: " + selectedDate);
                        }
                    }
                },
                year,
                month,
                day
        );

        // Set min date for return date picker if departure date is already selected
        if (!isDeparture && departureDate != null && !departureDate.isEmpty()) {
            try {
                String[] dateParts = departureDate.split("/");
                if (dateParts.length == 3) {
                    Calendar minDate = Calendar.getInstance();
                    minDate.set(Integer.parseInt(dateParts[2]),
                            Integer.parseInt(dateParts[1]) - 1,
                            Integer.parseInt(dateParts[0]));
                    datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        datePickerDialog.show();
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 200) {
                        //hotel
                        Intent intent = result.getData();
                        hotel = (HotelItem) intent.getSerializableExtra("HotelItem");
                        if (hotel != null) {
                            hotelText.setText("Selected Hotel: " + hotel.getName());
                        }
                    }
                    if(result.getResultCode() == 100)
                    {
                        // flight
                        Intent intent = result.getData();
                        flight = (Flight) intent.getSerializableExtra("Flight");
                        if (flight != null) {
                            flightText.setText("Selected Flight: " + flight.getFlightNumber());
                        }
                    }
                }
            }
    );

    @Override
    protected void setViewModel()
    {
        // Your existing implementation
    }
}