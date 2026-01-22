package io.cordova.ifb.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.CustomerCallingManaeActivity;
import io.cordova.ifb.activity.PlanogramActivity;
import io.cordova.ifb.module.DocumentManageModule;
import io.cordova.ifb.module.ProductDisplayModel;


public class ProductDisplayAdapter extends RecyclerView.Adapter<ProductDisplayAdapter.MyViewHolder> {
    ArrayList<ProductDisplayModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_display_row, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvProductName.setText(itemList.get(i).getModelName());
        if (itemList.get(i).getDisplay_Actual() == 2) {
            myViewHolder.llScanning.setVisibility(View.VISIBLE);
            myViewHolder.llScanned.setVisibility(View.GONE);
        } else {
            myViewHolder.llScanning.setVisibility(View.GONE);
            myViewHolder.llScanned.setVisibility(View.VISIBLE);
            if (itemList.get(i).getDisplay_Actual() == 1) {
                myViewHolder.tvScanned.setText("Yes Available");
            } else {
                myViewHolder.tvScanned.setText("Not Available");
            }
        }

        myViewHolder.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemList.get(i).getIsScan() == 1) {
                    ((PlanogramActivity) context).scanning(i);
                  //  ((PlanogramActivity) context).postDisplayProduct("1", itemList.get(i).getModelCode());
                } else {
                    ((PlanogramActivity) context).postDisplayProduct("1", itemList.get(i).getModelCode());
                }


            }
        });

        myViewHolder.tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((PlanogramActivity) context).postDisplayProduct("0", itemList.get(i).getModelCode());


            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvYes, tvNo, tvScanned;
        LinearLayout llScanned, llScanning;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvYes = (TextView) itemView.findViewById(R.id.tvYes);
            tvNo = (TextView) itemView.findViewById(R.id.tvNo);
            tvScanned = (TextView) itemView.findViewById(R.id.tvScanned);

            llScanning = itemView.findViewById(R.id.llScanning);
            llScanned = itemView.findViewById(R.id.llScanned);


        }
    }

    public ProductDisplayAdapter(ArrayList<ProductDisplayModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
