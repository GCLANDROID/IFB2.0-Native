package io.cordova.ifb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.NewTargetModel;

public class NewTargetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    ArrayList<NewTargetModel> itemList;

    public NewTargetAdapter(Context context,ArrayList<NewTargetModel> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.target_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).tvModelName.setText(itemList.get(position).getCategory());
        ((ItemViewHolder) holder).tvTarget.setText(itemList.get(position).getTarget());
        ((ItemViewHolder) holder).tvAchievement.setText(itemList.get(position).getAchievement());
        ((ItemViewHolder) holder).tvToBeAchievement.setText(itemList.get(position).getToBeAchievement());
        if (position%2 == 0){
            ((ItemViewHolder) holder).llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.light_yellow));
        } else {
            ((ItemViewHolder) holder).llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue));
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvModelName,tvTarget,tvAchievement,tvToBeAchievement;
        LinearLayout llMain;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvModelName = itemView.findViewById(R.id.tvModelName);
            tvTarget = itemView.findViewById(R.id.tvTarget);
            tvAchievement = itemView.findViewById(R.id.tvAchievement);
            tvToBeAchievement = itemView.findViewById(R.id.tvToBeAchievement);
            llMain = itemView.findViewById(R.id.llMain);
        }
    }
}
