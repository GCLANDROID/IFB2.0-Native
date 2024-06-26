package io.cordova.ifb.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.CustomerCallingManaeActivity;
import io.cordova.ifb.module.ProductDetailsModel;

public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.MyViewHolder> {
    ArrayList<ProductDetailsModel>itemList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_details_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final ProductDetailsModel approvalModel = itemList.get(i);



        myViewHolder.tvId.setText(itemList.get(i).getSerialNo());
        myViewHolder.tvCategory.setText(itemList.get(i).getCategory());
        myViewHolder.tvProductName.setText(itemList.get(i).getProduct());
        myViewHolder.tvStatus.setText(itemList.get(i).getStatus());

/*
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                approvalModel.setSelected(!approvalModel.isSelected());
                // holder.view.setBackgroundColor(attandanceModel.isSelected() ? Color.CYAN : Color.WHITE);

                if (approvalModel.isSelected()) {

                    myViewHolder.llSelected.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();

                    ((AttendanceApprovalFragment) fContext).updateAttendanceStatus(i, true );



                } else {
                    *//*myViewHolder.imgFrstHalf.setVisibility(View.GONE);
                    myViewHolder.imgScndHalf.setVisibility(View.GONE);
                    myViewHolder.imgFull.setVisibility(View.GONE);*//*
                    myViewHolder.llSelected.setVisibility(View.GONE);
                    ((AttendanceApprovalFragment) fContext).updateAttendanceStatus(i, false);
                    itemList.get(i).setSelected(false);
                    notifyDataSetChanged();
                }*/

        myViewHolder.llTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approvalModel.setSelected(!approvalModel.isSelected());
                // holder.view.setBackgroundColor(attandanceModel.isSelected() ? Color.CYAN : Color.WHITE);

                if (approvalModel.isSelected()) {
                    if (myViewHolder.imgTick.getVisibility()==View.VISIBLE){
                        myViewHolder.imgTick.setVisibility(View.GONE);
                    }

                    notifyDataSetChanged();

                    ((CustomerCallingManaeActivity) context).updateAttendanceStatus(i, false );



                } else {
                    myViewHolder.imgTick.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();

                    ((CustomerCallingManaeActivity) context).updateAttendanceStatus(i, true );



                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId,tvCategory,tvProductName,tvStatus;
        ImageView imgTick;
        LinearLayout llTick;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId=(TextView)itemView.findViewById(R.id.tvId);
            tvCategory=(TextView)itemView.findViewById(R.id.tvCategory);
            tvProductName=(TextView)itemView.findViewById(R.id.tvProductName);
            tvStatus=(TextView)itemView.findViewById(R.id.tvStatus);
            imgTick=(ImageView)itemView.findViewById(R.id.imgTick);
            llTick=(LinearLayout)itemView.findViewById(R.id.llTick);



        }
    }

    public ProductDetailsAdapter(ArrayList<ProductDetailsModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
