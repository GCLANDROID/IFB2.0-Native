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
import io.cordova.ifb.module.QueryModel;

public class IQueryReportAdapter extends RecyclerView.Adapter<IQueryReportAdapter.MyViewHolder> {
    ArrayList<QueryModel>reportList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.queryraw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvDate.setText(reportList.get(i).getDate());
        myViewHolder.tvProduct.setText(reportList.get(i).getProduct());
        myViewHolder.tvOtherProduct.setText(reportList.get(i).getOtherProduct());
        myViewHolder.tvContactPerson.setText(reportList.get(i).getContactPerson());
        myViewHolder.tvContactNumber.setText(reportList.get(i).getContactNumber());
        myViewHolder.tvLandLine.setText(reportList.get(i).getLandLineNumber());
        myViewHolder.tvEmail.setText(reportList.get(i).getEmailId());
        myViewHolder.tvCat.setText(reportList.get(i).getCustomerCat());
        myViewHolder.tvCatOther.setText(reportList.get(i).getOtherCat());
        myViewHolder.tvOrgName.setText(reportList.get(i).getOrgName());
        myViewHolder.tvAddress.setText(reportList.get(i).getAddress());
        myViewHolder.tvPinCode.setText(reportList.get(i).getPincoe());
        myViewHolder.tvRemarks.setText(reportList.get(i).getRemarks());


    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvProduct,tvOtherProduct,tvContactPerson,tvContactNumber,tvLandLine,tvEmail,tvCat,tvCatOther,tvAddress,tvPinCode,tvRemarks,tvOrgName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvProduct=(TextView)itemView.findViewById(R.id.tvProduct);
            tvOtherProduct=(TextView)itemView.findViewById(R.id.tvOtherProduct);
            tvContactPerson=(TextView)itemView.findViewById(R.id.tvContactPerson);
            tvContactNumber=(TextView)itemView.findViewById(R.id.tvContactNumber);
            tvLandLine=(TextView)itemView.findViewById(R.id.tvLandLine);
            tvEmail=(TextView)itemView.findViewById(R.id.tvEmail);
            tvCat=(TextView)itemView.findViewById(R.id.tvCat);
            tvCatOther=(TextView)itemView.findViewById(R.id.tvCatOther);
            tvRemarks=(TextView)itemView.findViewById(R.id.tvRemarks);
            tvAddress=(TextView)itemView.findViewById(R.id.tvAddress);
            tvPinCode=(TextView)itemView.findViewById(R.id.tvPinCode);
            tvOrgName=(TextView)itemView.findViewById(R.id.tvOrgName);

        }
    }

    public IQueryReportAdapter(ArrayList<QueryModel> reportList, Context context) {
        this.reportList = reportList;
        this.context = context;
    }
}
