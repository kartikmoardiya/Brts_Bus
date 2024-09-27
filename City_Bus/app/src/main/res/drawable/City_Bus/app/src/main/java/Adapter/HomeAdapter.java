package Adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.city_bus.R;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
    ArrayList<Pair<String,String>> busAndStations = new ArrayList<>();
    Context context;

    public HomeAdapter(ArrayList<Pair<String, String>> busAndStations, Context context) {
        this.busAndStations = busAndStations;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.homeTxtBus.setText(busAndStations.get(position).first);
        holder.homeTxtStation.setText(busAndStations.get(position).second);
    }

    @Override
    public int getItemCount() {
        return busAndStations.size();
    }

    public static class MyViewHolder  extends RecyclerView.ViewHolder{

        TextView homeTxtBus, homeTxtStation;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTxtBus = itemView.findViewById(R.id.homeTxtBus);
            homeTxtStation = itemView.findViewById(R.id.homeTxtStation);
        }
    }
}
