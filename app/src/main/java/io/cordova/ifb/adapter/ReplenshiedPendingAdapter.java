package io.cordova.ifb.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

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
import io.cordova.ifb.activity.ReplenshedUpdateActivity;
import io.cordova.ifb.module.ReplenshiedModel;
import io.cordova.ifb.utility.PrefManager;

public class ReplenshiedPendingAdapter extends RecyclerView.Adapter<ReplenshiedPendingAdapter.MyViewHolder> {
    ArrayList<ReplenshiedModel>reportList=new ArrayList<>();
    Context context;
    AlertDialog.Builder builder;
    PrefManager prefManager;
    ProgressDialog pd;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.replenished_pending_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        builder=new AlertDialog.Builder(context);
        prefManager=new PrefManager(context);
         pd = new ProgressDialog(context);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        myViewHolder.tvSalesDate.setText(reportList.get(i).getSalesDate());
        myViewHolder.tvProduct.setText(reportList.get(i).getProductName());
        myViewHolder.tvModel.setText(reportList.get(i).getModelName());

        if (reportList.get(i).getStatus().equals("PENDING")){
            myViewHolder.llStatus.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.llStatus.setVisibility(View.GONE);
        }

        myViewHolder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ReplenshedUpdateActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("salesDate",reportList.get(i).getSalesDate());
                intent.putExtra("productName",reportList.get(i).getProductName());
                intent.putExtra("ModelName",reportList.get(i).getModelName());
                intent.putExtra("id",reportList.get(i).getId());
                intent.putExtra("flag","1");
                intent.putExtra("categoryid",reportList.get(i).getCategoryID());
                intent.putExtra("modelcode",reportList.get(i).getModelID());
                context.startActivity(intent);
            }
        });

        myViewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ReplenshedUpdateActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("salesDate",reportList.get(i).getSalesDate());
                intent.putExtra("productName",reportList.get(i).getProductName());
                intent.putExtra("ModelName",reportList.get(i).getModelName());
                intent.putExtra("id",reportList.get(i).getId());
                intent.putExtra("flag","2");
                intent.putExtra("categoryid",reportList.get(i).getCategoryID());
                intent.putExtra("modelcode",reportList.get(i).getModelID());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSalesDate,tvProduct,tvModel;
        ImageView imgUpdate,imgDelete;
        LinearLayout llStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSalesDate=(TextView)itemView.findViewById(R.id.tvSalesDate);
            tvProduct=(TextView)itemView.findViewById(R.id.tvProduct);
            tvModel=(TextView)itemView.findViewById(R.id.tvModel);

            imgUpdate=(ImageView)itemView.findViewById(R.id.imgUpdate);
            imgDelete=(ImageView)itemView.findViewById(R.id.imgDelete);

            llStatus=(LinearLayout)itemView.findViewById(R.id.llStatus);


        }
    }

    public ReplenshiedPendingAdapter(ArrayList<ReplenshiedModel> reportList,Context context) {
        this.reportList = reportList;
        this.context=context;
    }




}
