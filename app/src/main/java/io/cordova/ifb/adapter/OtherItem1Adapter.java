package io.cordova.ifb.adapter;

import android.content.Context;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.OtherDailyActivity;
import io.cordova.ifb.module.TSRSaleItemModule;
import io.cordova.ifb.utility.PrefManager;

public class OtherItem1Adapter extends RecyclerView.Adapter<OtherItem1Adapter.MyViewHolder> {
    ArrayList<TSRSaleItemModule> itemList = new ArrayList<>();
    Context context;
    ArrayList<String>item=new ArrayList<>();
    PrefManager prefManager;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tsrsalemodel, viewGroup, false);
        return new MyViewHolder(view, new MyCustomEditTextListener(),new MycustomFocus());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        prefManager=new PrefManager(context);
        final TSRSaleItemModule itemModel = itemList.get(i);
        myViewHolder.myCustomEditTextListener.updatePosition(myViewHolder.getAdapterPosition());
        myViewHolder.cutomfocus.updatePosition(myViewHolder.getAdapterPosition());
        myViewHolder.tvItem.setText(itemList.get(i).getItem());
        myViewHolder.etItem.setText(itemList.get(i).getEditvalue());






    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        EditText etItem;
        ImageView imgTick;
        MyCustomEditTextListener myCustomEditTextListener;
        MycustomFocus cutomfocus;

        public MyViewHolder(@NonNull View itemView, MyCustomEditTextListener myCustomEditTextListener,MycustomFocus mycustomFocus) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tvItem);
            etItem = (EditText) itemView.findViewById(R.id.etItem);
            imgTick = (ImageView) itemView.findViewById(R.id.imgTick);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.cutomfocus = mycustomFocus;
            etItem.setOnFocusChangeListener(cutomfocus);
            etItem.addTextChangedListener(myCustomEditTextListener);


        }
    }


    public OtherItem1Adapter(ArrayList<TSRSaleItemModule> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }

   /* private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

              if (!charSequence.toString().equals("")) {
                  itemList.get(position).setEditvalue(charSequence.toString());
                  item.add(itemList.get(position).getItemId()+"-"+itemList.get(position).getEditvalue());
                  Log.d("etItem", item.toString());
                  String otheritem1=item.toString();
                  prefManager.savesaveOtherItem1(otheritem1);

              }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }


    }*/

    private class MycustomFocus implements View.OnFocusChangeListener {
        private int position;


        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                itemList.get(position).setEditvalue("0");


            } else {
                ((OtherDailyActivity) context).updateItemStatus(position);


            }



        }
    }


    private class MyCustomEditTextListener implements TextWatcher {

        private int position;


        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {


        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() > 0) {
                itemList.get(position).setEditvalue(s.toString());
                String test = itemList.get(position).getEditvalue();
                Log.d("ppp", test);
                item.add(itemList.get(position).getItemId()+"-"+itemList.get(position).getEditvalue());
                Log.d("competitoritem", item.toString());


            }

        }


    }
}

