package io.cordova.ifb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.CompSalesBrandAdapter;
import io.cordova.ifb.databinding.ActivityNewCompSalesBinding;
import io.cordova.ifb.module.CompetitonSalesBrandModel;

public class NewCompSalesActivity extends AppCompatActivity {
    ActivityNewCompSalesBinding binding;
    private Spinner spinnerCategory;
    private CompSalesBrandAdapter brandAdapter;
    private LinearLayout brandListContainer, savedDataContainer;
    private LinearLayout savedEntriesContainer;

    private List<CompetitonSalesBrandModel> brandList;
    private String selectedCategory = "";
    private List<SavedEntry> savedEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_new_comp_sales);
        initViews();
    }

    private void initViews() {
        spinnerCategory = findViewById(R.id.spinnerCategory);
        brandListContainer = findViewById(R.id.brandListContainer);
        savedDataContainer = findViewById(R.id.savedDataContainer);
        savedEntriesContainer = findViewById(R.id.savedEntriesContainer);

        String[] categories = {
                "Select Category",
                "AIR CONDITIONER",
                "DISHWASHER",
                "WASHING MACHINE-FL",
                "WASHING MACHINE-TL",
                "KITCHEN_APPLIANCE",
                "REFRIGERATOR_APPLIANCE"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedCategory = categories[position];
                } else {
                    selectedCategory = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = "";
            }
        });
        setupBrandList();
        setupButtons();
        displaySavedEntries();
    }


    private void setupBrandList() {
        brandList = new ArrayList<>();

        // Initialize brands with colors and IDs
        brandList.add(new CompetitonSalesBrandModel("1", "IFB9000", "Samsung", "", "#1428A0"));
        brandList.add(new CompetitonSalesBrandModel("2", "IFB9001", "LG", "", "#A50034"));
        brandList.add(new CompetitonSalesBrandModel("3", "IFB0001", "Beko", "", "#005B9F"));
        brandList.add(new CompetitonSalesBrandModel("4", "IFB9002", "Voltas", "", "#ED1C24"));
        brandList.add(new CompetitonSalesBrandModel("5", "IFB9003", "Daikin", "", "#0068B4"));
        brandList.add(new CompetitonSalesBrandModel("6", "IFB9004", "Hitachi", "", "#E60012"));
        brandList.add(new CompetitonSalesBrandModel("7", "IFB9005", "Whirlpool", "", "#004B87"));
        brandList.add(new CompetitonSalesBrandModel("8", "IFB9006", "Godrej", "", "#E31E24"));
        brandList.add(new CompetitonSalesBrandModel("9", "IFB9007", "Haier", "", "#00529B"));
        brandList.add(new CompetitonSalesBrandModel("10", "IFB9008", "Panasonic", "", "#003DA5"));

        brandAdapter = new CompSalesBrandAdapter(this, (brandId, qty) -> {
            updateStats();
        });

        binding.recyclerViewBrands.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewBrands.setAdapter(brandAdapter);
        binding.recyclerViewBrands.setHasFixedSize(true);
        binding.recyclerViewBrands.setNestedScrollingEnabled(false);
    }

    private void setupButtons() {
        binding.btnShow.setOnClickListener(v -> {
            if (TextUtils.isEmpty(selectedCategory)) {
                Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
                return;
            }

            // Reset quantities
            for (CompetitonSalesBrandModel brand : brandList) {
                brand.setQty("");
            }
            brandAdapter.setBrandList(brandList);
            brandListContainer.setVisibility(View.VISIBLE);
            binding.tvSelectedCategory.setText("Category: " + selectedCategory);
            updateStats();
        });

        binding.btnSave.setOnClickListener(v -> {
            List<CompetitonSalesBrandModel> filledBrands = new ArrayList<>();
            for (CompetitonSalesBrandModel brand : brandList) {
                if (brand.getQty() != null && !brand.getQty().isEmpty() &&
                        Integer.parseInt(brand.getQty()) > 0) {
                    filledBrands.add(brand);
                }
            }

            if (filledBrands.isEmpty()) {
                Toast.makeText(this, "Please enter quantity for at least one brand",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Save entry
            SavedEntry entry = new SavedEntry();
            entry.id = System.currentTimeMillis();
            entry.category = selectedCategory;
            entry.date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            entry.time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
            entry.brands = new ArrayList<>(filledBrands);
            entry.totalQuantity = 0;
            for (CompetitonSalesBrandModel brand : filledBrands) {
                entry.totalQuantity += Integer.parseInt(brand.getQty());
            }

            savedEntries.add(0, entry);
            displaySavedEntries();

            // Reset form
            for (CompetitonSalesBrandModel brand : brandList) {
                brand.setQty("");
            }
            brandAdapter.setBrandList(brandList);
            brandListContainer.setVisibility(View.GONE);
            updateStats();

            Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
        });

        binding.btnClear.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Clear All")
                    .setMessage("Are you sure you want to clear all entered quantities?")
                    .setPositiveButton("Clear", (dialog, which) -> {
                        for (CompetitonSalesBrandModel brand : brandList) {
                            brand.setQty("");
                        }
                        brandAdapter.setBrandList(brandList);
                        updateStats();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        binding.btnGenerateJson.setOnClickListener(v -> {
            if (savedEntries.isEmpty()) {
                Toast.makeText(this, "No saved data to generate JSON", Toast.LENGTH_SHORT).show();
                return;
            }
            generateAndShowJson();
        });
    }

    private void updateStats() {
        int totalBrands = 0;
        int totalQuantity = 0;

        for (CompetitonSalesBrandModel brand : brandList) {
            if (brand.getQty() != null && !brand.getQty().isEmpty()) {
                int qty = Integer.parseInt(brand.getQty());
                if (qty > 0) {
                    totalBrands++;
                    totalQuantity += qty;
                }
            }
        }

        binding.tvTotalBrands.setText("Brands: " + totalBrands);
        binding.tvTotalQuantity.setText("Total: " + totalQuantity);
    }

    private void displaySavedEntries() {
        savedEntriesContainer.removeAllViews();

        if (savedEntries.isEmpty()) {
            View emptyView = getLayoutInflater().inflate(R.layout.item_empty_state, null);
            savedEntriesContainer.addView(emptyView);
            return;
        }

        for (SavedEntry entry : savedEntries) {
            View cardView = getLayoutInflater().inflate(R.layout.item_saved_entry, null);

            TextView tvCategory = cardView.findViewById(R.id.tvSavedCategory);
            TextView tvDate = cardView.findViewById(R.id.tvSavedDate);
            TextView tvTotal = cardView.findViewById(R.id.tvSavedTotal);
            LinearLayout brandsContainer = cardView.findViewById(R.id.savedBrandsContainer);
            Button btnDelete = cardView.findViewById(R.id.btnDeleteEntry);

            tvCategory.setText(entry.category);
            tvDate.setText(entry.date + " " + entry.time);
            tvTotal.setText("Total: " + entry.totalQuantity + " units");

            // Add brands
            for (CompetitonSalesBrandModel brand : entry.brands) {
                View brandView = getLayoutInflater().inflate(R.layout.item_saved_brand, null);
                TextView tvBrandName = brandView.findViewById(R.id.tvSavedBrandName);
                TextView tvBrandQty = brandView.findViewById(R.id.tvSavedBrandQty);
                View colorDot = brandView.findViewById(R.id.colorDot);

                tvBrandName.setText(brand.getName());
                tvBrandQty.setText("×" + brand.getQty());
                int color = android.graphics.Color.parseColor(brand.getColor());
                colorDot.setBackgroundColor(color);

                brandsContainer.addView(brandView);
            }

            btnDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Delete Entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            savedEntries.remove(entry);
                            displaySavedEntries();
                            Toast.makeText(this, "Entry deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });

            savedEntriesContainer.addView(cardView);
        }
    }

    private void generateAndShowJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray brandArray = new JSONArray();

            // Get all brands with quantity > 0 from saved entries
            // Using the latest saved entry
            SavedEntry latestEntry = savedEntries.get(0);
            for (CompetitonSalesBrandModel brand : latestEntry.brands) {

                    JSONObject brandObj = new JSONObject();
                    brandObj.put("brandID", brand.getBrandId());
                    brandObj.put("brandName", brand.getName());
                    brandObj.put("Qty", Integer.parseInt(brand.getQty()));
                    brandArray.put(brandObj);

            }

            jsonObject.put("brandData", brandArray);

            // Show JSON in dialog
            String jsonString = jsonObject.toString(4);

            View dialogView = getLayoutInflater().inflate(R.layout.dialog_json_view, null);
            TextView tvJson = dialogView.findViewById(R.id.tvJsonContent);
            tvJson.setText(jsonString);

            new AlertDialog.Builder(this)
                    .setTitle("Generated JSON")
                    .setView(dialogView)
                    .setPositiveButton("Copy", (dialog, which) -> {
                        android.content.ClipboardManager clipboard =
                                (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        android.content.ClipData clip =
                                android.content.ClipData.newPlainText("JSON", jsonString);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(this, "JSON copied to clipboard", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Close", null)
                    .show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating JSON: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }



    // Inner class for saved entries
    private static class SavedEntry {
        long id;
        String category;
        String date;
        String time;
        List<CompetitonSalesBrandModel> brands;
        int totalQuantity;
    }
}