package io.cordova.ifb.adapter;

import android.content.Context;
import android.graphics.Color;
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
import io.cordova.ifb.activity.PlanogramActivity;
import io.cordova.ifb.activity.PlanogramScanReportActivity;
import io.cordova.ifb.module.PlanogramHygeneModel;
import io.cordova.ifb.module.ScannedPlanogramBarcodeModel;

public class PlanopgrameHygeneAdapter extends RecyclerView.Adapter<PlanopgrameHygeneAdapter.MyViewHolder> {
    ArrayList<PlanogramHygeneModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.planogram_report_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {



        myViewHolder.llHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!itemList.get(i).isExpanded()){
                    ((PlanogramScanReportActivity)context).updateStatus(i,true);
                }else {
                    ((PlanogramScanReportActivity)context).updateStatus(i,false);
                }
            }
        });


        if (itemList.get(i).isExpanded())
        {
            myViewHolder.llDetails.setVisibility(View.VISIBLE);
            myViewHolder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.up));

        }
        else
        {
            myViewHolder.llDetails.setVisibility(View.GONE);
            myViewHolder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.down));
        }

        myViewHolder.tvPrdctName.setText(itemList.get(i).getProductName());
        myViewHolder.tvPrdctCode.setText(itemList.get(i).getProductID());
        myViewHolder.tvCategory.setText(itemList.get(i).getCategory());
        myViewHolder.tvDateTime.setText(itemList.get(i).getCreatedOn());
        myViewHolder.tvBarcode.setText(itemList.get(i).getBarcode());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llHeader,llDetails;
        TextView tvBarcode,tvPrdctName,tvPrdctCode,tvCategory,tvDateTime;
        ImageView imgArrow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrdctName=(TextView) itemView.findViewById(R.id.tvPrdctName);
            tvBarcode=(TextView) itemView.findViewById(R.id.tvBarcode);
            tvPrdctCode=(TextView) itemView.findViewById(R.id.tvPrdctCode);
            tvCategory=(TextView) itemView.findViewById(R.id.tvCategory);
            tvDateTime=(TextView) itemView.findViewById(R.id.tvDateTime);
            imgArrow=(ImageView) itemView.findViewById(R.id.imgArrow);

            llHeader=(LinearLayout) itemView.findViewById(R.id.llHeader);
            llDetails=(LinearLayout) itemView.findViewById(R.id.llDetails);

        }
    }

    public PlanopgrameHygeneAdapter(ArrayList<PlanogramHygeneModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
