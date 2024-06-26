package io.cordova.ifb.adapter;

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
import io.cordova.ifb.module.SalesModule;

public class SalesReportAdapter extends RecyclerView.Adapter<SalesReportAdapter.MyViewHolder> {
    ArrayList<SalesModule>reportList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.salesreport_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvDate.setText(reportList.get(i).getDate());
        myViewHolder.tvTicketNumber.setText(reportList.get(i).getTicketNumber());
        myViewHolder.tvTokenNumber.setText(reportList.get(i).getTokenNumber());
        myViewHolder.tvModelName.setText(reportList.get(i).getModelName());
        myViewHolder.tvProductCode.setText(reportList.get(i).getProductCode());
        myViewHolder.tvCustomerName.setText(reportList.get(i).getCustomerName());
        myViewHolder.tvCustomerPhn.setText(reportList.get(i).getCustomerPhn());
        myViewHolder.tvCustomerEmail.setText(reportList.get(i).getCustomerEmail());
        myViewHolder.tvStatus.setText(reportList.get(i).getStatus());
        myViewHolder.tvRemarks.setText(reportList.get(i).getRemarks());
        myViewHolder.llCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(reportList.get(i).getFileUrl()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
        if (reportList.get(i).getFileUrl().equals("")||reportList.get(i).getFileUrl().equals("null")){

            myViewHolder.llCopy.setVisibility(View.GONE);


        }else {
            myViewHolder.llCopy.setVisibility(View.VISIBLE);
        }
        myViewHolder.tvSerialNumber.setText(reportList.get(i).getSerialNumber());

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvTicketNumber,tvTokenNumber,tvModelName,tvProductCode,tvCustomerName,tvCustomerPhn,tvCustomerEmail,tvStatus,tvRemarks,tvSerialNumber;
        LinearLayout llCopy;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvTicketNumber=(TextView)itemView.findViewById(R.id.tvTicketNumber);
            tvTokenNumber=(TextView)itemView.findViewById(R.id.tvTokenNumber);
            tvModelName=(TextView)itemView.findViewById(R.id.tvModelName);
            tvProductCode=(TextView)itemView.findViewById(R.id.tvProductCode);
            tvCustomerName=(TextView)itemView.findViewById(R.id.tvCustomerName);
            tvCustomerPhn=(TextView)itemView.findViewById(R.id.tvCustomerPhn);
            tvCustomerEmail=(TextView)itemView.findViewById(R.id.tvCustomerEmail);
            tvStatus=(TextView)itemView.findViewById(R.id.tvStatus);
            tvRemarks=(TextView)itemView.findViewById(R.id.tvRemarks);
            tvSerialNumber=(TextView)itemView.findViewById(R.id.tvSerialNumber);
            llCopy=(LinearLayout)itemView.findViewById(R.id.llCopy);
        }
    }

    public SalesReportAdapter(ArrayList<SalesModule> reportList, Context context) {
        this.reportList = reportList;
        this.context = context;
    }
}
