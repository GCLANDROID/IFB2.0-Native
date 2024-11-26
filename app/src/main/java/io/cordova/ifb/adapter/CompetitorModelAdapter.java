package io.cordova.ifb.adapter;

import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.ECatelougeActivity;
import io.cordova.ifb.module.CompetitorModelModule;
import io.cordova.ifb.module.ECatelogModel;


public class CompetitorModelAdapter extends RecyclerView.Adapter<CompetitorModelAdapter.MyViewHolder> {
    ArrayList<CompetitorModelModule> itemList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.competitor_model_module, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvModelName.setText(itemList.get(i).getModelName());
        myViewHolder.etQty.setText(""+itemList.get(i).getQty());


        myViewHolder.etQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (!editable.toString().isEmpty()){

                        itemList.get(i).setQty(Integer.parseInt(myViewHolder.etQty.getText().toString().trim()));
                    } else {

                    }
                } catch (IndexOutOfBoundsException e){

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvModelName;
        EditText etQty;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvModelName=(TextView)itemView.findViewById(R.id.tvModelName);
            etQty=(EditText) itemView.findViewById(R.id.etQty);
        }
    }

    public CompetitorModelAdapter(ArrayList<CompetitorModelModule> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
