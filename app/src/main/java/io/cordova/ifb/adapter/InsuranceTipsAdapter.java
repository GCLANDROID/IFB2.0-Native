package io.cordova.ifb.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.DocumentManageModule;
import io.cordova.ifb.module.IncentiveTipsModel;


public class InsuranceTipsAdapter extends RecyclerView.Adapter<InsuranceTipsAdapter.MyViewHolder> {
    ArrayList<IncentiveTipsModel>itemlist=new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.insurance_tips_raw,viewGroup,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion,tvAnswer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion=(TextView)itemView.findViewById(R.id.tvQuestion);
            tvAnswer=(TextView)itemView.findViewById(R.id.tvAnswer);


        }
    }


}
