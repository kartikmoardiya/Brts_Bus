package com.example.city_bus.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusDetail {


    @SerializedName("busStations")
    @Expose
    private BusStations busStations;

    public BusStations getBusStations() {
        return busStations;
    }

    public void setBusStations(BusStations busStations) {
        this.busStations = busStations;
    }
}
