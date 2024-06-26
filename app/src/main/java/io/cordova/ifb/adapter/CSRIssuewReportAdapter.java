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
import io.cordova.ifb.module.IssueModel;

public class CSRIssuewReportAdapter extends RecyclerView.Adapter<CSRIssuewReportAdapter.MyViewHolder> {
    ArrayList<IssueModel>reportList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.csrissuereport_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvDate.setText(reportList.get(i).getDate());
        myViewHolder.tvRemarks.setText(reportList.get(i).getRemarks());
        myViewHolder.tvType.setText(reportList.get(i).getIssueType());
        myViewHolder.tvReqStatusRemrks.setText(reportList.get(i).getReqRemarks());
        myViewHolder.tvReqStatus.setText(reportList.get(i).getReqStatus());



    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvType,tvRemarks,tvDate,tvReqStatusRemrks,tvReqStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvRemarks=(TextView)itemView.findViewById(R.id.tvRemarks);
            tvType=(TextView)itemView.findViewById(R.id.tvType);
            tvReqStatusRemrks=(TextView)itemView.findViewById(R.id.tvReqStatusRemrks);
            tvReqStatus=(TextView)itemView.findViewById(R.id.tvReqStatus);

        }
    }

    public CSRIssuewReportAdapter(ArrayList<IssueModel> reportList, Context context) {
        this.reportList = reportList;
        this.context = context;
    }
}
