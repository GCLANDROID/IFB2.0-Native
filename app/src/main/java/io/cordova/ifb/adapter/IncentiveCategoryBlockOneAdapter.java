package io.cordova.ifb.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.IncentiveCalculationDetailsActivity;
import io.cordova.ifb.module.IncentiveCalculationModule;
import io.cordova.ifb.module.IncentiveCategoryDetailsModel;

public class IncentiveCategoryBlockOneAdapter extends RecyclerView.Adapter<IncentiveCategoryBlockOneAdapter.MyViewHolder> {
    ArrayList<IncentiveCategoryDetailsModel>itemList=new ArrayList<>();
    Activity activity;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incentive_category_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.tvAmt.setText(itemList.get(i).getAmt());
        myViewHolder.tvCategory.setText(itemList.get(i).getCategory());
        myViewHolder.tvPercentage.setText(itemList.get(i).getAchievement());
        myViewHolder.tvAcheived.setText(itemList.get(i).getAcheived());
        myViewHolder.tvTarget.setText(itemList.get(i).getTgt());
        if (itemList.get(i).getCategory().replaceAll(" ","").equalsIgnoreCase("Total")){
            myViewHolder.itemView.setBackgroundColor(Color.parseColor("#8EF493"));
        }


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!itemList.get(i).getCategory().replaceAll(" ","").equalsIgnoreCase("Total") ){
                    if (!itemList.get(i).getEarningList().equals("")){
                        ((IncentiveCalculationDetailsActivity)activity).getEarningDetails(itemList.get(i).getEarningList(),itemList.get(i).getCategory());
                    }

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmt,tvPercentage,tvAcheived,tvTarget,tvCategory;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmt=(TextView) itemView.findViewById(R.id.tvAmt);
            tvPercentage=(TextView) itemView.findViewById(R.id.tvPercentage);
            tvAcheived=(TextView) itemView.findViewById(R.id.tvAcheived);
            tvTarget=(TextView) itemView.findViewById(R.id.tvTarget);
            tvCategory=(TextView) itemView.findViewById(R.id.tvCategory);



        }
    }

    public IncentiveCategoryBlockOneAdapter(ArrayList<IncentiveCategoryDetailsModel> itemList,Activity activity) {
        this.itemList = itemList;
        this.activity=activity;

    }
}
