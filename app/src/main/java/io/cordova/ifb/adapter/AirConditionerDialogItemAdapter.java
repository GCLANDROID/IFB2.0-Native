package io.cordova.ifb.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.AirConditionerDialogActivity;
import io.cordova.ifb.module.DialogItemModule;

public class AirConditionerDialogItemAdapter extends RecyclerView.Adapter<AirConditionerDialogItemAdapter.MyViewHolder> {
    ArrayList<DialogItemModule>itemList=new ArrayList<>();
    Context context;
    ArrayList<String>sendModel=new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_item_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        final DialogItemModule itemModel = itemList.get(i);
       if (itemList.get(i).isSelected()){
            myViewHolder.llClicked.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.llClicked.setVisibility(View.GONE);
        }

        myViewHolder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemModel.setSelected(!itemModel.isSelected());
                // holder.view.setBackgroundColor(attandanceModel.isSelected() ? Color.CYAN : Color.WHITE);

                if (itemModel.isSelected()) {

                    myViewHolder.llClicked.setVisibility(View.VISIBLE);
                    itemList.get(i).setSelected(true);
                    notifyDataSetChanged();

                    ((AirConditionerDialogActivity) context).updateItemStatus(i, true );
                } else {
                    myViewHolder.llClicked.setVisibility(View.GONE);
                    ((AirConditionerDialogActivity) context).updateItemStatus(i, false);
                    itemList.get(i).setSelected(false);
                    notifyDataSetChanged();
                }

            }
        });


        myViewHolder.tvItem.setText(itemList.get(i).getItem());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        LinearLayout llMain,llClicked;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem=(TextView)itemView.findViewById(R.id.tvItem);
            llMain=(LinearLayout)itemView.findViewById(R.id.llMain);
            llClicked=(LinearLayout)itemView.findViewById(R.id.llClick);
            checkBox=(CheckBox)itemView.findViewById(R.id.ckBox);
        }
    }


    public AirConditionerDialogItemAdapter(ArrayList<DialogItemModule> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }
}
