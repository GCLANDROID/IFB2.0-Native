package io.cordova.ifb.adapter;

import android.content.Context;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.RefInfoManageActivity;
import io.cordova.ifb.module.RefInfoModel;
import io.cordova.ifb.utility.PrefManager;

public class RefInfoAdapter extends RecyclerView.Adapter<RefInfoAdapter.MyViewHolder> {
    ArrayList<RefInfoModel> itemList = new ArrayList<>();
    Context context;
    ArrayList<String> item = new ArrayList<>();
    PrefManager prefManager;
    View.OnFocusChangeListener listener;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ref_info_row, viewGroup, false);
        return new MyViewHolder(view, new MyCustomEditTextListenerForFF(), new MycustomFocusForFF(),new MyCustomEditTextListenerForDC(), new MycustomFocusForDC());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        prefManager = new PrefManager(context);

        myViewHolder.myCustomEditTextListenerForFF.updatePosition(myViewHolder.getAdapterPosition());
        myViewHolder.cutomfocusForFF.updatePosition(myViewHolder.getAdapterPosition());

        myViewHolder.myCustomEditTextListenerForDC.updatePosition(myViewHolder.getAdapterPosition());
        myViewHolder.cutomfocusForDC.updatePosition(myViewHolder.getAdapterPosition());

        myViewHolder.tvCompanyName.setText(itemList.get(i).getCompName());










    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  tvCompanyName;
        EditText etFFValue,etDCValue;

        MyCustomEditTextListenerForFF myCustomEditTextListenerForFF;
        MycustomFocusForFF cutomfocusForFF;

        MyCustomEditTextListenerForDC myCustomEditTextListenerForDC;
        MycustomFocusForDC cutomfocusForDC;


        public MyViewHolder(@NonNull View itemView, MyCustomEditTextListenerForFF myCustomEditTextListenerForFF, MycustomFocusForFF mycustomFocusForFF,MyCustomEditTextListenerForDC myCustomEditTextListenerForDC, MycustomFocusForDC mycustomFocusForDC) {
            super(itemView);

            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);
            etDCValue = (EditText) itemView.findViewById(R.id.etDCValue);
            etFFValue = (EditText) itemView.findViewById(R.id.etFFValue);
            this.myCustomEditTextListenerForFF = myCustomEditTextListenerForFF;
            this.cutomfocusForFF = mycustomFocusForFF;
            etFFValue.addTextChangedListener(myCustomEditTextListenerForFF);
            etFFValue.setOnFocusChangeListener(cutomfocusForFF);


            this.myCustomEditTextListenerForDC = myCustomEditTextListenerForDC;
            this.cutomfocusForDC = mycustomFocusForDC;
            etDCValue.addTextChangedListener(myCustomEditTextListenerForDC);
            etDCValue.setOnFocusChangeListener(cutomfocusForDC);



        }
    }


    public RefInfoAdapter(ArrayList<RefInfoModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }

    private class MycustomFocusForFF implements View.OnFocusChangeListener {
        private int position;


        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                //((CompetitorSaleActivity) context).updateItemStatus(position);

             //   ((CompetitorSaleActivity) context).updateItemStatus(position,false);
            } else {
                ((RefInfoManageActivity) context).updateItemStatusForFF(position,true);


            }



        }
    }


    private class MyCustomEditTextListenerForFF implements TextWatcher {

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

                itemList.get(position).setFfEditVolume(s.toString());


                /*item.add(itemList.get(position).getCategoryId() + "-" + itemList.get(position).getCompanyId() + "#" + itemList.get(position).getEditVolume());
                Log.d("competitoritem", item.toString());
*/



            }else {
                itemList.get(position).setFfEditVolume("0");
            }

        }


    }


    private class MycustomFocusForDC implements View.OnFocusChangeListener {
        private int position;


        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                //((CompetitorSaleActivity) context).updateItemStatus(position);

                //   ((CompetitorSaleActivity) context).updateItemStatus(position,false);
            } else {
                ((RefInfoManageActivity) context).updateItemStatusForDC(position,true);


            }



        }
    }


    private class MyCustomEditTextListenerForDC implements TextWatcher {

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

                itemList.get(position).setDcEditVolume(s.toString());


                /*item.add(itemList.get(position).getCategoryId() + "-" + itemList.get(position).getCompanyId() + "#" + itemList.get(position).getEditVolume());
                Log.d("competitoritem", item.toString());
*/



            }else {
                itemList.get(position).setDcEditVolume("0");
            }

        }


    }
}

