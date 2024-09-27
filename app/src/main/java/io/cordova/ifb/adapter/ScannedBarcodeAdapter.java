package io.cordova.ifb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.CVisitModel;
import io.cordova.ifb.module.ScannedPlanogramBarcodeModel;

public class ScannedBarcodeAdapter extends RecyclerView.Adapter<ScannedBarcodeAdapter.MyViewHolder> {
    ArrayList<ScannedPlanogramBarcodeModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scanned_barcode_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

      myViewHolder.tvModelName.setText(itemList.get(i).getModel());
        myViewHolder.tvBarcode.setText(itemList.get(i).getBarcode());
        myViewHolder.tvSL.setText(""+itemList.get(i).getCount());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvBarcode,tvModelName,tvSL;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvModelName=(TextView) itemView.findViewById(R.id.tvModelName);
            tvBarcode=(TextView) itemView.findViewById(R.id.tvBarcode);
            tvSL=(TextView) itemView.findViewById(R.id.tvSL);

        }
    }

    public ScannedBarcodeAdapter(ArrayList<ScannedPlanogramBarcodeModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
