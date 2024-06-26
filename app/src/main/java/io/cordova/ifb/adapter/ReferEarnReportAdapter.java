package io.cordova.ifb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import io.cordova.ifb.module.ReferEarnModule;
import io.cordova.ifb.module.ReportModule;

public class ReferEarnReportAdapter extends RecyclerView.Adapter<ReferEarnReportAdapter.MyViewHolder> {
    ArrayList<ReferEarnModule>itemList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.refer_earn_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.tvCanName.setText("Refer Candidate "+itemList.get(i).getReferCanName());
        myViewHolder.tvCanMob.setText(itemList.get(i).getReferCanMob());
        myViewHolder.tvStatus.setText(itemList.get(i).getStatus());
        myViewHolder.llCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemList.get(i).getDocLink()));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCanName,tvCanMob,tvStatus;
        LinearLayout llCV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCanName=(TextView)itemView.findViewById(R.id.tvCanName);
            tvCanMob=(TextView)itemView.findViewById(R.id.tvCanMob);
            tvStatus=(TextView)itemView.findViewById(R.id.tvStatus);

            llCV=(LinearLayout) itemView.findViewById(R.id.llCV);
        }
    }

    public ReferEarnReportAdapter(ArrayList<ReferEarnModule> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
