package io.cordova.ifb.adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.UpdateSerialInvoiceActivity;
import io.cordova.ifb.module.InvoiceSerialModel;

public class SerialInvoiceAdapter extends RecyclerView.Adapter<SerialInvoiceAdapter.MyViewHolder> {
    ArrayList<InvoiceSerialModel>itemList=new ArrayList<>();
    Context context;
    String financialYear,month;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.serial_invoice_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvSerialNumber.setText("Token No. :-"+itemList.get(i).getTokenNumber());
        myViewHolder.tvCategory.setText(itemList.get(i).getCategory());
        myViewHolder.tvModel.setText(itemList.get(i).getProduct());
        myViewHolder.tvCusName.setText(itemList.get(i).getCustpmerName());
        myViewHolder.tvCusMobNumber.setText(itemList.get(i).getCustomerPhn());
        myViewHolder.tvSalesDate.setText(itemList.get(i).getSalesDate());
        myViewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, UpdateSerialInvoiceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("serialnoFlag",itemList.get(i).getSerialFlag());
                intent.putExtra("invoiceFlag",itemList.get(i).getInvoiceFlag());
                intent.putExtra("tokenNo",itemList.get(i).getTokenNumber());
                intent.putExtra("financialYear",financialYear);
                intent.putExtra("month",month);
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSerialNumber,tvSalesDate,tvCategory,tvModel,tvCusName,tvCusMobNumber;
        Button btnUpdate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSerialNumber=itemView.findViewById(R.id.tvSerialNumber);
            tvSalesDate=itemView.findViewById(R.id.tvSalesDate);
            tvCategory=itemView.findViewById(R.id.tvCategory);
            tvModel=itemView.findViewById(R.id.tvModel);
            tvCusName=itemView.findViewById(R.id.tvCusName);
            tvCusMobNumber=itemView.findViewById(R.id.tvCusMobNumber);
            btnUpdate=(Button) itemView.findViewById(R.id.btnUpdate);

        }
    }

    public SerialInvoiceAdapter(ArrayList<InvoiceSerialModel> itemList, Context context,String finYear,String month) {
        this.itemList = itemList;
        this.context = context;
        this.financialYear=finYear;
        this.month=month;
    }
}
