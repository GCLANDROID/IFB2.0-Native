package io.cordova.ifb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.TargetModule;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.MyViewHolder> {
    ArrayList<TargetModule>targetList=new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.target_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvProductName.setText(targetList.get(i).getProductName());
        myViewHolder.tvTarget.setText(targetList.get(i).getTarget());

    }

    @Override
    public int getItemCount() {
        return targetList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName,tvTarget;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName=(TextView)itemView.findViewById(R.id.tvProductName);
            tvTarget=(TextView)itemView.findViewById(R.id.tvTarget);

        }
    }

    public TargetAdapter(ArrayList<TargetModule> targetList) {
        this.targetList = targetList;
    }
}
