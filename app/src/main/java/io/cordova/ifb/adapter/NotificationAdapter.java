package io.cordova.ifb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import io.cordova.ifb.R;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    JSONArray itemList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        final JSONObject jsonObject = itemList.optJSONObject(i);

        myViewHolder.tvName.setText(jsonObject.optString("Remarks"));


    }

    @Override
    public int getItemCount() {
        return itemList.length() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName=(TextView)itemView.findViewById(R.id.tvName);

        }
    }

    public NotificationAdapter(JSONArray itemList) {
        this.itemList = itemList;
    }
}
