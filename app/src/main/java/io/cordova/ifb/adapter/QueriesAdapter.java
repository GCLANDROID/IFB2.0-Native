package io.cordova.ifb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.QueriesModel;

public class QueriesAdapter extends RecyclerView.Adapter<QueriesAdapter.MyViewHolder> {
    ArrayList<QueriesModel>queriesList=new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.queries_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvDate.setText(queriesList.get(i).getDate());
        myViewHolder.tvIssue.setText(queriesList.get(i).getIssue());
        myViewHolder.tvQuery.setText(queriesList.get(i).getQuery());
        myViewHolder.tvReply.setText(queriesList.get(i).getReply());
        myViewHolder.tvStatus.setText(queriesList.get(i).getStatus());


    }

    @Override
    public int getItemCount() {
        return queriesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvIssue,tvReply,tvQuery,tvStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvIssue=(TextView)itemView.findViewById(R.id.tvIssue);
            tvReply=(TextView)itemView.findViewById(R.id.tvReply);
            tvQuery=(TextView)itemView.findViewById(R.id.tvQuery);
            tvStatus=(TextView)itemView.findViewById(R.id.tvStatus);


        }
    }

    public QueriesAdapter(ArrayList<QueriesModel> queriesList) {
        this.queriesList = queriesList;
    }
}
