package io.cordova.ifb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.DummySalesModule;

public class DummySalesReportAdapter extends RecyclerView.Adapter<DummySalesReportAdapter.MyViewHolder> {
    ArrayList<DummySalesModule>reportList=new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dummy_salesreport_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvDate.setText(reportList.get(i).getDate());
        myViewHolder.tvTicketNumber.setText(reportList.get(i).getTicketNumber());
        myViewHolder.tvTokenNumber.setText(reportList.get(i).getTokenNumber());
        myViewHolder.tvModelName.setText(reportList.get(i).getModelName());
        myViewHolder.tvProductCode.setText(reportList.get(i).getModelCode());
        myViewHolder.tvCustomerName.setText(reportList.get(i).getCustomerName());
        myViewHolder.tvCustomerPhn.setText(reportList.get(i).getCustomerPhn());
        myViewHolder.tvCustomerEmail.setText(reportList.get(i).getCustomerEmail());
        myViewHolder.tvStatus.setText(reportList.get(i).getStatus());
        myViewHolder.tvRemarks.setText(reportList.get(i).getRemarks());
        myViewHolder.tvTempNo.setText(reportList.get(i).getTempno());
        myViewHolder.tvCategoryName.setText(reportList.get(i).getCategoryname());

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvTicketNumber,tvTokenNumber,tvModelName,tvProductCode,tvCustomerName,tvCustomerPhn,tvCustomerEmail,tvStatus,tvRemarks,tvTempNo,tvCategoryName;
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
            tvTempNo=(TextView)itemView.findViewById(R.id.tvTempNo);
            tvCategoryName=(TextView)itemView.findViewById(R.id.tvCategoryName);

        }
    }

    public DummySalesReportAdapter(ArrayList<DummySalesModule> reportList) {
        this.reportList = reportList;
    }
}
