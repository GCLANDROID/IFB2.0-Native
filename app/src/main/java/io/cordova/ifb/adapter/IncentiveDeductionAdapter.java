package io.cordova.ifb.adapter;

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
import io.cordova.ifb.module.DeductionModule;
import io.cordova.ifb.module.IncentiveCategoryDetailsModel;

public class IncentiveDeductionAdapter extends RecyclerView.Adapter<IncentiveDeductionAdapter.MyViewHolder> {
    ArrayList<DeductionModule>itemList=new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.deduction_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.tvAmt.setText(itemList.get(i).getAmt());
        myViewHolder.tvRemarks.setText(itemList.get(i).getRemarks());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmt,tvRemarks;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmt=(TextView) itemView.findViewById(R.id.tvAmt);
            tvRemarks=(TextView) itemView.findViewById(R.id.tvRemarks);



        }
    }

    public IncentiveDeductionAdapter(ArrayList<DeductionModule> itemList) {
        this.itemList = itemList;

    }
}
