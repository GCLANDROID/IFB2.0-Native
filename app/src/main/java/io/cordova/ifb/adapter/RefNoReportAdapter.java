package io.cordova.ifb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.RefNoModel;

public class RefNoReportAdapter extends RecyclerView.Adapter<RefNoReportAdapter.MyViewHolder> {
    ArrayList<RefNoModel>reportList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.refreport_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        myViewHolder.tvDate.setText(reportList.get(i).getDate());
        myViewHolder.tvModelName.setText(reportList.get(i).getModel());
        myViewHolder.tvCustomerName.setText(reportList.get(i).getCusName());
        myViewHolder.tvCustomerPhn.setText(reportList.get(i).getCusPhn());
        myViewHolder.tvCustomerEmail.setText(reportList.get(i).getCusEmail());
        myViewHolder.tvRefNo.setText(reportList.get(i).getRefno());
        myViewHolder.tvDeliveryDate.setText(reportList.get(i).getDelieryDate());
        myViewHolder.tvRemarks.setText(reportList.get(i).getDeliveryStatus());
        if (reportList.get(i).getCancel().equals("") || reportList.get(i).getCancel().equals("null")){
            myViewHolder.tvCancel.setVisibility(View.GONE);
        }else {
            myViewHolder.tvCancel.setVisibility(View.VISIBLE);
        }


        myViewHolder.llCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(reportList.get(i).getInvoicecopyUrl()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
        if (reportList.get(i).getInvoicecopyUrl().equals("")||reportList.get(i).getInvoicecopyUrl().equals("null")){

            myViewHolder.llCopy.setVisibility(View.GONE);


        }else {
            myViewHolder.llCopy.setVisibility(View.VISIBLE);
        }
        myViewHolder.tvStatus.setText(reportList.get(i).getApprovalStatus());
        myViewHolder.tvTiketNo.setText(reportList.get(i).getTicketNumber());
        myViewHolder.tvTokenNo.setText(reportList.get(i).getTokenNumber());
        myViewHolder.tvCategoryName.setText(reportList.get(i).getCategoryName());
        myViewHolder.tvCSDSale.setText(reportList.get(i).getCsdSales());
        myViewHolder.tvCusConfStatus.setText(reportList.get(i).getCust_Conf_Stats());


    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvModelName,tvCustomerName,tvCustomerPhn,tvCustomerEmail,tvRefNo,tvDeliveryDate,tvRemarks,tvCancel,tvStatus,tvTokenNo,tvTiketNo,tvCategoryName,tvCSDSale,tvCusConfStatus;
        LinearLayout llCopy;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvRefNo=(TextView)itemView.findViewById(R.id.tvRefNo);
            tvModelName=(TextView)itemView.findViewById(R.id.tvModelName);
            tvCustomerName=(TextView)itemView.findViewById(R.id.tvCustomerName);
            tvCustomerPhn=(TextView)itemView.findViewById(R.id.tvCustomerPhn);
            tvCustomerEmail=(TextView)itemView.findViewById(R.id.tvCustomerEmail);
            tvDeliveryDate=(TextView)itemView.findViewById(R.id.tvDeliveryDate);
            tvRemarks=(TextView)itemView.findViewById(R.id.tvRemarks);
            tvCancel=(TextView)itemView.findViewById(R.id.tvCancel);
            tvStatus=(TextView)itemView.findViewById(R.id.tvStatus);
            tvTokenNo=(TextView)itemView.findViewById(R.id.tvTokenNo);
            tvTiketNo=(TextView)itemView.findViewById(R.id.tvTiketNo);
            tvCategoryName=(TextView)itemView.findViewById(R.id.tvCategoryName);
            tvCSDSale=(TextView)itemView.findViewById(R.id.tvCSDSale);
            tvCusConfStatus=(TextView)itemView.findViewById(R.id.tvCusConfStatus);

            llCopy=(LinearLayout)itemView.findViewById(R.id.llCopy);
        }
    }

    public RefNoReportAdapter(ArrayList<RefNoModel> reportList, Context context) {
        this.reportList = reportList;
        this.context = context;
    }
}
