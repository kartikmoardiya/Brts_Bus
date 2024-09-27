package com.example.city_bus.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllBuses {
    @SerializedName("buses")
    @Expose
    private List<String> buses;

    public List<String> getBuses() {
        return buses;
    }

    public void setBuses(List<String> buses) {
        this.buses = buses;
    }
}
