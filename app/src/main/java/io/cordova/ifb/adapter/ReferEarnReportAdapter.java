package io.cordova.ifb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import io.cordova.ifb.module.ReferEarnModule;
import io.cordova.ifb.module.ReportModule;

public class ReferEarnReportAdapter extends RecyclerView.Adapter<ReferEarnReportAdapter.MyViewHolder> {
    ArrayList<ReferEarnModule>itemList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.refer_earn_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.tvCanName.setText(itemList.get(i).getReferCanName());
        myViewHolder.tvCanMob.setText(itemList.get(i).getReferCanMob());
        myViewHolder.tvMonth.setText(itemList.get(i).getReffered_Month());
        myViewHolder.tvStatus.setText(itemList.get(i).getCSR_Current_Status());
        myViewHolder.tvOnboarded.setText(itemList.get(i).getCSR_ONboarded_Date());
        myViewHolder.tvEligable.setText(itemList.get(i).getReferralEligabledate());
        myViewHolder.tvAmount.setText(itemList.get(i).getReferral_Amount());
        myViewHolder.tvCanAadhaar.setText(itemList.get(i).getCandidateAadhar());
        myViewHolder.tvPaid.setText(itemList.get(i).getReferral_Amount_Paid_date());
        myViewHolder.tvExit.setText(itemList.get(i).getCSR_EXIT_Date());
        if (itemList.get(i).getCSR_Current_Status().equals("")){
           myViewHolder.lLCSRStatus.setVisibility(View.GONE);
        }else {
            myViewHolder.lLCSRStatus.setVisibility(View.VISIBLE);
            if (itemList.get(i).getCSR_Current_Status().equalsIgnoreCase("ACTIVE")){
                 myViewHolder.llActiveStatus.setVisibility(View.VISIBLE);
                myViewHolder.llInActiveStaus.setVisibility(View.GONE);
            }else {
                myViewHolder.llInActiveStaus.setVisibility(View.VISIBLE);
                myViewHolder.llActiveStatus.setVisibility(View.GONE);
            }
        }






    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCanName,tvCanMob,tvStatus,tvMonth,tvOnboarded,tvEligable,tvAmount,tvPaid,tvExit,tvCanAadhaar;
        LinearLayout lLCSRStatus,llActiveStatus,llInActiveStaus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCanName=(TextView)itemView.findViewById(R.id.tvCanName);
            tvCanMob=(TextView)itemView.findViewById(R.id.tvCanMob);
            tvStatus=(TextView)itemView.findViewById(R.id.tvStatus);
            tvMonth=(TextView)itemView.findViewById(R.id.tvMonth);
            tvOnboarded=(TextView)itemView.findViewById(R.id.tvOnboarded);
            tvEligable=(TextView)itemView.findViewById(R.id.tvEligable);
            tvAmount=(TextView)itemView.findViewById(R.id.tvAmount);
            tvPaid=(TextView)itemView.findViewById(R.id.tvPaid);
            tvExit=(TextView)itemView.findViewById(R.id.tvExit);
            tvCanAadhaar=(TextView)itemView.findViewById(R.id.tvCanAadhaar);

            lLCSRStatus=(LinearLayout) itemView.findViewById(R.id.lLCSRStatus);
            llActiveStatus=(LinearLayout) itemView.findViewById(R.id.llActiveStatus);
            llInActiveStaus=(LinearLayout) itemView.findViewById(R.id.llInActiveStaus);


        }
    }

    public ReferEarnReportAdapter(ArrayList<ReferEarnModule> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
