package io.cordova.ifb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.CallingReportModel;
import io.cordova.ifb.module.IncentiveManualModule;

public class IncentiveManualAdapter extends RecyclerView.Adapter<IncentiveManualAdapter.MyViewHolder> {
    ArrayList<IncentiveManualModule>itemList=new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incentive_manual_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvdocInfo.setText(itemList.get(i).getDocName());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(itemList.get(i).getDocURL()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
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
        TextView tvdocInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvdocInfo=(TextView)itemView.findViewById(R.id.tvdocInfo);


        }
    }


    public IncentiveManualAdapter(ArrayList<IncentiveManualModule> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
