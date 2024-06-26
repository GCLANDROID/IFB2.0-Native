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
import io.cordova.ifb.module.CollaborationModel;

public class CollaboartionReportAdapter extends RecyclerView.Adapter<CollaboartionReportAdapter.MyViewHolder> {
    ArrayList<CollaborationModel>reportList=new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.collaboartion_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvInteractionDate.setText(reportList.get(i).getInteractionDate());
        myViewHolder.tvEntryDate.setText(reportList.get(i).getEntryDate());
        myViewHolder.tvAns.setText(reportList.get(i).getAnswer());
        myViewHolder.tvQuestion.setText(reportList.get(i).getQuestion());
        if (!reportList.get(i).getInteractionDate().equals("")){
            myViewHolder.llDate.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.llDate.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvInteractionDate,tvEntryDate,tvQuestion,tvAns;
        LinearLayout llDate,llPanel2,llPanel1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInteractionDate=(TextView)itemView.findViewById(R.id.tvInteractionDate);
            tvEntryDate=(TextView)itemView.findViewById(R.id.tvEntryDate);
            tvQuestion=(TextView)itemView.findViewById(R.id.tvQuestion);
            tvAns=(TextView)itemView.findViewById(R.id.tvAns);

            llDate=(LinearLayout)itemView.findViewById(R.id.llDate);
            llPanel2=(LinearLayout)itemView.findViewById(R.id.llPanel2);
            llPanel1=(LinearLayout)itemView.findViewById(R.id.llPanel1);
        }
    }

    public CollaboartionReportAdapter(ArrayList<CollaborationModel> reportList) {
        this.reportList = reportList;
    }
}
