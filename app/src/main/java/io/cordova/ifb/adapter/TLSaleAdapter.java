package io.cordova.ifb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.TLSaleModel;

public class TLSaleAdapter extends RecyclerView.Adapter<TLSaleAdapter.MyViewHolder> {
    ArrayList<TLSaleModel>itemList=new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tlraw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvMonthName.setText(itemList.get(i).getMonthname());
        myViewHolder.tvYear.setText(itemList.get(i).getYear());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvMonthName,tvYear;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonthName=(TextView)itemView.findViewById(R.id.tvMonthName);
            tvYear=(TextView)itemView.findViewById(R.id.tvYear);

        }
    }

    public TLSaleAdapter(ArrayList<TLSaleModel> itemList) {
        this.itemList = itemList;
    }
}
