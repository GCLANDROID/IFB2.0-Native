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
import io.cordova.ifb.module.DisplayMatrixReportModel;

public class DisplayMatrixReportAdapter extends RecyclerView.Adapter<DisplayMatrixReportAdapter.MyViewHolder> {
    ArrayList<DisplayMatrixReportModel> itemList = new ArrayList<>();
    Context context;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.displaymatrix_report_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {



        myViewHolder.tvItemName.setText(itemList.get(i).getItemName());
        myViewHolder.tvCompanyName.setText(itemList.get(i).getCompanyName());
        myViewHolder.tvValue.setText(itemList.get(i).getQuantity());
        if (!itemList.get(i).getItemName().equals("")){
            myViewHolder.llItemName.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.llItemName.setVisibility(View.GONE);
        }




    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvCompanyName;
        TextView tvValue;
        LinearLayout llItemName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);
            tvValue = (TextView) itemView.findViewById(R.id.tvValue);
            llItemName=(LinearLayout)itemView.findViewById(R.id.llItemName);


        }
    }

    public DisplayMatrixReportAdapter(ArrayList<DisplayMatrixReportModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}

