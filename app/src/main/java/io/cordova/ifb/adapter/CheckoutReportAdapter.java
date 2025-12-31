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
import io.cordova.ifb.module.CheckOutStatusModel;
import io.cordova.ifb.module.ReportModule;

public class CheckoutReportAdapter extends RecyclerView.Adapter<CheckoutReportAdapter.MyViewHolder> {
    ArrayList<CheckOutStatusModel>reportList=new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.checkout_count_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvDate.setText(reportList.get(i).getDate());
        myViewHolder.tvChekIn.setText(reportList.get(i).getCheckInTime());

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvChekIn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvChekIn=(TextView)itemView.findViewById(R.id.tvChekIn);


        }
    }

    public CheckoutReportAdapter(ArrayList<CheckOutStatusModel> reportList) {
        this.reportList = reportList;
    }
}
