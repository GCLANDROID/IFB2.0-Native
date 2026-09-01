package io.cordova.ifb.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.CompSalesBrandAdapter;
import io.cordova.ifb.databinding.ActivityNewCompSalesBinding;
import io.cordova.ifb.module.CategoryStatusItem;
import io.cordova.ifb.module.CompetitonSalesBrandModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;
import okhttp3.OkHttpClient;

public class NewCompSalesActivity extends AppCompatActivity {
    ActivityNewCompSalesBinding binding;
    private Spinner spinnerCategory;
    private CompSalesBrandAdapter brandAdapter;
    private LinearLayout brandListContainer, savedDataContainer;
    private LinearLayout savedEntriesContainer;

    private List<CompetitonSalesBrandModel> brandList=new ArrayList<>();
    private String selectedCategory = "";
    private List<SavedEntry> savedEntries = new ArrayList<>();
    private List<CategoryItem> categoryList = new ArrayList<>();
    ArrayList<String>category=new ArrayList<>();
    String selectedCategoryId,selectedCategoryName;
    PrefManager prefManager;
    AlertDialog alerDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_new_comp_sales);
        initViews();
    }

    private void initViews() {
        OkHttpClient okHttpClient =
                AppController.getUnsafeOkHttpClient();

        AndroidNetworking.initialize(
                getApplicationContext(),
                okHttpClient
        );
        prefManager=new PrefManager(NewCompSalesActivity.this);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        brandListContainer = findViewById(R.id.brandListContainer);
        savedDataContainer = findViewById(R.id.savedDataContainer);
        savedEntriesContainer = findViewById(R.id.savedEntriesContainer);


        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("AEMEmployeeID",prefManager.getUserId());
            jsonObject1.put("CategoryID","IFBPC1000011");
            jsonObject1.put("SecurityCode",prefManager.getSecurityCode());
            checkCompSalesFlag(jsonObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SecurityCode",prefManager.getSecurityCode());
            getCategoryList(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }




        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (categoryList == null || categoryList.isEmpty()) {
                    return;
                }

                if (position >= 0 && position < categoryList.size()) {
                    selectedCategoryId = categoryList.get(position).getCategoryId();
                    selectedCategoryName = categoryList.get(position).getCategoryName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setupButtons();
        displaySavedEntries();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    private void getCategoryList(JSONObject jsonObject) {
        Log.e("LOGIN", "login: " + jsonObject.toString());
        final ProgressDialog pd = new ProgressDialog(NewCompSalesActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        AndroidNetworking.post(AppController.APIV2URL + "api/IFBEmployeeCompetorSales/ShowList")
                .addJSONObjectBody(jsonObject)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();

                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        String Response_Code = job1.optString("Response_Code");

                        if (Response_Code.equalsIgnoreCase("101")) {
                            // Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();

                            try {
                                JSONArray responseData  = job1.getJSONArray("Response_Data");
                                categoryList.clear();

                                // Add Select Category option
                                categoryList.add(new CategoryItem("", "Select Category"));

                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject item = responseData.getJSONObject(i);
                                    String categoryId = item.getString("CategoryID");
                                    String categoryName = item.getString("CategoryName");
                                    categoryList.add(new CategoryItem(categoryId, categoryName));

                                }

                                String[] categories = new String[categoryList.size()];
                                for (int i = 0; i < categoryList.size(); i++) {
                                    categories[i] = categoryList.get(i).getCategoryName();
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewCompSalesActivity.this,
                                        android.R.layout.simple_spinner_item, categories);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCategory.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }


                        // boolean _status = job1.getBoolean("status");


                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("LOGIN", "onError: " + error);
                        pd.dismiss();


                    }
                });
    }

    private void setUpBrandList(JSONObject jsonObject) {
        Log.e("LOGIN", "login: " + jsonObject.toString());
        final ProgressDialog pd = new ProgressDialog(NewCompSalesActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        AndroidNetworking.post(AppController.APIV2URL + "api/IFBEmployeeCompetorSales/Show")
                .addJSONObjectBody(jsonObject)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();

                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        String Response_Code = job1.optString("Response_Code");

                        if (Response_Code.equalsIgnoreCase("101")) {
                            // Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();

                            try {
                                JSONArray responseData  = job1.getJSONArray("Response_Data");
                                brandList.clear();

                                // Define brand colors
                                Map<String, String> brandColors = new HashMap<>();
                                brandColors.put("LG", "#A50034");
                                brandColors.put("SAMSUNG", "#1428A0");
                                brandColors.put("WHIRPOOL", "#004B87");
                                brandColors.put("GODREJ", "#E31E24");
                                brandColors.put("PANASONIC", "#003DA5");
                                brandColors.put("VOLTAS", "#ED1C24");
                                brandColors.put("DAIKEN", "#0068B4");
                                brandColors.put("LLOYDS", "#005B9F");
                                brandColors.put("IFB", "#1a237e");
                                brandColors.put("ONIDA", "#E60012");
                                brandColors.put("CARRIER", "#00529B");
                                brandColors.put("BLUE STAR", "#0088CC");
                                brandColors.put("OGENERAL", "#FF6B00");
                                brandColors.put("HAIER", "#00529B");
                                brandColors.put("HITACHI", "#E60012");
                                brandColors.put("MITSUBISHI", "#FF0000");
                                brandColors.put("OTHERS", "#666666");

                                // Default color if brand not found
                                String defaultColor = "#666666";

                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject item = responseData.getJSONObject(i);
                                    String competitorCompanyId = item.getString("CompetitorCompanyID");
                                    String brandName = item.getString("Name");
                                    int quantity = item.getInt("Quantity");

                                    // Get color for brand
                                    String color = brandColors.getOrDefault(brandName.toUpperCase(), defaultColor);

                                    // Create brand object
                                    CompetitonSalesBrandModel brand = new CompetitonSalesBrandModel(
                                            String.valueOf(i + 1),
                                            competitorCompanyId,
                                            brandName,
                                            String.valueOf(quantity),
                                            color
                                    );
                                    brandList.add(brand);
                                }

                                // Update adapter
                                brandAdapter = new CompSalesBrandAdapter(NewCompSalesActivity.this, (brandId, qty) -> {
                                    updateStats();
                                });

                                LinearLayoutManager layoutManager = new LinearLayoutManager(NewCompSalesActivity.this);
                                layoutManager.setStackFromEnd(false);
                                binding.recyclerViewBrands.setLayoutManager(layoutManager);
                                binding.recyclerViewBrands.setAdapter(brandAdapter);
                                binding.recyclerViewBrands.setHasFixedSize(true);
                                binding.recyclerViewBrands.setNestedScrollingEnabled(false);
                                binding.recyclerViewBrands.setFocusable(false);
                                binding.recyclerViewBrands.setFocusableInTouchMode(false);
                                brandAdapter.setBrandList(brandList);
                                brandListContainer.setVisibility(View.VISIBLE);
                                binding.tvSelectedCategory.setText("Category: " + selectedCategoryName);
                                updateStats();

                                // Scroll to brand list
                                binding.mainScrollView.postDelayed(() -> {
                                    binding.mainScrollView.smoothScrollTo(0, brandListContainer.getTop());
                                }, 100);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }


                        // boolean _status = job1.getBoolean("status");


                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("LOGIN", "onError: " + error);
                        pd.dismiss();


                    }
                });
    }


    private void setupButtons() {
        binding.btnShow.setOnClickListener(v -> {
            hideKeyboard();


            // Reset quantities
            if (TextUtils.isEmpty(selectedCategoryId)) {
                Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("SecurityCode",prefManager.getSecurityCode());
                    getCategoryList(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }

            // Show loading


            // Fetch brands from API
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("AEMEmployeeID",prefManager.getUserId());
                jsonObject.put("CategoryID",selectedCategoryId);
                jsonObject.put("SecurityCode",prefManager.getSecurityCode());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setUpBrandList(jsonObject);

        });

        binding.btnSave.setOnClickListener(v -> {
            hideKeyboard();
            if (TextUtils.isEmpty(selectedCategoryId)) {
                Toast.makeText(this, "Please select a category first", Toast.LENGTH_SHORT).show();
                return;
            }
            if (brandList.isEmpty()) {
                Toast.makeText(this, "Please click Show Brands to load brands", Toast.LENGTH_SHORT).show();
                return;
            }
            List<CompetitonSalesBrandModel> allBrandsWithQty = new ArrayList<>();

            for (CompetitonSalesBrandModel brand : brandList) {
                String qtyStr = brand.getQty();
                int qty = 0;

                // If quantity is empty or null, set to 0
                if (qtyStr == null || qtyStr.isEmpty()) {
                    qty = 0;
                } else {
                    try {
                        qty = Integer.parseInt(qtyStr);
                    } catch (NumberFormatException e) {
                        qty = 0; // If invalid number, set to 0
                    }
                }

                // Update brand with proper quantity
                brand.setQty(String.valueOf(qty));
                allBrandsWithQty.add(brand);
            }

            // Show loading and save (send all brands including 0)

            DataSave(allBrandsWithQty);
        });




        binding.btnClear.setOnClickListener(v -> {
            hideKeyboard();
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
            hideKeyboard();
            if (savedEntries.isEmpty()) {
                Toast.makeText(this, "No saved data to generate JSON", Toast.LENGTH_SHORT).show();
                return;
            }
            generateAndShowJson();
        });
    }

    private void DataSave(List<CompetitonSalesBrandModel> allBrands) {
        JSONObject requestBody = new JSONObject();
        try {

            requestBody.put("AEMEmployeeID", prefManager.getUserId());
            requestBody.put("CategoryID", selectedCategoryId);
            requestBody.put("SecurityCode", prefManager.getSecurityCode());
            requestBody.put("SalesPointID", prefManager.getSalesPointID()); // Replace with actual SalesPointID

            JSONArray salesArray = new JSONArray();
            for (CompetitonSalesBrandModel brand : allBrands) {
                int qty = Integer.parseInt(brand.getQty());
                JSONObject salesItem = new JSONObject();
                salesItem.put("CompetitorCompanyID", brand.getBrandId());
                salesItem.put("Quantity", String.valueOf(qty)); // Send 0 for empty quantities
                salesArray.put(salesItem);
            }
            requestBody.put("Sales", salesArray);

            // Log request for debugging
            Log.d("SaveRequest", requestBody.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ProgressDialog pd = new ProgressDialog(NewCompSalesActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        AndroidNetworking.post(AppController.APIV2URL + "api/IFBEmployeeCompetorSales/SaveCompetitorSales")
                .addJSONObjectBody(requestBody)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();



                        try {
                            JSONObject job1 = response;
                            Log.e("response12", "@@@@@@" + job1);
                            String Response_Code = job1.optString("Response_Code");
                            String responseMessage = job1.getString("Response_Message");
                            if (Response_Code.equalsIgnoreCase("101")) {
                                // Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();

                                Toast.makeText(NewCompSalesActivity.this,
                                        "✅ " + responseMessage, Toast.LENGTH_SHORT).show();

                                // Save to local list (only brands with quantity > 0)
                                List<CompetitonSalesBrandModel> savedBrands = new ArrayList<>();
                                for (CompetitonSalesBrandModel brand : allBrands) {
                                    if (Integer.parseInt(brand.getQty()) > 0) {
                                        savedBrands.add(brand);
                                    }
                                }
                                saveToLocalEntries(savedBrands);
                                successAlert(selectedCategoryName + " has been saved successfully!");

                                // Reset form


                            } else {
                                Toast.makeText(NewCompSalesActivity.this,
                                        "❌ Error: " + responseMessage, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        // boolean _status = job1.getBoolean("status");


                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("LOGIN", "onError: " + error);
                        pd.dismiss();


                    }
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


    private void saveToLocalEntries(List<CompetitonSalesBrandModel> filledBrands) {
        if (filledBrands.isEmpty()) {
            return;
        }

        SavedEntry entry = new SavedEntry();
        entry.id = System.currentTimeMillis();
        entry.categoryName = selectedCategoryName;
        entry.date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        entry.time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        entry.brands = new ArrayList<>(filledBrands);
        entry.totalQuantity = 0;
        for (CompetitonSalesBrandModel brand : filledBrands) {
            entry.totalQuantity += Integer.parseInt(brand.getQty());
        }

        savedEntries.add(0, entry);
        displaySavedEntries();
    }

    private void resetForm() {
        for (CompetitonSalesBrandModel brand : brandList) {
            brand.setQty("");
        }
        brandAdapter.setBrandList(brandList);
        binding.brandListContainer.setVisibility(View.GONE);
        updateStats();
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

            tvCategory.setText(entry.categoryName);
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

            SavedEntry latestEntry = savedEntries.get(0);
            for (CompetitonSalesBrandModel brand : latestEntry.brands) {
                if (brand.getQty() != null && !brand.getQty().isEmpty() &&
                        Integer.parseInt(brand.getQty()) > 0) {
                    JSONObject brandObj = new JSONObject();
                    brandObj.put("brandID", brand.getBrandId());
                    brandObj.put("brandName", brand.getName());
                    brandObj.put("Qty", Integer.parseInt(brand.getQty()));
                    brandArray.put(brandObj);
                }
            }

            jsonObject.put("brandData", brandArray);

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

    private void setupKeyboardListeners() {
        // Hide keyboard when touching outside EditText
        findViewById(android.R.id.content).setOnTouchListener((v, event) -> {
            hideKeyboard();
            return false;
        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private static class CategoryItem {
        private String categoryId;
        private String categoryName;

        public CategoryItem(String categoryId, String categoryName) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
        }

        public String getCategoryId() { return categoryId; }
        public String getCategoryName() { return categoryName; }
    }

    // Inner class for saved entries
    private static class SavedEntry {
        long id;
        String categoryName;
        String date;
        String time;
        List<CompetitonSalesBrandModel> brands;
        int totalQuantity;
    }

    private void checkCompSalesFlag(JSONObject jsonObject) {
        Log.e("LOGIN", "login: " + jsonObject.toString());
        final ProgressDialog pd = new ProgressDialog(NewCompSalesActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        AndroidNetworking.post(AppController.APIV2URL + "api/IFBEmployeeCompetorSales/CheckStatus")
                .addJSONObjectBody(jsonObject)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();

                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        String Response_Code = job1.optString("Response_Code");

                        if (Response_Code.equalsIgnoreCase("101")) {
                            // Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();

                            try {
                                JSONObject responseData  = job1.optJSONObject("Response_Data");
                                JSONArray Table=responseData.optJSONArray("Table");


                                // Add Select Category option


                                JSONArray Table1=responseData.optJSONArray("Table1");
                                JSONObject frstObject=Table1.getJSONObject(0);
                                boolean LockFlag=frstObject.getBoolean("LockFlag");
                                if (LockFlag){
                                    binding.llLockScreen.setVisibility(View.VISIBLE);
                                    binding.llMainScreen.setVisibility(View.GONE);
                                }else {
                                    binding.llLockScreen.setVisibility(View.GONE);
                                    binding.llMainScreen.setVisibility(View.VISIBLE);

                                }




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }


                        // boolean _status = job1.getBoolean("status");


                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("LOGIN", "onError: " + error);
                        pd.dismiss();


                    }
                });
    }

    private void successAlert(String showText) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NewCompSalesActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText(showText);

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                resetForm();


            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }
}