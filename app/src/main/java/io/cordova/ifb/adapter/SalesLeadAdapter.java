package io.cordova.ifb.adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.SalesLeadDetailsActivity;
import io.cordova.ifb.module.SalesLeadModel;

public class SalesLeadAdapter extends RecyclerView.Adapter<SalesLeadAdapter.MyViewHolder> {
    ArrayList<SalesLeadModel>itemList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sales_lead_customer_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
       myViewHolder.tvCusName.setText(itemList.get(i).getCusName());
       myViewHolder.tvCustomerPhn.setText(itemList.get(i).getMobile());
       myViewHolder.tvDate.setText(itemList.get(i).getDate());

       myViewHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context, SalesLeadDetailsActivity.class);
               intent.putExtra("callToken",itemList.get(i).getCallToken());
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);
           }
       });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCusName,tvCustomerPhn,tvDate;
        Button btnDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCusName=(TextView)itemView.findViewById(R.id.tvCusName);
            tvCustomerPhn=(TextView)itemView.findViewById(R.id.tvCustomerPhn);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            btnDetails=(Button)itemView.findViewById(R.id.btnDetails);

        }
    }

    public SalesLeadAdapter(ArrayList<SalesLeadModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
