package io.cordova.ifb.Location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.Util;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder>{
    Context context;
    List<LocationModel> itemLocation;

    public LocationAdapter(Context context, List<LocationModel> itemLocation) {
        this.context = context;
        this.itemLocation = itemLocation;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvID.setText(String.valueOf(itemLocation.get(position).id));
        holder.tvID2.setText(String.valueOf(itemLocation.get(position).id));
        holder.tvLongitude.setText(itemLocation.get(position).getLongitude());
        holder.tvLatitude.setText(itemLocation.get(position).getLatitude());
        holder.tvDateTime.setText(Util.changeAnyDateFormat(itemLocation.get(position).getDate(),"yyyy-MM-dd'T'HH:mm:ss","d MMMM yyyy, hh:mm:ss a"));
    }

    @Override
    public int getItemCount() {
        return itemLocation.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvLongitude, tvLatitude,tvID,tvDateTime,tvID2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tvID);
            tvID2 = itemView.findViewById(R.id.tvID2);
            tvLongitude = itemView.findViewById(R.id.tvLongitude);
            tvLatitude = itemView.findViewById(R.id.tvLatitude);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }
}
