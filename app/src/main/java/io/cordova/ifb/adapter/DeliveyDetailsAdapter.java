package io.cordova.ifb.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import io.cordova.ifb.activity.DeliveryAddressUpdateActivity;
import io.cordova.ifb.activity.DeliveryDetailsActivity;
import io.cordova.ifb.activity.SalesUpdateActivity;
import io.cordova.ifb.module.DeliveryDetailsModel;

public class DeliveyDetailsAdapter extends RecyclerView.Adapter<DeliveyDetailsAdapter.MyViewHolder> {
    ArrayList<DeliveryDetailsModel>reportList=new ArrayList<>();
    Context mContext;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.delivery_details_raw, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvDate.setText(reportList.get(i).getDate());
        myViewHolder.tvRefNumber.setText(reportList.get(i).getRefNo());
        myViewHolder.tvCustomerName.setText(reportList.get(i).getCusName());
        myViewHolder.tvProduct.setText(reportList.get(i).getCategory());
        myViewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to cancel this ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((DeliveryDetailsActivity)mContext).cancel(reportList.get(i).getRefNo());

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Alert");
                alert.show();
            }
        });
        myViewHolder.btnDelivey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DeliveryAddressUpdateActivity.class);
                intent.putExtra("refNo",reportList.get(i).getRefNo());
                intent.putExtra("contactNumber",reportList.get(i).getContactNumber());
                intent.putExtra("altNumber",reportList.get(i).getAltNumber());
                intent.putExtra("modelcode",reportList.get(i).getModelCode());
                intent.putExtra("product",reportList.get(i).getCategory());
                intent.putExtra("InstallationBy",reportList.get(i).getInstallationBy());
                intent.putExtra("WiFiDeviceStatus",reportList.get(i).getWiFiDeviceStatus());
                intent.putExtra("SalesType",reportList.get(i).getSalesType());
                intent.putExtra("qty",reportList.get(i).getQuantity());
                intent.putExtra("cusname",reportList.get(i).getCusName());
                intent.putExtra("address",reportList.get(i).getAddress());
                intent.putExtra("pincode",reportList.get(i).getPincode());
                intent.putExtra("categoryId",reportList.get(i).getCategoryID());
                intent.putExtra("underexchange",reportList.get(i).getUnderExchange());
                intent.putExtra("financescheme",reportList.get(i).getFinancialScheme());
                intent.putExtra("firstname",reportList.get(i).getFirstName());
                intent.putExtra("lastname",reportList.get(i).getLastName());
                intent.putExtra("month",reportList.get(i).getMonth());
                intent.putExtra("financilayear",reportList.get(i).getFinancialyear());
                intent.putExtra("invoicevalue",reportList.get(i).getInvoiceValue());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        myViewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, SalesUpdateActivity.class);
                intent.putExtra("refNo",reportList.get(i).getRefNo());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvRefNumber,tvProduct,tvCustomerName;
        Button btnCancel,btnDelivey,btnUpdate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvRefNumber=(TextView)itemView.findViewById(R.id.tvRefNumber);
            tvCustomerName=(TextView)itemView.findViewById(R.id.tvCustomerName);
            tvProduct=(TextView)itemView.findViewById(R.id.tvProduct);
            btnCancel=(Button)itemView.findViewById(R.id.btnCancel);
            btnDelivey=(Button)itemView.findViewById(R.id.btnDelivey);
            btnUpdate=(Button)itemView.findViewById(R.id.btnUpdate);

        }
    }

    public DeliveyDetailsAdapter(ArrayList<DeliveryDetailsModel> reportList, Context mContext) {
        this.reportList = reportList;
        this.mContext = mContext;
    }
}
