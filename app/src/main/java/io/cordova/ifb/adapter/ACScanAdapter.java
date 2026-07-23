package io.cordova.ifb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.AcProductScanActivity;
import io.cordova.ifb.module.ACModel;
import io.cordova.ifb.module.CallingReportModel;

public class ACScanAdapter extends RecyclerView.Adapter<ACScanAdapter.MyViewHolder> {
    ArrayList<ACModel>itemList=new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ac_scan_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        ACModel model = itemList.get(i);
        myViewHolder.tvProductName.setText(model.getValue());
        if (model.isSelected()) {
            myViewHolder.imgPlus.setVisibility(View.GONE);
            myViewHolder.imgMinus.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.imgPlus.setVisibility(View.VISIBLE);
            myViewHolder.imgMinus.setVisibility(View.GONE);
        }


        myViewHolder.imgPlus.setOnClickListener(v -> {
            model.setSelected(true);
            notifyItemChanged(i);

            try {
                JSONObject obj = new JSONObject();
                obj.put("ModelCode", model.getId()); // using id as ModelCode
                AcProductScanActivity.selectedArray.put(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        myViewHolder.imgMinus.setOnClickListener(v -> {
            model.setSelected(false);
            notifyItemChanged(i);

            // Remove from JSON array
            JSONArray newArray = new JSONArray();

            for (int j = 0; j < AcProductScanActivity.selectedArray.length(); j++) {
                try {
                    JSONObject obj = AcProductScanActivity.selectedArray.getJSONObject(j);
                    if (!obj.getString("ModelCode").equals(model.getId())) {
                        newArray.put(obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            AcProductScanActivity.selectedArray = newArray;
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName;
        ImageView imgPlus,imgMinus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName=(TextView)itemView.findViewById(R.id.tvProductName);
            imgMinus=itemView.findViewById(R.id.imgMinus);
            imgPlus=itemView.findViewById(R.id.imgPlus);

        }
    }

    public ACScanAdapter(ArrayList<ACModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
    public void updateList(ArrayList<ACModel> list){
        itemList = list;
        notifyDataSetChanged();
    }
}
