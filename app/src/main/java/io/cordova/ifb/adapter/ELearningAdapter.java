package io.cordova.ifb.adapter;

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
import io.cordova.ifb.module.ELearningModel;


public class ELearningAdapter extends RecyclerView.Adapter<ELearningAdapter.MyViewHolder> {
    ArrayList<ELearningModel> itemList=new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.elearning_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvCaption.setText(itemList.get(i).getCaption());
        myViewHolder.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemList.get(i).getVideoURL()));
                context.startActivity(browserIntent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCaption;
        Button btnShow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCaption=(TextView)itemView.findViewById(R.id.tvCaption);
            btnShow=(Button)itemView.findViewById(R.id.btnShow);




        }
    }

    public ELearningAdapter(ArrayList<ELearningModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
