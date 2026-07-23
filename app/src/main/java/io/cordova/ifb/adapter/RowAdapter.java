package io.cordova.ifb.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.NewCompetitorDisplayMatrixActivity;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.RowViewHolder> {

    NewCompetitorDisplayMatrixActivity.Category category;
//    HorizontalScrollView headerScroll;
//
//    List<HorizontalScrollView> scrollViews = new ArrayList<>();
    boolean responseStatus;

    private int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }


    public RowAdapter(NewCompetitorDisplayMatrixActivity.Category category, /*HorizontalScrollView headerScroll,*/boolean responseStatus) {
        this.category = category;
       // this.headerScroll = headerScroll;
        this.responseStatus=responseStatus;
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_dynamic, parent, false);
        return new RowViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {

        NewCompetitorDisplayMatrixActivity.BrandRow row = category.brands.get(position);
        Context context = holder.itemView.getContext();

        // ✅ Set Brand Name
        holder.txtBrand.setText(row.brandName);

        // ✅ Clear old views (VERY IMPORTANT)
        holder.containerCapacity.removeAllViews();

        int total = 0;

//        for (int i = 0; i < category.capacities.size(); i++) {
//
//            int value = row.values.get(i);
//            total += value;
//
//            EditText et = new EditText(context);
//
//            // 🔥 IMPORTANT: width must match header
//            LinearLayout.LayoutParams params =
//                    new LinearLayout.LayoutParams(70, ViewGroup.LayoutParams.MATCH_PARENT);
//            params.setMargins(4, 0, 4, 0);
//
//            et.setLayoutParams(params);
//
//            // ✅ Apply your border design
//            et.setBackgroundResource(R.drawable.lldesign24);
//            et.setBackgroundTintList(null);
//
//            et.setText(String.valueOf(value));
//            et.setGravity(Gravity.CENTER);
//            et.setTextSize(10);
//            et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });
//            et.setInputType(InputType.TYPE_CLASS_NUMBER);
//            et.setPadding(4, 8, 4, 8);
//            if (responseStatus){
//                et.setEnabled(true);
//            }else {
//                et.setEnabled(false);
//            }
//
//            int index = i;
//
//            et.addTextChangedListener(new TextWatcher() {
//                @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
//                @Override public void onTextChanged(CharSequence s, int st, int b, int c) {}
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                    int val = 0;
//                    if (!s.toString().isEmpty()) {
//                        val = Integer.parseInt(s.toString());
//                    }
//
//                    // ✅ Save value in model
//                    row.values.set(index, val);
//
//                    // ✅ Update total WITHOUT refreshing row
//                    int sum = 0;
//                    for (int v : row.values) {
//                        sum += v;
//                    }
//
//                    holder.txtTotal.setText(String.valueOf(sum));
//                }
//            });
//
//            holder.containerCapacity.addView(et);
//        }


        List<NewCompetitorDisplayMatrixActivity.CapacityItem> capList = row.capacityList;

        int cellWidth;

        if (capList.size() <= 5) {
            // ✅ Fit to screen
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;

            int brandWidth = dpToPx(context, 40);
            int totalWidth = dpToPx(context, 40);
            int padding = dpToPx(context, 32);
            int totalMargin = dpToPx(context, 8 * capList.size());

            int availableWidth = screenWidth - brandWidth - totalWidth - padding - totalMargin;

            cellWidth = availableWidth / capList.size();
            holder.txtBrand.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        } else {
            // ✅ Use scroll (BEST for 6+ columns)
            cellWidth = dpToPx(context, 70);
            holder.txtBrand.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        }
        for (int i = 0; i < capList.size(); i++) {

            NewCompetitorDisplayMatrixActivity.CapacityItem item = capList.get(i);

            int value = item.qty;
            total += value;

            EditText et = new EditText(context);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(cellWidth, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(4, 0, 4, 0);

            et.setLayoutParams(params);
            et.setBackgroundResource(R.drawable.lldesign24);
            et.setBackgroundTintList(null);

            et.setText(String.valueOf(value));
            et.setGravity(Gravity.CENTER);
            et.setTextSize(10);
            et.setTextColor(ContextCompat.getColor(context, R.color.black));
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
            et.setPadding(4, 8, 4, 8);

            //et.setEnabled(responseStatus);

            int index = i;
            et.setOnFocusChangeListener((v, hasFocus) -> {

                if (hasFocus) {
                    // 🔹 Remove 0 when focused
                    if (et.getText().toString().equals("0")) {
                        et.setText("");
                    }
                } else {
                    // 🔹 If empty, set 0 back
                    if (et.getText().toString().trim().isEmpty()) {
                        et.setText("0");
                    }
                }
            });

            et.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
                @Override public void onTextChanged(CharSequence s, int st, int b, int c) {}

                @Override
                public void afterTextChanged(Editable s) {

                    int val = 0;
                    if (!s.toString().isEmpty()) {
                        try {
                            val = Integer.parseInt(s.toString());
                        } catch (Exception e) {
                            val = 0;
                        }
                    }

                    // ✅ Update MODEL (CRITICAL)
                    capList.get(index).qty = val;

                    // ✅ Recalculate total
                    int sum = 0;
                    for (NewCompetitorDisplayMatrixActivity.CapacityItem cItem : capList) {
                        sum += cItem.qty;
                    }

                    holder.txtTotal.setText(String.valueOf(sum));
                }
            });

            holder.containerCapacity.addView(et);
        }

        // ✅ Set total initially
        holder.txtTotal.setText(String.valueOf(total));

//
//        List<EditText> editTextList = new ArrayList<>();
//
//        for (int i = 0; i < capList.size(); i++) {
//
//            NewCompetitorDisplayMatrixActivity.CapacityItem item = capList.get(i);
//
//            int value = item.qty;
//            total += value;
//
//            // 🔹 Parent layout (column)
//            LinearLayout parent = new LinearLayout(context);
//            parent.setOrientation(LinearLayout.VERTICAL);
//
//            LinearLayout.LayoutParams parentParams =
//                    new LinearLayout.LayoutParams(cellWidth, ViewGroup.LayoutParams.MATCH_PARENT);
//            parentParams.setMargins(4, 0, 4, 0);
//            parent.setLayoutParams(parentParams);
//
//            // 🔹 EditText
//            EditText et = new EditText(context);
//
//            et.setLayoutParams(new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
//
//            et.setBackgroundResource(R.drawable.lldesign24);
//            et.setBackgroundTintList(null);
//            et.setText(String.valueOf(value));
//            et.setGravity(Gravity.CENTER);
//            et.setTextSize(10);
//            et.setTextColor(ContextCompat.getColor(context, R.color.black));
//            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
//            et.setInputType(InputType.TYPE_CLASS_NUMBER);
//            et.setPadding(4, 8, 4, 8);
//            et.setSelectAllOnFocus(true);
//
//            int index = i;
//
//            et.addTextChangedListener(new TextWatcher() {
//                @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
//                @Override public void onTextChanged(CharSequence s, int st, int b, int c) {}
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                    int val = 0;
//                    if (!s.toString().isEmpty()) {
//                        val = Integer.parseInt(s.toString());
//                    }
//
//                    capList.get(index).qty = val;
//
//                    int sum = 0;
//                    for (NewCompetitorDisplayMatrixActivity.CapacityItem cItem : capList) {
//                        sum += cItem.qty;
//                    }
//
//                    holder.txtTotal.setText(String.valueOf(sum));
//                }
//            });
//
//            // 🔹 Arrow Layout
//            LinearLayout arrowLayout = new LinearLayout(context);
//            arrowLayout.setOrientation(LinearLayout.HORIZONTAL);
//            arrowLayout.setGravity(Gravity.CENTER);
//
//            ImageView btnLeft = new ImageView(context);
//            btnLeft.setImageResource(R.drawable.ic_baseline_chevron_left_24);
//
//
//            ImageView btnRight = new ImageView(context);
//            btnRight.setImageResource(R.drawable.ic_baseline_chevron_right_24);
//
//            arrowLayout.addView(btnLeft);
//            arrowLayout.addView(btnRight);
//
//            // 🔹 Add views
//            parent.addView(et);
//            parent.addView(arrowLayout);
//            holder.containerCapacity.addView(parent);
//
//            // 🔹 Store EditText
//            editTextList.add(et);
//
//            // 🔥 Navigation
//            btnRight.setOnClickListener(v -> {
//                if (index < editTextList.size() - 1) {
//                    editTextList.get(index + 1).requestFocus();
//                }
//            });
//
//            btnLeft.setOnClickListener(v -> {
//                if (index > 0) {
//                    editTextList.get(index - 1).requestFocus();
//                }
//            });
//
//            // 🔥 Show/Hide arrows
//            if (i == 0) {
//                btnLeft.setVisibility(View.INVISIBLE);
//            }
//
//            if (i == capList.size() - 1) {
//                btnRight.setVisibility(View.INVISIBLE);
//            }
//        }

        // 🔥 OPTIONAL: SCROLL SYNC (if you already implemented)
//        if (headerScroll != null) {
//            holder.rowScroll.getViewTreeObserver().addOnScrollChangedListener(() -> {
//                headerScroll.scrollTo(holder.rowScroll.getScrollX(), 0);
//            });
//
//            headerScroll.getViewTreeObserver().addOnScrollChangedListener(() -> {
//                holder.rowScroll.scrollTo(headerScroll.getScrollX(), 0);
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return category.brands.size();
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {

        TextView txtBrand;
        TextView txtTotal;

        //HorizontalScrollView rowScroll;
        LinearLayout containerCapacity;

        public RowViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBrand = itemView.findViewById(R.id.txtBrand);
            txtTotal = itemView.findViewById(R.id.txtTotal);

            //rowScroll = itemView.findViewById(R.id.rowScroll);
            containerCapacity = itemView.findViewById(R.id.containerCapacity);
        }
    }
}