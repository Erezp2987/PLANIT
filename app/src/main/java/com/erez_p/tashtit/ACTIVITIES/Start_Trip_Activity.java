package com.erez_p.tashtit.ACTIVITIES;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.erez_p.model.Trip;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.R;
import com.erez_p.viewmodel.TripsViewModel;

import java.util.Calendar;

public class Start_Trip_Activity extends BaseActivity {
    private Button suggestions, start;
    EditText tripName, tripDateDeparture, tripDateReturn;
    String departureDate, returnDate;
    private TripsViewModel tripsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_trip);
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
        suggestions = findViewById(R.id.btnTripSuggestions);
        start = findViewById(R.id.btnPlanTrip);
        tripName=findViewById(R.id.etTripName);
        tripDateDeparture=findViewById(R.id.etDepartureDate);
        tripDateReturn=findViewById(R.id.etReturnDate);
        tripDateReturn.setFocusable(false);
        tripDateDeparture.setFocusable(false);
    }

    @Override
    protected void setListeners() 
    {
        //AI NIR (FOR TRIP SUGGESTIONS)
        suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                
            }
        });
        tripDateReturn.setOnClickListener(v -> showDatePickerDialog(false));
        tripDateDeparture.setOnClickListener(v -> showDatePickerDialog(true));
        //NEW TRIP
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tripName.getText().toString()!="" && tripDateDeparture.getText().toString()!="" && tripDateReturn.getText().toString()!="") {
                    Intent intentFromLastActivity = getIntent();
                    String userId = intentFromLastActivity.getStringExtra("userId");
                    Intent intent = new Intent(Start_Trip_Activity.this, Trip_Plan_Activity.class);
                    Trip trip = new Trip(tripName.getText().toString(),
                            departureDate,
                            returnDate,
                            userId);
                    tripsViewModel.add(trip);
                    intent.putExtra("tripId", trip.getIdFs());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Start_Trip_Activity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
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
                            tripDateDeparture.setText("Departure: " + selectedDate);
                        } else {
                            returnDate = selectedDate;
                            tripDateReturn.setText("Return: " + selectedDate);
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

    @Override
    protected void setViewModel() {
        tripsViewModel = new ViewModelProvider(this).get(TripsViewModel.class);
        tripsViewModel.getAll();
    }
}