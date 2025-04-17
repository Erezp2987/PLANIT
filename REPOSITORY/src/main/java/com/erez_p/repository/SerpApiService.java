package com.erez_p.repository;

import com.erez_p.model.HotelResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SerpApiService {
    @GET("search.json")
    Call<HotelResponse> getHotels(
            @Query("engine") String engine,
            @Query("q") String query,
            @Query("check_in_date") String checkInDate,
            @Query("check_out_date") String checkOutDate,
            @Query("adults") int adults,
            @Query("max_price") int maxPrice,
            @Query("sort_by") String sortBy,
            @Query("api_key") String apiKey
    );
}
