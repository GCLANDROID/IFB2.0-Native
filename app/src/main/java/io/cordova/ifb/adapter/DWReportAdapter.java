package io.cordova.ifb.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.DWReportModel;

public class DWReportAdapter extends RecyclerView.Adapter<DWReportAdapter.MyViewHolder> {
    ArrayList<DWReportModel>itemList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dwreport_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvDate.setText(itemList.get(i).getDate());
        myViewHolder.tvCusName.setText(itemList.get(i).getCusNAME());
        myViewHolder.tvCusMob.setText(itemList.get(i).getCusMob());
        myViewHolder.tvCusAddress.setText(itemList.get(i).getCusAddress());
        myViewHolder.tvCatName.setText(itemList.get(i).getCategory());
        myViewHolder.tvModelName.setText(itemList.get(i).getModel());
        myViewHolder.tvAdvanced.setText(itemList.get(i).getAdvanced());
        myViewHolder.tvAmt.setText(itemList.get(i).getAmt());
        myViewHolder.tvRemarks.setText(itemList.get(i).getRemarks());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvCusName,tvCusMob,tvCusAddress,tvCatName,tvModelName,tvAdvanced,tvAmt,tvRemarks;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvCusName=(TextView)itemView.findViewById(R.id.tvCusName);
            tvCusMob=(TextView)itemView.findViewById(R.id.tvCusMob);
            tvModelName=(TextView)itemView.findViewById(R.id.tvModelName);
            tvCusAddress=(TextView)itemView.findViewById(R.id.tvCusAddress);
            tvCatName=(TextView)itemView.findViewById(R.id.tvCatName);
            tvAdvanced=(TextView)itemView.findViewById(R.id.tvAdvanced);
            tvAmt=(TextView)itemView.findViewById(R.id.tvAmt);
            tvRemarks=(TextView)itemView.findViewById(R.id.tvRemarks);

        }
    }

    public DWReportAdapter(ArrayList<DWReportModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
