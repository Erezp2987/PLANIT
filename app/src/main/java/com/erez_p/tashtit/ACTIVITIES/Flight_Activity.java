package com.erez_p.tashtit.ACTIVITIES;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.erez_p.model.BestFlight;
import com.erez_p.model.FinalFlight;
import com.erez_p.model.Flight;
import com.erez_p.model.FlightResponse;
import com.erez_p.model.GeminiManager;
import com.erez_p.model.OtherFlight;
import com.erez_p.model.ResponseCallback;
import com.erez_p.repository.FlightApiService;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.ADPTERS.AirportAdapter;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;
import com.erez_p.tashtit.ADPTERS.FlightAdapter;
import com.erez_p.tashtit.R;
import com.erez_p.viewmodel.FlightViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Flight_Activity extends BaseActivity {
    private FlightViewModel flightViewModel;
    private EditText fromInput, toInput, dateInput;
    private Button searchButton,switchActivitys;
    private RecyclerView recyclerView;
    private FlightAdapter flightAdapter;
    private static final String BASE_URL = "https://serpapi.com/";
    private static final String API_KEY = "e6030086b12c1c7c7fda68d5768fb563c679da80726e596b079e03ba0473c929";
    private ProgressDialog progressDialog;
    private RecyclerView fromRecyclerView, toRecyclerView;
    private GeminiManager geminiManager = new GeminiManager();
    private List<String> codesListFrom = new ArrayList<>();
    private List<String> codesListTo = new ArrayList<>();
    private AirportAdapter fromAdapter, toAdapter;
    private Handler fromHandler = new Handler();
    private Handler toHandler = new Handler();
    private static final long DEBOUNCE_DELAY = 500; // 500ms delay
    private boolean fromInputChanged = false;
    private boolean toInputChanged = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        initializeViews();
        setViewModel();
        setAirportsRecyclerView();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        fromInput = findViewById(R.id.fromInput);
        switchActivitys=findViewById(R.id.buttonHotel);
        toInput = findViewById(R.id.toInput);
        dateInput = findViewById(R.id.dateInput);
        dateInput.setFocusable(false);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fromRecyclerView = findViewById(R.id.fromRecyclerView);
        toRecyclerView = findViewById(R.id.toRecyclerView);
        fromRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        toRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        dateInput.setOnClickListener(v -> showDatePickerDialog());
        fromInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fromInputChanged = true; // Mark as changed
                fromHandler.removeCallbacksAndMessages(null); // Cancel previous calls
                fromHandler.postDelayed(() -> {
                    String input = s.toString().trim();
                    if (!input.isEmpty()) {
                        String prompt = "send me the codes of the civilian airports of the country/city that i write here with a , between them if their are more then one code:" +
                                input + ". For example israel would be TLV. Give me just the codes without any other words";

                        geminiManager.getResponse(prompt, new ResponseCallback() {
                            @Override
                            public void onResponse(String response) {
                                runOnUiThread(() -> {
                                    codesListFrom.clear();
                                    String[] codes = response.split(",");
                                    for (String code : codes) {
                                        codesListFrom.add(code.trim());
                                    }
                                    fromAdapter.notifyDataSetChanged();
                                    if (fromInputChanged) { // Show only if text was changed
                                        fromRecyclerView.setVisibility(View.VISIBLE);
                                    }
                                });
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                // handle error
                            }
                        });
                    } else {
                        runOnUiThread(() -> fromRecyclerView.setVisibility(View.GONE));
                    }
                }, DEBOUNCE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        toInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                toInputChanged = true; // Mark as changed
                toHandler.removeCallbacksAndMessages(null); // Cancel previous calls
                toHandler.postDelayed(() -> {
                    String input = s.toString().trim();
                    if (!input.isEmpty()) {
                        String prompt = "send me the codes of the civilian airports of the country/city that i write here with a , between them if their are more then one code:" +
                                input + ". For example israel would be TLV. Give me just the codes without any other words";

                        geminiManager.getResponse(prompt, new ResponseCallback() {
                            @Override
                            public void onResponse(String response) {
                                runOnUiThread(() -> {
                                    codesListTo.clear();
                                    String[] codes = response.split(",");
                                    for (String code : codes) {
                                        codesListTo.add(code.trim());
                                    }
                                    toAdapter.notifyDataSetChanged();
                                    if (toInputChanged) { // Show only if text was changed
                                        toRecyclerView.setVisibility(View.VISIBLE);
                                    }
                                });
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                // handle error
                            }
                        });
                    } else {
                        runOnUiThread(() -> toRecyclerView.setVisibility(View.GONE));
                    }
                }, DEBOUNCE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


    }

    @Override
    protected void setViewModel() {
        flightViewModel = new ViewModelProvider(this).get(FlightViewModel.class);
        flightViewModel.getAll();
    }
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Ensure 2-digit month and day format
                    String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    dateInput.setText(formattedDate);
                },
                year, month, day
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    public void setAirportsRecyclerView(){
        fromAdapter = new AirportAdapter(codesListFrom, R.layout.item_airport,
                holder -> {
                    holder.putView("airportCode", holder.itemView.findViewById(R.id.tvAirportCode));
                },
                (holder, airportCode, position) -> {
                    ((TextView) holder.getView("airportCode")).setText(airportCode);
                });
            fromAdapter.setOnItemClickListener(new GenericAdapter.OnItemClickListener<String>() {
                @Override
                public void onItemClick(String item, int position) {
                    fromInput.setText(item);
                    fromInputChanged = false; // Reset the flag
                    fromRecyclerView.setVisibility(View.GONE);
                }
            });
        fromRecyclerView.setAdapter(fromAdapter);
        fromRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager
        toAdapter = new AirportAdapter(codesListTo, R.layout.item_airport,
                holder -> {
                    holder.putView("airportCode", holder.itemView.findViewById(R.id.tvAirportCode));
                },
                (holder, airportCode, position) -> {
                    ((TextView) holder.getView("airportCode")).setText(airportCode);
                });
        toAdapter.setOnItemClickListener(new GenericAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(String item, int position) {
                toInput.setText(item);
                toInputChanged = false; // Reset the flag
                toRecyclerView.setVisibility(View.GONE);
            }
        });
        toRecyclerView.setAdapter(toAdapter);
        toRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager
    }
    public void setRecyclerView(List<Flight> allFlights)
    {
        flightAdapter = new FlightAdapter(allFlights,
                R.layout.item_flight,
                holder -> {
                    holder.putView("flightImage", holder.itemView.findViewById(R.id.airlineLogoImageView));
                    holder.putView("airline", holder.itemView.findViewById(R.id.airlineTextView));
                    holder.putView("flightNumber", holder.itemView.findViewById(R.id.flightNumberTextView));
                    holder.putView("departure", holder.itemView.findViewById(R.id.departureTextView));
                    holder.putView("arrival", holder.itemView.findViewById(R.id.arrivalTextView));
                    holder.putView("duration", holder.itemView.findViewById(R.id.durationTextView));
                    holder.putView("price", holder.itemView.findViewById(R.id.priceTextView));
                    holder.putView("class", holder.itemView.findViewById(R.id.Class));
                },
                (holder, flight, position) -> {
                    // Set the image using Glide or any other image loading library
                    Glide.with(this)
                            .load(flight.getAirlineLogo())
                            .into((ImageView) holder.getView("flightImage"));
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
                Intent intentGot= getIntent();
                String tripId = intentGot.getStringExtra("tripId");
                Intent intent= new Intent(Flight_Activity.this,Trip_Plan_Activity.class);
                intent.putExtra("Flight",item.getFlightNumber());
                FinalFlight finalFlight = new FinalFlight(item.getDepartureAirport().getName(),item.getDepartureAirport().getTime(), item.getArrivalAirport().getName(),item.getArrivalAirport().getTime(), item.getDuration(),
                        item.getAirplane(), item.getAirline(), item.getAirlineLogo(), item.getTravelClass(), item.getFlightNumber(),
                        item.getLegroom(), item.getPrice(), tripId, dateInput.getText().toString());
                flightViewModel.add(finalFlight);
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
        progressDialog = new ProgressDialog(Flight_Activity.this);
        progressDialog.setMessage("Searching for flights...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FlightApiService service = retrofit.create(FlightApiService.class);
        Call<FlightResponse> call = service.getFlightInfo(fromInput.getText().toString(), toInput.getText().toString(),
                dateInput.getText().toString(),2,
                API_KEY, "google_flights", "en", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)",1,2);

        call.enqueue(new Callback<FlightResponse>() {
            @Override
            public void onResponse(Call<FlightResponse> call, Response<FlightResponse> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
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
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("API_FAILURE", "Request failed: " + t.getMessage());
            }
        });
    }
}
