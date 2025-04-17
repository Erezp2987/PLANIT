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

import com.erez_p.model.HotelItem;
import com.erez_p.model.HotelResponse;
import com.erez_p.model.Property;
import com.erez_p.repository.RetrofitClient;
import com.erez_p.repository.SerpApiService;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;
import com.erez_p.tashtit.ADPTERS.HotelAdapter;
import com.erez_p.tashtit.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Hotel_Activity extends BaseActivity {

    private EditText editTextQuery, editTextCheckIn, editTextCheckOut, editTextAdults, editTextMaxPrice;
    private Button buttonSearch, returnButton;
    private RecyclerView recyclerViewHotels;
    private HotelAdapter hotelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        initializeViews();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        editTextQuery = findViewById(R.id.editTextQuery);
        editTextCheckIn = findViewById(R.id.editTextCheckIn);
        editTextCheckOut = findViewById(R.id.editTextCheckOut);
        editTextAdults = findViewById(R.id.editTextAdults);
        editTextMaxPrice = findViewById(R.id.editTextMaxPrice);
        buttonSearch = findViewById(R.id.buttonSearch);
        recyclerViewHotels = findViewById(R.id.recyclerViewHotels);
        returnButton = findViewById(R.id.returnbutton);

        recyclerViewHotels.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void setListeners() {
        buttonSearch.setOnClickListener(view -> fetchHotels());
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void setViewModel() {

    }

    private void fetchHotels() {
        String query = editTextQuery.getText().toString();
        String checkIn = editTextCheckIn.getText().toString();
        String checkOut = editTextCheckOut.getText().toString();
        int adults = Integer.parseInt(editTextAdults.getText().toString());
        int maxPrice = Integer.parseInt(editTextMaxPrice.getText().toString());

        List<HotelItem> hotelItems = new ArrayList<>();
        HotelAdapter adapter = new HotelAdapter(hotelItems, R.layout.hotel_item_layout,
                holder -> {
            holder.putView("hotelName", holder.itemView.findViewById(R.id.hotel_name1));
            holder.putView("hotelPrice", holder.itemView.findViewById(R.id.hotel_price1));
        },
                (holder, hotelItem, position) -> {
                    ((TextView) holder.getView("hotelName")).setText("Hotel Name: " + hotelItem.getName());
                    ((TextView) holder.getView("hotelPrice")).setText("Price: " + hotelItem.getPrice());
                });
        adapter.setOnItemLongClickListener(new GenericAdapter.OnItemLongClickListener<HotelItem>() {
            @Override
            public boolean onItemLongClick(HotelItem item, int position) {
                //החזרת מלון
                Intent intent = new Intent(Hotel_Activity.this,Trip_Plan_Activity.class);
                intent.putExtra("HotelItem",item);
                setResult(200,intent);
                finish();
                return false;
            }
        });
        adapter.setOnItemSwipeListener(new GenericAdapter.OnItemSwipeListener<HotelItem>() {
            @Override
            public void onItemSwipeRight(HotelItem item, int position) {
                String url = "https://www.google.com/maps?q=" + item.getLatitude() + "," + item.getLongtitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

            }

            @Override
            public void onItemSwipeLeft(HotelItem item, int position) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                startActivity(browserIntent);

            }
        });
        recyclerViewHotels.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        SerpApiService service = RetrofitClient.getService();
        Call<HotelResponse> call = service.getHotels("google_hotels", query, checkIn, checkOut, adults, maxPrice, "8", "e6030086b12c1c7c7fda68d5768fb563c679da80726e596b079e03ba0473c929");

        call.enqueue(new Callback<HotelResponse>() {
            @Override
            public void onResponse(Call<HotelResponse> call, Response<HotelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HotelResponse hotelResponse = response.body();
                    hotelItems.clear();

                    if (hotelResponse.getProperties() != null) {
                        for (Property brand : hotelResponse.getProperties()) {
                            String link = brand.getLink(); // Assuming API provides a link for brands
                            Log.d("LOngtitude+vvdvd:",""+brand.getCordinates().getLatitude()+" "+brand.getCordinates().getLongitude()+" ");
                            hotelItems.add(new HotelItem(brand.getName(), brand.getRatePerNight().getLowest(),link,brand.getCordinates().getLongitude(), brand.getCordinates().getLatitude()));
                        }
                    } else if (hotelResponse.getType() != null && hotelResponse.getType().equals("hotel")) {
                        Log.d("LOngtitude+vvdvd:", "" + hotelResponse.getCordinates().getLatitude() + " " + hotelResponse.getCordinates().getLongitude() + " ");
                        if (hotelResponse.getRatePerNight() != null) {
                            hotelItems.add(new HotelItem(
                                    hotelResponse.getName(),
                                    hotelResponse.getRatePerNight().getLowest(),
                                    hotelResponse.getLink(),
                                    hotelResponse.getCordinates().getLongitude(),
                                    hotelResponse.getCordinates().getLatitude()
                            ));
                        }
                        else{
                            hotelItems.add(new HotelItem(
                                    hotelResponse.getName(),
                                    hotelResponse.getLink(),
                                    hotelResponse.getCordinates().getLongitude(),
                                    hotelResponse.getCordinates().getLatitude()
                            ));
                        }
                    }
                    for (HotelItem item : hotelItems) {
                        Log.d("RecyclerViewDebug", "Hotel Name: " + item.getName());
                    }
                    Log.d("RecyclerViewDebug", "Total Hotels: " + hotelItems.size());

                    if (recyclerViewHotels.getAdapter() == null) {
                        recyclerViewHotels.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    recyclerViewHotels.setVisibility(View.VISIBLE);
                } else {
                    Log.e("API_RESPONSE", "Empty Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<HotelResponse> call, Throwable t) {
                Log.e("API_FAILURE", "API Call failed", t);
            }
        });
    }
}
