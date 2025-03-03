package io.cordova.ifb.adapter;

import android.content.Context;
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
import io.cordova.ifb.module.IncentiveCalculationModule;
import io.cordova.ifb.module.ReportModule;

public class IncentiveCalculationAdapter extends RecyclerView.Adapter<IncentiveCalculationAdapter.MyViewHolder> {
    ArrayList<IncentiveCalculationModule>itemList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incnetive_calculation_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvIncentiveMonth,tvRanking,tvEarning,tvIncentiveBranch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIncentiveMonth=(TextView)itemView.findViewById(R.id.tvIncentiveMonth);
            tvRanking=(TextView)itemView.findViewById(R.id.tvRanking);
            tvEarning=(TextView)itemView.findViewById(R.id.tvEarning);
            tvIncentiveBranch=(TextView)itemView.findViewById(R.id.tvIncentiveBranch);


        }
    }

    public IncentiveCalculationAdapter(ArrayList<IncentiveCalculationModule> itemList,Context context) {
        this.itemList = itemList;
        this.context=context;
    }
}
