package io.cordova.ifb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.EcatelougeModel;


public class ESubCatelougeAdapter extends RecyclerView.Adapter<ESubCatelougeAdapter.MyViewHolder> {
    ArrayList<EcatelougeModel> itemList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.esubcatelouge_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {



        myViewHolder.tvFileName.setText(itemList.get(i).getFileName());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemList.get(i).getUrl()));
                context.startActivity(browserIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvFileName;
        LinearLayout llCopy;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFileName=(TextView)itemView.findViewById(R.id.tvFileName);

        }
    }

    public ESubCatelougeAdapter(ArrayList<EcatelougeModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
