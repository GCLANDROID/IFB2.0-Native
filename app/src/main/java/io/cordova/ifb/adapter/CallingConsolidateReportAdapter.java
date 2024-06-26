package io.cordova.ifb.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.databinding.CallingConsolidateRawBinding;
import io.cordova.ifb.module.CallingConsolidateReportModel;

public class CallingConsolidateReportAdapter extends RecyclerView.Adapter<CallingConsolidateReportAdapter.MyViewHolder> {
    ArrayList<CallingConsolidateReportModel>reportList=new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CallingConsolidateRawBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.calling_consolidate_raw, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.binding.tvCalled.setText(reportList.get(i).getCalled());
        myViewHolder.binding.tvDate.setText(reportList.get(i).getDate());
        myViewHolder.binding.tvConnected.setText(reportList.get(i).getCallConnected());
        myViewHolder.binding.tvNotConnected.setText(reportList.get(i).getCallNotConnected());

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CallingConsolidateRawBinding binding;

        public MyViewHolder(@NonNull CallingConsolidateRawBinding binding) {
            super(binding.lnMain);
            this.binding=binding;

        }
    }

    public CallingConsolidateReportAdapter(ArrayList<CallingConsolidateReportModel> reportList) {
        this.reportList = reportList;
    }
}
