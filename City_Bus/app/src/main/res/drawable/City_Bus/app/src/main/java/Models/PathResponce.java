package Models;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PathResponce {


    @SerializedName("destStation")
    @Expose
    private String destStation;
    @SerializedName("busNumbers")
    @Expose
    private List<String> busNumbers;

    public String getDestStation() {
        return destStation;
    }

    public void setDestStation(String destStation) {
        this.destStation = destStation;
    }

    public List<String> getBusNumbers() {
        return busNumbers;
    }

    public void setBusNumbers(List<String> busNumbers) {
        this.busNumbers = busNumbers;
    }


}

