package io.cordova.ifb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.CompSaleModel;

public class DailyCompSaleReportAdapter extends RecyclerView.Adapter<DailyCompSaleReportAdapter.MyViewHolder> {
    ArrayList<CompSaleModel> itemList = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.compsale_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvDate.setText(itemList.get(i).getSalesDate());
        myViewHolder.tvCompany.setText(itemList.get(i).getComapnanyname());
        myViewHolder.tvCategory.setText(itemList.get(i).getCategory());
        myViewHolder.tvQuant.setText(" : "+itemList.get(i).getQuant());


        if (!itemList.get(i).getCategory().equals("")){

            myViewHolder.llCategory.setVisibility(View.VISIBLE);
        }else {

            myViewHolder.llCategory.setVisibility(View.GONE);
        }

        myViewHolder.llDate.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvCompany, tvCategory, tvQuant;
        LinearLayout llCategory,llBlank,llDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvCompany = (TextView) itemView.findViewById(R.id.tvCompany);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            tvQuant = (TextView) itemView.findViewById(R.id.tvQuant);

            llBlank=(LinearLayout)itemView.findViewById(R.id.llBlank);
            llDate=(LinearLayout)itemView.findViewById(R.id.llDate);
            llCategory=(LinearLayout)itemView.findViewById(R.id.llCategory);

        }
    }

    public DailyCompSaleReportAdapter(ArrayList<CompSaleModel> itemList) {
        this.itemList = itemList;
    }
}
