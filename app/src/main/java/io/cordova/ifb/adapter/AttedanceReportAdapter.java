package io.cordova.ifb.adapter;

import android.os.Build;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.ReportModule;

public class AttedanceReportAdapter extends RecyclerView.Adapter<AttedanceReportAdapter.MyViewHolder> {
    ArrayList<ReportModule>reportList=new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendancereport_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvDate.setText(reportList.get(i).getDate());
        myViewHolder.tvEmpId.setText(reportList.get(i).getEmpid());
        myViewHolder.tvDay.setText(reportList.get(i).getDay());
        if (!reportList.get(i).getTime().equals("")) {
            myViewHolder.tvTime.setText(reportList.get(i).getTime());
        }else {
            myViewHolder.tvTime.setText("--");
        }
        if (!reportList.get(i).getLocation().equals("")) {
            myViewHolder.tvLocation.setText(reportList.get(i).getLocation());
        }else {
            myViewHolder.tvLocation.setText("--");
        }

        if (!reportList.get(i).getType().equals("")) {
            myViewHolder.tvType.setText(reportList.get(i).getType());
        }else {
            myViewHolder.tvType.setText("--");
        }

        myViewHolder.tvCheckOut.setText(reportList.get(i).getCheckOutStatus());

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvEmpId,tvDay,tvTime,tvLocation,tvType,tvCheckOut;
        LinearLayout lnItem,lnChekcOut;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvEmpId=(TextView)itemView.findViewById(R.id.tvEmpId);
            tvDay=(TextView)itemView.findViewById(R.id.tvDay);
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);
            tvLocation=(TextView)itemView.findViewById(R.id.tvLocation);
            tvType=(TextView)itemView.findViewById(R.id.tvType);
            lnItem=(LinearLayout) itemView.findViewById(R.id.lnItem);
            tvCheckOut=(TextView)itemView.findViewById(R.id.tvCheckOut);

        }
    }

    public AttedanceReportAdapter(ArrayList<ReportModule> reportList) {
        this.reportList = reportList;
    }
}
