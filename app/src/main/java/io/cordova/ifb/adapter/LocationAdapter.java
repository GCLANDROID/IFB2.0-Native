package io.cordova.ifb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.LocationModel;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    ArrayList<LocationModel>itemList=new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvTime.setText(itemList.get(i).getTime());
        myViewHolder.tvLocation.setText(itemList.get(i).getAddress());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime,tvLocation;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);
            tvLocation=(TextView)itemView.findViewById(R.id.tvLocation);

        }
    }

    public LocationAdapter(ArrayList<LocationModel> itemList) {
        this.itemList = itemList;
    }
}
