package io.cordova.ifb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.cordova.ifb.R;
import io.cordova.ifb.module.CategoryData;

public class NewIncnetiveCategoryAdapter extends RecyclerView.Adapter<NewIncnetiveCategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<CategoryData> categoryList;
    private DecimalFormat df;

    public NewIncnetiveCategoryAdapter(Context context) {
        this.context = context;
        this.categoryList = new ArrayList<>();
        this.df = new DecimalFormat("#.#");
    }

    public void setCategoryList(List<CategoryData> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_row, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryData data = categoryList.get(position);

        String shortName = getShortCategoryName(data.getName());
        holder.categoryName.setText(shortName);
        holder.targetText.setText(String.valueOf(data.getTarget()));
        holder.soldText.setText(String.valueOf(data.getSold()));
        holder.percentageText.setText(df.format(data.getPercentage()) + "%");
        holder.earnText.setText("₹" + df.format(data.getEarn()));

        // Set background for alternating rows
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.row_even));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.row_odd));
        }

        // Set percentage color
        if (data.getPercentage() >= 80) {
            holder.percentageText.setTextColor(ContextCompat.getColor(context, R.color.high_percentage));
        } else if (data.getPercentage() >= 60) {
            holder.percentageText.setTextColor(ContextCompat.getColor(context, R.color.medium_percentage));
        } else {
            holder.percentageText.setTextColor(ContextCompat.getColor(context, R.color.low_percentage));
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName, targetText, soldText, percentageText, earnText;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            targetText = itemView.findViewById(R.id.targetText);
            soldText = itemView.findViewById(R.id.soldText);
            percentageText = itemView.findViewById(R.id.percentageText);
            earnText = itemView.findViewById(R.id.earnText);
        }
    }

    private String getShortCategoryName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return "N/A";
        }

        String upperName = fullName.toUpperCase().trim();

        // Check for specific category names (check most specific first)
        if (upperName.contains("WASHING MACHINE-TL") || upperName.contains("WASHING MACHINE TL") ||
                upperName.contains("TL") && upperName.contains("WASHING")) {
            return "WM-TL";
        } else if (upperName.contains("WASHING MACHINE-FL") || upperName.contains("WASHING MACHINE FL") ||
                upperName.contains("FL") && upperName.contains("WASHING")) {
            return "WM_FL";
        } else if (upperName.contains("DISHWASHER") || upperName.contains("DIS") ||
                upperName.contains("DISH WASHER")) {
            return "DW";
        } else if (upperName.contains("REFRIGERATOR") || upperName.contains("REF") ||
                upperName.contains("REFRIGERATOR_APPLIANCE") ||
                (upperName.contains("RF") && !upperName.contains("WASHING"))) {
            return "REF";
        } else if (upperName.contains("KITCHEN") || upperName.contains("KA") ||
                upperName.contains("KITCHEN_APPLIANCE")) {
            return "KA";
        } else if (upperName.contains("AIR CONDITIONER") || upperName.contains("AC") ||
                upperName.contains("AIRCONDITIONER")) {
            return "AC";
        }

        // If no match, get first 3 characters
        if (fullName.length() >= 3) {
            return fullName.substring(0, 3).toUpperCase();
        }

        return fullName.toUpperCase();
    }
}
