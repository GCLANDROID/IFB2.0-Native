package io.cordova.ifb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.QusetionModel;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {
    ArrayList<QusetionModel> itemList = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvQuestion.setText(itemList.get(i).getQuestion());
        myViewHolder.tvHint.setText(itemList.get(i).getHints());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion,tvHint;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion=(TextView)itemView.findViewById(R.id.tvQuestion);
            tvHint=(TextView)itemView.findViewById(R.id.tvHint);

        }
    }


    public QuestionAdapter(ArrayList<QusetionModel> itemList) {
        this.itemList = itemList;
    }
}
