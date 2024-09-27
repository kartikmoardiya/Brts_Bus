package Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.city_bus.MapActivity;
import com.example.city_bus.R;

import java.util.ArrayList;

import Models.Station;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.MyViewHolder>{
    ArrayList<Station> arrayList = new ArrayList<>();
    Context context;

    public StationAdapter(ArrayList<Station> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_recycler,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.routeRecTxt.setText(arrayList.get(position).getBusStation());
        holder.routeRecImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String tmp = arrayList.get(position).getBusStation();
                // Toast.makeText(context, "" + tmp, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, MapActivity.class);
                Log.d("Tagy","A "+arrayList.get(position).getLatitude());
                i.putExtra("latitude",arrayList.get(position).getLatitude());
                i.putExtra("longitude",arrayList.get(position).getLongitude());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView routeRecTxt;
        ImageView routeRecImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            routeRecTxt = itemView.findViewById(R.id.routeRecTxt);
            routeRecImg = itemView.findViewById(R.id.routeRecImg);
        }
    }
}
