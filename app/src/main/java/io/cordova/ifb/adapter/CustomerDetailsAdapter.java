package io.cordova.ifb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.CustomerCallingManaeActivity;
import io.cordova.ifb.module.CustomerDetailsModel;

public class CustomerDetailsAdapter extends RecyclerView.Adapter<CustomerDetailsAdapter.MyViewHolder> {
    ArrayList<CustomerDetailsModel>itemList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customerdetails_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        myViewHolder.tvCusName.setText(itemList.get(i).getName());
        myViewHolder.tvCustomerPhn.setText(itemList.get(i).getPhn());
        myViewHolder.tvCustomerPhn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+itemList.get(i).getPhn()));
                context.startActivity(intent);
            }
        });

        myViewHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CustomerCallingManaeActivity.class);
                intent.putExtra("name",itemList.get(i).getName());
                intent.putExtra("phn",itemList.get(i).getPhn());
                intent.putExtra("email",itemList.get(i).getEmail());
                intent.putExtra("pinCode",itemList.get(i).getPinCode());
                intent.putExtra("area",itemList.get(i).getArea());
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
        TextView tvCusName,tvCustomerPhn;
        Button btnDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCusName=(TextView)itemView.findViewById(R.id.tvCusName);
            tvCustomerPhn=(TextView)itemView.findViewById(R.id.tvCustomerPhn);
            btnDetails=(Button)itemView.findViewById(R.id.btnDetails);

        }
    }

    public CustomerDetailsAdapter(ArrayList<CustomerDetailsModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
