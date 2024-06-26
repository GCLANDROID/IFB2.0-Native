package io.cordova.ifb.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.ReplashedReportModel;

public class ReplenshiedReportAdapter extends RecyclerView.Adapter<ReplenshiedReportAdapter.MyViewHolder> {
    ArrayList<ReplashedReportModel>reportList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.replenished_report_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvReplacementDate.setText(reportList.get(i).getRepalcementDate());
        myViewHolder.tvProduct.setText(reportList.get(i).getProductName());
        myViewHolder.tvModel.setText(reportList.get(i).getModelName());




    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvReplacementDate,tvProduct,tvModel;
        ImageView imgUpdate,imgDelete;
        LinearLayout llStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReplacementDate=(TextView)itemView.findViewById(R.id.tvReplacementDate);
            tvProduct=(TextView)itemView.findViewById(R.id.tvProduct);
            tvModel=(TextView)itemView.findViewById(R.id.tvModel);

            imgUpdate=(ImageView)itemView.findViewById(R.id.imgUpdate);
            imgDelete=(ImageView)itemView.findViewById(R.id.imgDelete);

            llStatus=(LinearLayout)itemView.findViewById(R.id.llStatus);


        }
    }

    public ReplenshiedReportAdapter(ArrayList<ReplashedReportModel> reportList, Context context) {
        this.reportList = reportList;
        this.context=context;
    }
}
