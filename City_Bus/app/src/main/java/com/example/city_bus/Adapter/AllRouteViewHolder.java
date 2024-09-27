package com.example.city_bus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.city_bus.R;

import java.util.ArrayList;

public class AllRouteViewHolder extends RecyclerView.Adapter<AllRouteViewHolder.CustomViewHolder>{
    public Context context;
    public ArrayList<String> allRoutes;

    public AllRouteViewHolder(Context context, ArrayList<String> allRoutes) {
        this.context = context;
        this.allRoutes = allRoutes;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textViewItem.setText(allRoutes.get(position));
    }

    @Override
    public int getItemCount() {
        return allRoutes.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewItem;

        public CustomViewHolder(View itemView) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.text_view_item);
        }
    }
}
