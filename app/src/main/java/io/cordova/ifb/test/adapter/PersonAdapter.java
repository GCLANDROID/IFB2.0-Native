package io.cordova.ifb.test.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.test.model.PersonModel;
import io.cordova.ifb.test.model.RangeModel;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {
    private static final String TAG = "PersonAdapter";
    Context context;
    ArrayList<PersonModel> personList;
    ArrayList<RangeModel> rangeArray;
    ArrayList<String> rangeString;

    public PersonAdapter(Context context, ArrayList<PersonModel> personList,ArrayList<RangeModel> rangeArray,ArrayList<String> rangeString) {
        this.context = context;
        this.personList = personList;
        this.rangeArray = rangeArray;
        this.rangeString = rangeString;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtUserName.setText(personList.get(position).getName());
        holder.edtItem.setText(personList.get(position).getRange());

        int index = rangeString.indexOf(personList.get(position).getText());
        Log.e(TAG, "Text: "+personList.get(position).getText());
        Log.e(TAG, "index: "+index);
        ItemSpinnerAdapter itemSpinnerAdapter = new ItemSpinnerAdapter(context,rangeArray);
        holder.itemSpinner.setAdapter(itemSpinnerAdapter);
        //holder.itemSpinner.setOnItemSelectedListener(null);
        holder.itemSpinner.setSelection(index);



        holder.itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                personList.get(position).setText(rangeArray.get(p).getText());
                personList.get(position).setRange(String.valueOf(rangeArray.get(p).getRange()));
                Toast.makeText(context, rangeArray.get(p).getText(), Toast.LENGTH_SHORT).show();
                holder.edtItem.setText(String.valueOf(rangeArray.get(p).getRange()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.edtItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    int index = rangeString.indexOf(findRange(Integer.parseInt(s.toString())));
                    holder.itemSpinner.setSelection(index);
                }
            }
        });

        holder.edtItem2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                personList.get(position).setRemask(s.toString().trim());
            }
        });
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtUserName;
        EditText edtItem,edtItem2;
        Spinner itemSpinner;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            edtItem = itemView.findViewById(R.id.edtItem);
            edtItem2 = itemView.findViewById(R.id.edtItem2);
            itemSpinner = itemView.findViewById(R.id.itemSpinner);
        }
    }

    String findRange(int value){
        int pre = 0;
        for (int i = 0; i < rangeArray.size(); i++) {
            if(rangeArray.get(i).getRange() == value){
                return rangeArray.get(i).getText();
            } else if (rangeArray.get(i).getRange() < value){
                return rangeArray.get(i).getText();
            } else if (rangeArray.get(i).getRange() > value){
                if (i == 0){
                    pre = rangeArray.get(i).getRange();
                } else {
                    if (pre < value && value < rangeArray.get(i).getRange()){
                        return rangeArray.get(i).getText();
                    }
                }
            }
        }
        return rangeArray.get(rangeArray.size()-1).getText();
    }
}
