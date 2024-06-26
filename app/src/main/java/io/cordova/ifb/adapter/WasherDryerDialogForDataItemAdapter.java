package io.cordova.ifb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.DialogItemModule;

public class WasherDryerDialogForDataItemAdapter extends RecyclerView.Adapter<WasherDryerDialogForDataItemAdapter.MyViewHolder> {
    ArrayList<DialogItemModule>itemList=new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_item_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {




        myViewHolder.llClicked.setVisibility(View.VISIBLE);

        myViewHolder.tvItem.setText(itemList.get(i).getItem());





    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        LinearLayout llMain,llClicked;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem=(TextView)itemView.findViewById(R.id.tvItem);
            llMain=(LinearLayout)itemView.findViewById(R.id.llMain);
            llClicked=(LinearLayout)itemView.findViewById(R.id.llClick);



        }
    }


    public WasherDryerDialogForDataItemAdapter(ArrayList<DialogItemModule> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }
}
