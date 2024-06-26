package io.cordova.ifb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.QAModule;

public class QAAdapter extends RecyclerView.Adapter<QAAdapter.MyViewHolder> {
    ArrayList<QAModule>qaList=new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.qa_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvNumber.setText(qaList.get(i).getNumber());
        myViewHolder.tvAnswer.setText(qaList.get(i).getAnswer());


    }

    @Override
    public int getItemCount() {
        return qaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber,tvAnswer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber=(TextView)itemView.findViewById(R.id.tvNumber);
            tvAnswer=(TextView)itemView.findViewById(R.id.tvAns);



        }
    }

    public QAAdapter(ArrayList<QAModule> qaList) {
        this.qaList = qaList;
    }
}
