package io.cordova.ifb.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.DummyProductModel;

public class DummyUpdationAdapter extends RecyclerView.Adapter<DummyUpdationAdapter.MyViewHolder> {
    ArrayList<DummyProductModel>reportList=new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dummy_product_raw, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvDate.setText(reportList.get(i).getSaledate());
        myViewHolder.tvTokenNumber.setText(reportList.get(i).getTokenno());
        myViewHolder.tvTempNo.setText(reportList.get(i).getTemptoken());
        myViewHolder.tvModelName.setText(reportList.get(i).getModelname());
        myViewHolder.tvProductCode.setText(reportList.get(i).getProductcode());
        myViewHolder.tvCustomerName.setText(reportList.get(i).getCustomername());
        myViewHolder.tvCustomerPhn.setText(reportList.get(i).getCustomerphn());
        myViewHolder.tvCategoryName.setText(reportList.get(i).getCategoryName());
        myViewHolder.tvTicketNumber.setText(reportList.get(i).getTicketnumber());



    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvTicketNumber,tvTokenNumber,tvModelName,tvProductCode,tvCustomerName,tvCustomerPhn,tvCategoryName,tvTempNo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvTicketNumber=(TextView)itemView.findViewById(R.id.tvTicketNumber);
            tvTokenNumber=(TextView)itemView.findViewById(R.id.tvTokenNumber);
            tvModelName=(TextView)itemView.findViewById(R.id.tvModelName);
            tvProductCode=(TextView)itemView.findViewById(R.id.tvProductCode);
            tvCustomerName=(TextView)itemView.findViewById(R.id.tvCustomerName);
            tvCustomerPhn=(TextView)itemView.findViewById(R.id.tvCustomerPhn);
            tvCategoryName=(TextView)itemView.findViewById(R.id.tvCategoryName);
            tvTempNo=(TextView)itemView.findViewById(R.id.tvTempNo);


        }
    }

    public DummyUpdationAdapter(ArrayList<DummyProductModel> reportList) {
        this.reportList = reportList;
    }
}
