package io.cordova.ifb.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.SalesClaimActivity;
import io.cordova.ifb.activity.SalesManage2Activity;
import io.cordova.ifb.module.CategoryItem;
import io.cordova.ifb.module.ClaimItem;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class ClaimAdapter extends RecyclerView.Adapter<ClaimAdapter.ClaimViewHolder> {

    private final Context context;
    private final List<ClaimItem> claimList;

    private static final String TAG = "CLAIM_SAVE_JSON";


    private static final String CATEGORY_API_URL =
            AppController.APIV2URL+"api/CommonDDL?ModuleNo=4&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=IFB";

    private final RequestQueue requestQueue;
    PrefManager preference;

    public ClaimAdapter(
            Context context,
            List<ClaimItem> claimList) {

        this.context = context;
        this.claimList = claimList;
        requestQueue = AppController.getUnsafeOkHttpQueue(context);

        preference = new PrefManager(context);
    }

    @NonNull
    @Override
    public ClaimViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_claim, parent, false);

        return new ClaimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ClaimViewHolder holder,
            int position) {

        ClaimItem item = claimList.get(position);

        holder.txtProduct.setText(item.product);
        holder.txtClaimID.setText("Claim ID: " + item.claimID);
        holder.txtCustomer.setText("Customer: " + item.customer);
        holder.txtMobile.setText("Mobile: " + item.mobileNo);

        holder.txtSaleDate.setText(
                "Sale Date: " + formatDate(item.saleDate));

        NumberFormat currency =
                NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

        holder.txtInvoiceValue.setText(
                "Invoice Value: " + item.invoiceValue);

        holder.txtDeliveryDate.setText(
                "Delivery Date: " + formatDate(item.deliveryDate));

        holder.txtDeliveryBy.setText(
                "Delivery By: " + item.deliveryBy);

        boolean isModelMissing = item.isModelCodeMissing == 1;

        holder.txtStatus.setVisibility(
                isModelMissing ? View.VISIBLE : View.GONE);

        holder.modelSection.setVisibility(
                isModelMissing ? View.VISIBLE : View.GONE);

        if (!isModelMissing) {
            return;
        }

        updateModelUI(holder, item);

        holder.btnChooseModel.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();

            if (currentPosition != RecyclerView.NO_POSITION) {
                showCategoryDialog(
                        claimList.get(currentPosition),
                        currentPosition);
            }
        });

        holder.btnSave.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();

            if (currentPosition == RecyclerView.NO_POSITION) {
                return;
            }

            ClaimItem currentItem = claimList.get(currentPosition);

            if (currentItem.selectedModelCode == null
                    || currentItem.selectedModelCode.trim().isEmpty()) {

                Toast.makeText(
                        context,
                        "Please choose a model first",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            JSONObject jsonObject = createSaveJson(currentItem);

            if (jsonObject != null) {
                Log.d(TAG, jsonObject.toString());

                saveClaimData(jsonObject, currentItem);
            }
        });
    }


    private void saveClaimData(JSONObject jsonObject, ClaimItem item) {

        String saveApiUrl = AppController.APIV2URL
                + "api/IFBSalesPIClaim/SaveList";

        AlertDialog loadingDialog = new AlertDialog.Builder(context)
                .setTitle("Saving Claim")
                .setMessage("Please wait...")
                .setCancelable(false)
                .create();

        loadingDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                saveApiUrl,
                jsonObject,

                response -> {
                    if (loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }

                    Log.d("CLAIM_SAVE_RESPONSE", response.toString());

                    String responseCode =
                            response.optString("Response_Code", "");

                    if ("101".equals(responseCode)) {

                        Toast.makeText(
                                context,
                                "Claim saved successfully",
                                Toast.LENGTH_SHORT
                        ).show();

                        // Refresh claim data after successful save.

                            JSONObject jsonObject1=new JSONObject();

                            try {
                                jsonObject1.put("AEMEmployeeID", preference.getUserId());
                                ((SalesClaimActivity) context).loadClaimData(jsonObject1);
                            } catch (JSONException e) {
                                Log.e("CLAIM_SAVE_API", "JSON creation failed", e);
                            }



                    } else {
                        String message = response.optString(
                                "Response_Message",
                                response.optString(
                                        "ResponseMessage",
                                        "Unable to save claim"
                                )
                        );

                        Toast.makeText(
                                context,
                                message,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                },

                error -> {
                    if (loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }

                    Log.e("CLAIM_SAVE_API", "Save request failed", error);

                    String errorMessage = "Failed to save claim. Please try again.";

                    if (error.networkResponse != null) {
                        errorMessage += " HTTP "
                                + error.networkResponse.statusCode;
                    }

                    Toast.makeText(
                            context,
                            errorMessage,
                            Toast.LENGTH_LONG
                    ).show();
                }
        ) {
            @Override
            public java.util.Map<String, String> getHeaders()
                    throws com.android.volley.AuthFailureError {

                java.util.Map<String, String> headers =
                        new java.util.HashMap<>();

                String token = preference.getAccessToken();

                headers.put("Authorization", "Bearer " + token);
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");

                return headers;
            }
        };

        requestQueue.add(request);
    }

    private void updateModelUI(
            ClaimViewHolder holder,
            ClaimItem item) {

        if (item.selectedModelCode == null
                || item.selectedModelCode.isEmpty()) {

            holder.txtSelectedModel.setText("No model selected");
            holder.btnSave.setVisibility(View.GONE);

        } else {

            holder.txtSelectedModel.setText(
                    "Category: " + item.selectedCategory
                            + "\nModel: " + item.selectedModel
                            + "\nModel Code: " + item.selectedModelCode);

            holder.btnSave.setVisibility(View.VISIBLE);
        }

        holder.btnChooseModel.setVisibility(View.VISIBLE);
    }

    private void showCategoryDialog(ClaimItem item, int position) {

        AlertDialog loadingDialog = new AlertDialog.Builder(context)
                .setTitle("Loading Categories")
                .setMessage("Please wait...")
                .setCancelable(false)
                .create();

        loadingDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                CATEGORY_API_URL,
                null,

                response -> {
                    if (!loadingDialog.isShowing()) {
                        return;
                    }

                    loadingDialog.dismiss();

                    try {
                        boolean success =
                                response.optBoolean("responseStatus", false);

                        if (!success) {
                            String message = response.optString(
                                    "responseText",
                                    "Unable to load categories");

                            Toast.makeText(
                                    context,
                                    message,
                                    Toast.LENGTH_LONG
                            ).show();

                            return;
                        }

                        JSONArray responseData =
                                response.optJSONArray("responseData");

                        if (responseData == null ||
                                responseData.length() == 0) {

                            Toast.makeText(
                                    context,
                                    "No categories available",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        List<CategoryItem> categories =
                                new ArrayList<>();

                        for (int i = 0; i < responseData.length(); i++) {

                            JSONObject object =
                                    responseData.getJSONObject(i);

                            String id = object.optString("id", "");
                            String value = object.optString("value", "");

                            if (!id.isEmpty() && !value.isEmpty()) {
                                categories.add(
                                        new CategoryItem(id, value));
                            }
                        }

                        if (categories.isEmpty()) {
                            Toast.makeText(
                                    context,
                                    "No valid categories found",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        showCategoryOptions(item, position, categories);

                    } catch (Exception e) {
                        Log.e("CATEGORY_API", "JSON parsing error", e);

                        Toast.makeText(
                                context,
                                "Unable to process categories",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                },

                error -> {
                    if (loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }

                    Log.e("CATEGORY_API", "Request failed", error);

                    Toast.makeText(
                            context,
                            "Failed to load categories. Please try again.",
                            Toast.LENGTH_LONG
                    ).show();
                }) {
            @Override
            public java.util.Map<String, String> getHeaders()
                    throws com.android.volley.AuthFailureError {

                java.util.Map<String, String> headers =
                        new java.util.HashMap<>();

                String token = preference.getAccessToken(); // Adjust to your Preference class method

                headers.put("Authorization", "Bearer " + token);
                headers.put("Accept", "application/json");

                return headers;
            }
        };







        requestQueue.add(request);
    }


    private void showCategoryOptions(
            ClaimItem item,
            int position,
            List<CategoryItem> categories) {

        String[] categoryNames = new String[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            categoryNames[i] = categories.get(i).getValue();
        }

        new AlertDialog.Builder(context)
                .setTitle("Choose Category")
                .setItems(categoryNames, (dialog, which) -> {

                    int currentPosition = position;

                    if (currentPosition < 0 ||
                            currentPosition >= claimList.size()) {
                        return;
                    }

                    ClaimItem currentItem =
                            claimList.get(currentPosition);

                    CategoryItem selectedCategory =
                            categories.get(which);

                    currentItem.selectedCategoryId =
                            selectedCategory.getId();

                    currentItem.selectedCategory =
                            selectedCategory.getValue();

                    // Clear the previously selected model.
                    currentItem.selectedModel = "";
                    currentItem.selectedModelCode = "";

                    notifyItemChanged(currentPosition);

                    // Next step: fetch models using selectedCategoryId.
                    showModelDialog(currentItem, currentPosition);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showModelDialog(ClaimItem item, int position) {

        String modelApiUrl =
                "https://ifb.geniushrtech.com/IFBApi/api/CommonDDL"
                        + "?ModuleNo=18M"
                        + "&ID=" + android.net.Uri.encode(item.selectedCategoryId)
                        + "&ID1=0"
                        + "&ID2=0"
                        + "&ID3=0"
                        + "&SecurityCode=IFB";

        AlertDialog loadingDialog = new AlertDialog.Builder(context)
                .setTitle("Loading Models")
                .setMessage("Please wait...")
                .setCancelable(false)
                .create();

        loadingDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                modelApiUrl,
                null,

                response -> {

                    if (loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }

                    try {
                        boolean success =
                                response.optBoolean("responseStatus", false);

                        if (!success) {
                            Toast.makeText(
                                    context,
                                    response.optString(
                                            "responseText",
                                            "Unable to load models"),
                                    Toast.LENGTH_LONG
                            ).show();
                            return;
                        }

                        JSONArray responseData =
                                response.optJSONArray("responseData");

                        if (responseData == null ||
                                responseData.length() == 0) {

                            Toast.makeText(
                                    context,
                                    "No models available",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        List<CategoryItem> models = new ArrayList<>();

                        for (int i = 0; i < responseData.length(); i++) {

                            JSONObject modelObject =
                                    responseData.getJSONObject(i);

                            String modelId =
                                    modelObject.optString("id", "");

                            String modelName =
                                    modelObject.optString("value", "");

                            String mrp =
                                    modelObject.optString("MRP", "0");

                            if (!modelId.isEmpty() &&
                                    !modelName.isEmpty()) {

                                // Display model name and MRP in the dialog.
                                String displayName = modelName
                                        + " | MRP: ₹" + mrp;

                                models.add(
                                        new CategoryItem(
                                                modelId,
                                                displayName
                                        )
                                );
                            }
                        }

                        if (models.isEmpty()) {
                            Toast.makeText(
                                    context,
                                    "No valid models found",
                                    Toast.LENGTH_SHORT
                            ).show();
                            return;
                        }

                        showModelOptions(item, position, models);



                    } catch (Exception e) {

                        Log.e("MODEL_API", "JSON parsing error", e);

                        Toast.makeText(
                                context,
                                "Unable to process models",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                },

                error -> {

                    if (loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }

                    Log.e("MODEL_API", "Request failed", error);

                    Toast.makeText(
                            context,
                            "Failed to load models. Please try again.",
                            Toast.LENGTH_LONG
                    ).show();
                }
        ) {
            @Override
            public java.util.Map<String, String> getHeaders()
                    throws com.android.volley.AuthFailureError {

                java.util.Map<String, String> headers =
                        new java.util.HashMap<>();

                // Use your existing Preference class.
                String token = preference.getAccessToken();

                headers.put("Authorization", "Bearer " + token);
                headers.put("Accept", "application/json");

                return headers;
            }
        };

        requestQueue.add(request);
    }


    private void showModelOptions(
            ClaimItem item,
            int position,
            List<CategoryItem> models
    ) {
        // Filtered results currently displayed in the list.
        final List<CategoryItem> filteredModels =
                new ArrayList<>(models);

        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(dp(16), dp(8), dp(16), dp(8));

        // Search input
        EditText searchInput = new EditText(context);
        searchInput.setSingleLine(true);
        searchInput.setHint("Enter model name or code");
        searchInput.setPadding(
                dp(12), dp(8), dp(12), dp(8)
        );

        // Search button
        Button searchButton = new Button(context);
        searchButton.setText("Search");

        // Display list
        android.widget.ListView modelListView =
                new android.widget.ListView(context);

        modelListView.setChoiceMode(
                android.widget.ListView.CHOICE_MODE_SINGLE
        );

        // Build display names for the adapter.
        List<String> displayNames = new ArrayList<>();

        for (CategoryItem model : filteredModels) {
            displayNames.add(model.getValue());
        }

        android.widget.ArrayAdapter<String> modelAdapter =
                new android.widget.ArrayAdapter<>(
                        context,
                        android.R.layout.simple_list_item_1,
                        displayNames
                );

        modelListView.setAdapter(modelAdapter);

        mainLayout.addView(
                searchInput,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        mainLayout.addView(
                searchButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        LinearLayout.LayoutParams listParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dp(300)
                );

        mainLayout.addView(modelListView, listParams);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Choose Model")
                .setView(mainLayout)
                .setNegativeButton("Back", (d, which) -> {
                    showCategoryDialog(item, position);
                })
                .setCancelable(true)
                .create();

        // Search button click
        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText()
                    .toString()
                    .trim()
                    .toLowerCase(Locale.ROOT);

            filteredModels.clear();
            displayNames.clear();

            for (CategoryItem model : models) {
                String modelName = model.getValue() == null
                        ? ""
                        : model.getValue().toLowerCase(Locale.ROOT);

                String modelId = model.getId() == null
                        ? ""
                        : model.getId().toLowerCase(Locale.ROOT);

                // Model value includes the name and MRP.
                if (modelName.contains(query)
                        || modelId.contains(query)) {

                    filteredModels.add(model);
                    displayNames.add(model.getValue());
                }
            }

            modelAdapter.notifyDataSetChanged();

            if (filteredModels.isEmpty()) {
                Toast.makeText(
                        context,
                        "No matching models found",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // Select a model from the filtered list.
        modelListView.setOnItemClickListener(
                (parent, view, index, id) -> {

                    if (index < 0 || index >= filteredModels.size()) {
                        return;
                    }

                    if (position < 0 || position >= claimList.size()) {
                        return;
                    }

                    ClaimItem currentItem = claimList.get(position);

                    // Avoid applying the selection to a different claim.
                    if (currentItem != item) {
                        return;
                    }

                    CategoryItem selectedModel =
                            filteredModels.get(index);

                    currentItem.selectedModel =
                            selectedModel.getValue();

                    currentItem.selectedModelCode =
                            selectedModel.getId();

                    notifyItemChanged(position);
                    dialog.dismiss();
                }
        );

        dialog.show();
    }

    private int dp(float value) {
        return (int) (
                value * context.getResources()
                        .getDisplayMetrics().density + 0.5f
        );
    }


    // Creates the JSON object only. No API call.
    private JSONObject createSaveJson(ClaimItem item) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(
                    "AEMEmployeeID",
                    "AEMP000480009657");

            jsonObject.put(
                    "ClaimID",
                    item.claimID);

            jsonObject.put(
                    "ModelCode",
                    item.selectedModelCode);

            return jsonObject;

        } catch (Exception e) {
            Log.e(TAG, "Error creating JSON", e);
            return null;
        }
    }

    private String formatDate(String value) {

        if (value == null || value.isEmpty()) {
            return "-";
        }

        try {
            SimpleDateFormat inputFormat =
                    new SimpleDateFormat(
                            "yyyy-MM-dd'T'HH:mm:ss",
                            Locale.getDefault());

            SimpleDateFormat outputFormat =
                    new SimpleDateFormat(
                            "dd MMM yyyy, hh:mm a",
                            Locale.getDefault());

            Date date = inputFormat.parse(value);

            return date != null
                    ? outputFormat.format(date)
                    : value;

        } catch (Exception e) {
            return value;
        }
    }

    @Override
    public int getItemCount() {
        return claimList.size();
    }

    static class ClaimViewHolder extends RecyclerView.ViewHolder {

        TextView txtProduct;
        TextView txtStatus;
        TextView txtClaimID;
        TextView txtCustomer;
        TextView txtMobile;
        TextView txtSaleDate;
        TextView txtInvoiceValue;
        TextView txtDeliveryDate;
        TextView txtDeliveryBy;
        TextView txtSelectedModel;

        LinearLayout modelSection;

        Button btnChooseModel;
        Button btnSave;

        ClaimViewHolder(@NonNull View itemView) {
            super(itemView);

            txtProduct = itemView.findViewById(R.id.txtProduct);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtClaimID = itemView.findViewById(R.id.txtClaimID);
            txtCustomer = itemView.findViewById(R.id.txtCustomer);
            txtMobile = itemView.findViewById(R.id.txtMobile);
            txtSaleDate = itemView.findViewById(R.id.txtSaleDate);
            txtInvoiceValue = itemView.findViewById(R.id.txtInvoiceValue);
            txtDeliveryDate = itemView.findViewById(R.id.txtDeliveryDate);
            txtDeliveryBy = itemView.findViewById(R.id.txtDeliveryBy);

            modelSection = itemView.findViewById(R.id.modelSection);
            txtSelectedModel =
                    itemView.findViewById(R.id.txtSelectedModel);

            btnChooseModel =
                    itemView.findViewById(R.id.btnChooseModel);

            btnSave = itemView.findViewById(R.id.btnSave);
        }
    }
}
