package io.cordova.ifb.adapter;

import android.annotation.SuppressLint;
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
import io.cordova.ifb.activity.AirConditionerDialogActivity;
import io.cordova.ifb.module.DisplayMatrixModel;
import io.cordova.ifb.utility.PrefManager;

public class DisplayMatrixAdapterAdapter extends RecyclerView.Adapter<DisplayMatrixAdapterAdapter.MyViewHolder> {
    ArrayList<DisplayMatrixModel> itemList = new ArrayList<>();
    Context context;
    ArrayList<String> item = new ArrayList<>();
    PrefManager prefManager;
    View.OnFocusChangeListener listener;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.displaymatrix_raw, viewGroup, false);
        return new MyViewHolder(view, new MyCustomEditTextListener(), new MycustomFocus());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        prefManager = new PrefManager(context);

        myViewHolder.myCustomEditTextListener.updatePosition(myViewHolder.getAdapterPosition());
        myViewHolder.cutomfocus.updatePosition(myViewHolder.getAdapterPosition());
        myViewHolder.etValue.setText(itemList.get(i).getEditVolume());

        myViewHolder.tvItemName.setText(itemList.get(i).getItemName());
        myViewHolder.tvCompanyName.setText(itemList.get(i).getComapnyName());
//        myViewHolder.etValue.setText(prefManager.getAirIfbSize());
        if (!itemList.get(i).getItemName().equals("")){
            myViewHolder.llItemName.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.llItemName.setVisibility(View.GONE);
        }

        if (itemList.get(i).getCompanyId().equals("IFBCC000015")){

                myViewHolder.tvAdd.setVisibility(View.VISIBLE);
            myViewHolder.etValue.setEnabled(false);


        }else {
            myViewHolder.tvAdd.setVisibility(View.GONE);
            myViewHolder.etValue.setEnabled(true);
        }

        myViewHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent=new Intent(context,AirConditionerDialogActivity.class);
                    intent.putExtra("categoryid",itemList.get(i).getCategoryId());
                    context.startActivity(intent);


                /*else if (itemList.get(i).getItemName().equals("CLOTHES DRYER")){
                    Intent intent=new Intent(context,ClothsDryerDialogActivity.class);
                    context.startActivity(intent);
                }
                else if (itemList.get(i).getItemName().equals("DISHWASHER")){
                    Intent intent=new Intent(context,DishwasherDialogActivity.class);
                    context.startActivity(intent);

                }else if (itemList.get(i).getItemName().equals("KITCHEN_APPLIANCE")){


                }else if (itemList.get(i).getItemName().equals("MICROWAVE OVEN")){
                    Intent intent=new Intent(context,MicroOvenDialogActivity.class);
                    context.startActivity(intent);

                }else if (itemList.get(i).getItemName().equals("WASHING MACHINE-FLU")){
                    Intent intent=new Intent(context,WMFLUDialogActivity .class);
                    context.startActivity(intent);

                }else if (itemList.get(i).getItemName().equals("WASHING MACHINE-TL")){
                    Intent intent=new Intent(context,WMTLDialogActivity.class);
                    context.startActivity(intent);

                }*/
            }
        });




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
        TextView tvAdd;

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
            tvAdd=(TextView)itemView.findViewById(R.id.tvAdd);


        }
    }


    public DisplayMatrixAdapterAdapter(ArrayList<DisplayMatrixModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    private class MycustomFocus implements View.OnFocusChangeListener {
        private int position;


        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {




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

