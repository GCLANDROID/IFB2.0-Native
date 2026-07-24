package io.cordova.ifb.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.cordova.ifb.R;
import io.cordova.ifb.module.CompetitonSalesBrandModel;

public class CompSalesBrandAdapter extends RecyclerView.Adapter<CompSalesBrandAdapter.BrandViewHolder>{
    private Context context;
    private List<CompetitonSalesBrandModel> brandList;
    private OnQuantityChangeListener quantityChangeListener;

    public interface OnQuantityChangeListener {
        void onQuantityChanged(String brandId, String qty);
    }

    public CompSalesBrandAdapter(Context context, OnQuantityChangeListener listener) {
        this.context = context;
        this.brandList = new ArrayList<>();
        this.quantityChangeListener = listener;
    }

    public void setBrandList(List<CompetitonSalesBrandModel> brandList) {
        this.brandList = brandList;
        notifyDataSetChanged();
    }

    public List<CompetitonSalesBrandModel> getBrandList() {
        return brandList;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_brand_row, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        CompetitonSalesBrandModel brand = brandList.get(position);

        holder.brandName.setText(brand.getName());

        // Set brand color indicator
        int color = android.graphics.Color.parseColor(brand.getColor());
        holder.colorIndicator.setBackgroundColor(color);
        holder.colorIndicator.setVisibility(View.VISIBLE);

        // Set quantity
        holder.qtyInput.setText(brand.getQty());

        // Highlight if quantity is entered
        if (brand.getQty() != null && !brand.getQty().isEmpty() && Integer.parseInt(brand.getQty()) > 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.brand_filled));
            holder.qtyInput.setTextColor(color);
            holder.qtyInput.setBackgroundResource(R.drawable.edittext_filled_border);
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.brand_empty));
            holder.qtyInput.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
            holder.qtyInput.setBackgroundResource(R.drawable.edittext_border);
        }

        // Set TextWatcher for quantity changes
        holder.qtyInput.removeTextChangedListener(holder.textWatcher);
        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String qty = s.toString();
                brand.setQty(qty);
                if (quantityChangeListener != null) {
                    quantityChangeListener.onQuantityChanged(brand.getBrandId(), qty);
                }
                // Update UI
                if (!qty.isEmpty() && Integer.parseInt(qty) > 0) {
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.brand_filled));
                    holder.qtyInput.setTextColor(color);
                    holder.qtyInput.setBackgroundResource(R.drawable.edittext_filled_border);
                } else {
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.brand_empty));
                    holder.qtyInput.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
                    holder.qtyInput.setBackgroundResource(R.drawable.edittext_border);
                }
            }
        };
        holder.qtyInput.addTextChangedListener(holder.textWatcher);
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    static class BrandViewHolder extends RecyclerView.ViewHolder {
        TextView brandName;
        EditText qtyInput;
        TextWatcher textWatcher;
        View colorIndicator;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            brandName = itemView.findViewById(R.id.brandName);
            colorIndicator = itemView.findViewById(R.id.colorIndicator);
            qtyInput = itemView.findViewById(R.id.qtyInput);
        }
    }
}
