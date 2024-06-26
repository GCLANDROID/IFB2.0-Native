package io.cordova.ifb.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.IMEIReqModel;

public class ImeiReqAdapter extends RecyclerView.Adapter<ImeiReqAdapter.MyViewHolder> {
    ArrayList<IMEIReqModel>itemList=new ArrayList<>();
    Context mContext;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.imei_req_details_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvImeiNumber.setText("IMEI Number : "+itemList.get(i).getImeiNumber());
        myViewHolder.tvRequestedOn.setText("Requested On : "+itemList.get(i).getReqDetails());
        if (itemList.get(i).getApprovalStatus().equals("")){
            myViewHolder.tvRemarks.setText("Pending");
        }else {
            myViewHolder.tvRemarks.setText(itemList.get(i).getApprovalStatus());
        }




    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvImeiNumber,tvRemarks,tvRequestedOn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRequestedOn=(TextView) itemView.findViewById(R.id.tvRequestedOn);
            tvImeiNumber=(TextView) itemView.findViewById(R.id.tvImeiNumber);
            tvRemarks=(TextView) itemView.findViewById(R.id.tvRemarks);


        }
    }

    public ImeiReqAdapter(ArrayList<IMEIReqModel> itemList, Context mContext) {
        this.itemList = itemList;
        this.mContext = mContext;
    }
}
