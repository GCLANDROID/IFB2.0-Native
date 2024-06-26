package io.cordova.ifb.adapter;

import android.content.Context;
import android.os.Build;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.DailyCompetitorSaleActivity;
import io.cordova.ifb.module.CompetiorSaleModel;
import io.cordova.ifb.utility.PrefManager;

public class DailyCompetitorSaleAdapter extends RecyclerView.Adapter<DailyCompetitorSaleAdapter.MyViewHolder> {
    ArrayList<CompetiorSaleModel> itemList = new ArrayList<>();
    Context context;
    ArrayList<String> item = new ArrayList<>();
    PrefManager prefManager;
    View.OnFocusChangeListener listener;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comptetior_raw, viewGroup, false);
        return new MyViewHolder(view, new MyCustomEditTextListener(), new MycustomFocus());
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        prefManager = new PrefManager(context);

        myViewHolder.myCustomEditTextListener.updatePosition(myViewHolder.getAdapterPosition());
        myViewHolder.cutomfocus.updatePosition(myViewHolder.getAdapterPosition());

        myViewHolder.tvItemName.setText(itemList.get(i).getItemName());
        myViewHolder.tvCompanyName.setText(itemList.get(i).getComapnyName());


        if (itemList.get(i).getEditVolume().equals("")){
            myViewHolder.etValue.setText("0");
        }else {
            myViewHolder.etValue.setText(itemList.get(i).getEditVolume());
        }
        if (!itemList.get(i).getItemName().equals("")){
            myViewHolder.llItemName.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.llItemName.setVisibility(View.GONE);
        }
        /*if (((DailyCompetitorSaleActivity) context).updateItemStatusCheck(i).equals("")){
           // Toast.makeText(context,"Please Enter Sale Value",Toast.LENGTH_LONG).show();

        }else {

        }*/







    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvCompanyName;
        EditText etValue;
        MyCustomEditTextListener myCustomEditTextListener;
        MycustomFocus cutomfocus;
        LinearLayout llItemName;

        public MyViewHolder(@NonNull View itemView, MyCustomEditTextListener myCustomEditTextListener, MycustomFocus mycustomFocus) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);
            etValue = (EditText) itemView.findViewById(R.id.etValue);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.cutomfocus = mycustomFocus;
            etValue.addTextChangedListener(myCustomEditTextListener);
            etValue.setOnFocusChangeListener(cutomfocus);
            llItemName=(LinearLayout)itemView.findViewById(R.id.llItemName);


        }
    }


    public DailyCompetitorSaleAdapter(ArrayList<CompetiorSaleModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    private class MycustomFocus implements View.OnFocusChangeListener {
        private int position;


        public void updatePosition(int position) {
            this.position = position;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                //((DailyCompetitorSaleActivity) context).updateItemStatus(position);

             //   ((DailyCompetitorSaleActivity) context).updateItemStatus(position,false);
            } else {
                ((DailyCompetitorSaleActivity) context).updateItemStatus(position,true);


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
                itemList.get(position).setEditVolume(s.toString());
                String test = itemList.get(position).getEditVolume();
                Log.d("ppp", test);
                item.add(itemList.get(position).getCategoryId() + "-" + itemList.get(position).getCompanyId() + "#" + itemList.get(position).getEditVolume());
                Log.d("competitoritem", item.toString());




            }

        }


    }
}

