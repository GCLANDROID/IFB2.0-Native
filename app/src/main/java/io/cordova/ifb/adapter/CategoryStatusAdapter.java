package io.cordova.ifb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.cordova.ifb.R;
import io.cordova.ifb.module.CategoryStatusItem;

public class CategoryStatusAdapter extends RecyclerView.Adapter<CategoryStatusAdapter.ViewHolder> {
    private Context context;
    private List<CategoryStatusItem> categoryList;

    public CategoryStatusAdapter(Context context, List<CategoryStatusItem> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryStatusItem item = categoryList.get(position);

        holder.tvCategoryName.setText(item.getCategoryName());
        holder.tvCategoryId.setText(item.getCategoryId());

        if (item.isCompleted()) {
            holder.tvStatus.setText("✅ Completed");
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.statusIndicator.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.completed_bg));
        } else {
            holder.tvStatus.setText("⏳ Pending");
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.statusIndicator.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.pending_bg));
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName, tvCategoryId, tvStatus;
        View statusIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvCategoryId = itemView.findViewById(R.id.tvCategoryId);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            statusIndicator = itemView.findViewById(R.id.statusIndicator);
        }
    }
}
