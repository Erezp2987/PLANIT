package com.erez_p.repository;

import com.erez_p.model.FlightResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface FlightApiService {
    @GET("search")
    Call<FlightResponse> getFlightInfo(
            @Query("departure_id") String departureAirport,
            @Query("arrival_id") String arrivalAirport,
            @Query("outbound_date") String departureDate,
            @Query("return_date") String returnDate,
            @Query("api_key") String apiKey,
            @Query("engine") String engine,
            @Query("hl") String hl,
            @Header("User-Agent") String userAgent,
            @Query("stops") int stops
    );
}
