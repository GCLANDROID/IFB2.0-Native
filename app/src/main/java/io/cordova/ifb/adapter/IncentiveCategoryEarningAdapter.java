package io.cordova.ifb.adapter;

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
import io.cordova.ifb.module.IncentiveCategoryEarningModule;

public class IncentiveCategoryEarningAdapter extends RecyclerView.Adapter<IncentiveCategoryEarningAdapter.MyViewHolder> {
    ArrayList<IncentiveCategoryEarningModule>itemList=new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incentive_category_earning_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.tvAmt.setText(itemList.get(i).getAmount());
        myViewHolder.tvCategory.setText(itemList.get(i).getCategory());
        myViewHolder.tvQty.setText(itemList.get(i).getQty());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmt,tvCategory,tvQty;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmt=(TextView) itemView.findViewById(R.id.tvAmt);
            tvCategory=(TextView) itemView.findViewById(R.id.tvCategory);
            tvQty=(TextView) itemView.findViewById(R.id.tvQty);



        }
    }

    public IncentiveCategoryEarningAdapter(ArrayList<IncentiveCategoryEarningModule> itemList) {
        this.itemList = itemList;

    }
}
