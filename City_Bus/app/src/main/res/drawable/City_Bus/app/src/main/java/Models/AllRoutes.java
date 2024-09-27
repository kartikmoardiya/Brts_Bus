package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllRoutes {
    @SerializedName("allStations")
    @Expose
    private List<String> allStations;

    public List<String> getAllStations() {
        return allStations;
    }

    public void setAllStations(List<String> allStations) {
        this.allStations = allStations;
    }
}
