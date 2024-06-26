package io.cordova.ifb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.CallingReportModel;

public class SalesLeadReportAdapter extends RecyclerView.Adapter<SalesLeadReportAdapter.MyViewHolder> {
    ArrayList<CallingReportModel>reportList=new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sales_lead_report_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvDate.setText(reportList.get(i).getDate());
        myViewHolder.tvTokenNumber.setText(reportList.get(i).getToken());
        myViewHolder.tvCustomerName.setText(reportList.get(i).getName());
        myViewHolder.tvCustomerPhn.setText(reportList.get(i).getMobNo());
        myViewHolder.tvAction.setText(reportList.get(i).getAction());
        myViewHolder.tvStatus.setText(reportList.get(i).getStatus());
        myViewHolder.tvRemarks.setText(reportList.get(i).getRemarks());
        myViewHolder.tvCustomerEmail.setText(reportList.get(i).getEmail());
        myViewHolder.tvProduct.setText(reportList.get(i).getModelName());
        myViewHolder.tvTicketNumber.setText(reportList.get(i).getTicketNo());


    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvTokenNumber,tvCustomerName,tvCustomerPhn,tvAction,tvStatus,tvRemarks,tvCustomerEmail,tvProduct,tvTicketNumber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvTokenNumber=(TextView)itemView.findViewById(R.id.tvTokenNumber);
            tvCustomerName=(TextView)itemView.findViewById(R.id.tvCustomerName);
            tvCustomerPhn=(TextView)itemView.findViewById(R.id.tvCustomerPhn);
            tvAction=(TextView)itemView.findViewById(R.id.tvAction);
            tvStatus=(TextView)itemView.findViewById(R.id.tvStatus);
            tvRemarks=(TextView)itemView.findViewById(R.id.tvRemarks);
            tvCustomerEmail=(TextView)itemView.findViewById(R.id.tvCustomerEmail);
            tvProduct=(TextView)itemView.findViewById(R.id.tvProduct);
            tvTicketNumber=(TextView)itemView.findViewById(R.id.tvTicketNumber);

        }
    }

    public SalesLeadReportAdapter(ArrayList<CallingReportModel> reportList) {
        this.reportList = reportList;
    }
}
