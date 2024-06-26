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
import io.cordova.ifb.module.CVisitModel;

public class CvisitReportAdapter extends RecyclerView.Adapter<CvisitReportAdapter.MyViewHolder> {
    ArrayList<CVisitModel> reportList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.c_visit_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvDate.setText(reportList.get(i).getVisitingDate());
        myViewHolder.tvCategory.setText(reportList.get(i).getCategory());
        myViewHolder.tvModel.setText(reportList.get(i).getModel());
        myViewHolder.tvCustomerName.setText(reportList.get(i).getCusName());
        myViewHolder.tvCustomerPhn.setText(reportList.get(i).getCusMob());
        myViewHolder.tvVisit.setText(reportList.get(i).getEngVisit());
        myViewHolder.tvEngName.setText(reportList.get(i).getVisitingDate());
        myViewHolder.tvEngPhn.setText(reportList.get(i).getEngMob());
        myViewHolder.tvLocation.setText(reportList.get(i).getLocation());
        myViewHolder.tvAddress.setText(reportList.get(i).getCusAddress());
        myViewHolder.tvRemarks.setText(reportList.get(i).getRemarks());
        myViewHolder.llImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(reportList.get(i).getImageUrl()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);

            }

        });


    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvCategory, tvModel, tvCustomerName, tvCustomerPhn, tvCustomerEmail, tvVisit, tvEngName, tvEngPhn, tvLocation, tvAddress, tvRemarks;
        LinearLayout llImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            tvModel = (TextView) itemView.findViewById(R.id.tvModel);
            tvCustomerName = (TextView) itemView.findViewById(R.id.tvCustomerName);
            tvCustomerPhn = (TextView) itemView.findViewById(R.id.tvCustomerPhn);
            tvCustomerEmail = (TextView) itemView.findViewById(R.id.tvCustomerEmail);
            tvVisit = (TextView) itemView.findViewById(R.id.tvVisit);
            tvEngName = (TextView) itemView.findViewById(R.id.tvEngName);
            tvEngPhn = (TextView) itemView.findViewById(R.id.tvEngPhn);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvRemarks = (TextView) itemView.findViewById(R.id.tvRemarks);
            llImage=(LinearLayout)itemView.findViewById(R.id.llImage);


        }
    }

    public CvisitReportAdapter(ArrayList<CVisitModel> reportList, Context context) {
        this.reportList = reportList;
        this.context = context;
    }
}
