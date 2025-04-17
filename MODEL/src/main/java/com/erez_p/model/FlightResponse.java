package com.erez_p.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FlightResponse implements Serializable {
    @SerializedName("best_flights")  // This matches the key in JSON
    private List<BestFlight> bestFlights;
    @SerializedName("other_flights")
    private List<OtherFlight>otherFlights;
    public List<BestFlight> getBestFlights() {
        return bestFlights;
    }

    public List<OtherFlight> getOtherFlights() {
        return otherFlights;
    }
}
