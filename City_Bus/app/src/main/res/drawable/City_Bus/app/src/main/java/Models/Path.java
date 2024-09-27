package Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Path {
    @SerializedName("path")
    @Expose
    private List<PathResponce> path;

    public List<PathResponce> getPath() {
        return path;
    }

    public void setPath(List<PathResponce> path) {
        this.path = path;
    }
}
