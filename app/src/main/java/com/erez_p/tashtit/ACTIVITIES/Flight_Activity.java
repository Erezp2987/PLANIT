package com.erez_p.tashtit.ACTIVITIES;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erez_p.model.BestFlight;
import com.erez_p.model.Flight;
import com.erez_p.model.FlightResponse;
import com.erez_p.model.OtherFlight;
import com.erez_p.repository.FlightApiService;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;
import com.erez_p.tashtit.ADPTERS.FlightAdapter;
import com.erez_p.tashtit.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Flight_Activity extends BaseActivity {
    private EditText fromInput, toInput, dateInput, returnDateInput;
    private Button searchButton,switchActivitys;
    private RecyclerView recyclerView;
    private FlightAdapter flightAdapter;
    private static final String BASE_URL = "https://serpapi.com/";
    private static final String API_KEY = "e6030086b12c1c7c7fda68d5768fb563c679da80726e596b079e03ba0473c929";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        initializeViews();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        fromInput = findViewById(R.id.fromInput);
        switchActivitys=findViewById(R.id.buttonHotel);
        toInput = findViewById(R.id.toInput);
        dateInput = findViewById(R.id.dateInput);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);
        returnDateInput = findViewById(R.id.returnDateInput);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void setListeners() {
        switchActivitys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        searchButton.setOnClickListener(v -> searchFlights());
    }

    @Override
    protected void setViewModel() {

    }

    public void setRecyclerView(List<Flight> allFlights)
    {
        flightAdapter = new FlightAdapter(allFlights,
                R.layout.item_flight,
                holder -> {
                    holder.putView("airline", holder.itemView.findViewById(R.id.airlineTextView));
                    holder.putView("flightNumber", holder.itemView.findViewById(R.id.flightNumberTextView));
                    holder.putView("departure", holder.itemView.findViewById(R.id.departureTextView));
                    holder.putView("arrival", holder.itemView.findViewById(R.id.arrivalTextView));
                    holder.putView("duration", holder.itemView.findViewById(R.id.durationTextView));
                    holder.putView("price", holder.itemView.findViewById(R.id.priceTextView));
                    holder.putView("class", holder.itemView.findViewById(R.id.Class));
                },
                (holder, flight, position) -> {
                    ((TextView) holder.getView("airline")).setText("Airline: " + flight.getAirline());
                    ((TextView) holder.getView("flightNumber")).setText("Flight No: " + flight.getFlightNumber());
                    ((TextView) holder.getView("departure")).setText("Departure: " + flight.getDepartureAirport().getName() + " at " + flight.getDepartureAirport().getTime());
                    ((TextView) holder.getView("arrival")).setText("Arrival: " + flight.getArrivalAirport().getName() + " at " + flight.getArrivalAirport().getTime());
                    ((TextView) holder.getView("duration")).setText("Duration: " + flight.getDuration() + " min");
                    ((TextView) holder.getView("price")).setText("Price: $" + flight.getPrice());
                    ((TextView) holder.getView("class")).setText("Class: " + flight.getTravelClass());
                });
        flightAdapter.setOnItemLongClickListener(new GenericAdapter.OnItemLongClickListener<Flight>() {
            @Override
            public boolean onItemLongClick(Flight item, int position) {
                //מחיזרה טיסה
                Intent intent= new Intent(Flight_Activity.this,Trip_Plan_Activity.class);
                intent.putExtra("Flight",item);
                setResult(100,intent);
                finish();
                return false;
            }
        });
        flightAdapter.setOnItemClickListener(new GenericAdapter.OnItemClickListener<Flight>() {
            @Override
            public void onItemClick(Flight item, int position) {
                String url = "https://www.google.com/search?q=" + item.getAirline() + "+" + item.getFlightNumber() + "+flight+status";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(flightAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager
    }

    private void searchFlights() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FlightApiService service = retrofit.create(FlightApiService.class);
        Call<FlightResponse> call = service.getFlightInfo(fromInput.getText().toString(), toInput.getText().toString(),
                dateInput.getText().toString(), returnDateInput.getText().toString(),
                API_KEY, "google_flights", "en", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)",1);

        call.enqueue(new Callback<FlightResponse>() {
            @Override
            public void onResponse(Call<FlightResponse> call, Response<FlightResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FlightResponse flightResponse = response.body();
                    List<BestFlight> bestFlights = flightResponse.getBestFlights();
                    List<OtherFlight> otherFlights = flightResponse.getOtherFlights(); // ✅ Get other_flights

                    List<Flight> allFlights = new ArrayList<>(); // ✅ Store all flights

                    // ✅ Process best_flights
                    if (bestFlights != null && !bestFlights.isEmpty()) {
                        for (BestFlight bestFlight : bestFlights) {
                            int price = bestFlight.getPrice(); // 💰 Get price for best_flight

                            for (Flight flight : bestFlight.getFlights()) {
                                flight.setPrice(price); // ✅ Assign correct price to each flight
                                allFlights.add(flight);
                            }
                        }
                    }

                    // ✅ Process other_flights
                    if (otherFlights != null && !otherFlights.isEmpty()) {
                        for (OtherFlight otherFlight : otherFlights) {
                            int price = otherFlight.getPrice(); // 💰 Get price for other_flight

                            for (Flight flight : otherFlight.getFlights()) {
                                flight.setPrice(price); // ✅ Assign correct price
                                allFlights.add(flight);
                            }
                        }
                    }
                    setRecyclerView(allFlights);
                } else {
                    Log.e("API_ERROR", "Response Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<FlightResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Request failed: " + t.getMessage());
            }
        });
    }
}
