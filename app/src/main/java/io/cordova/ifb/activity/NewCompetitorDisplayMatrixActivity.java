package io.cordova.ifb.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.CategoryAdapter;

import io.cordova.ifb.databinding.ActivityNewCompetitorDisplayMatrixBinding;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;
import okhttp3.OkHttpClient;


public class NewCompetitorDisplayMatrixActivity extends AppCompatActivity {
    ActivityNewCompetitorDisplayMatrixBinding binding;
    RecyclerView recyclerCategory;
    CategoryAdapter adapter;
    List<Category> categoryList;
    Button btnSave;
    ArrayList<SpinnerItemModule> moduleCategory = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    PrefManager prefManager;
    String categoryId="";
    String categoryname="";
    int y;
    String year, month, financialYear;
    String monthname;
    int totalcount;
    AlertDialog alerDialog1;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewCompetitorDisplayMatrixBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        OkHttpClient okHttpClient =
                AppController.getUnsafeOkHttpClient();

        AndroidNetworking.initialize(
                getApplicationContext(),
                okHttpClient
        );

        prefManager=new PrefManager(NewCompetitorDisplayMatrixActivity.this);


        y = Calendar.getInstance().get(Calendar.YEAR);
        year = String.valueOf(y);
        Log.d("year", year);

        int m = Calendar.getInstance().get(Calendar.MONTH) + 1;
        Log.d("month", String.valueOf(m));
        if (m == 1) {
            monthname = "January";
        } else if (m == 2) {
            monthname = "February";
        } else if (m == 3) {
            monthname = "March";
        } else if (m == 4) {
            monthname = "April";
        } else if (m == 5) {
            monthname = "May";
        } else if (m == 6) {
            monthname = "June";
        } else if (m == 7) {
            monthname = "July";
        } else if (m == 8) {
            monthname = "August";
        } else if (m == 9) {
            monthname = "September";
        } else if (m == 10) {
            monthname = "October";
        } else if (m == 11) {
            monthname = "November";
        } else if (m == 12) {
            monthname = "December";
        }

        if (monthname.equals("January")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else if (monthname.equals("February")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else if (monthname.equals("March")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else {
            int futureyear = y + 1;
            financialYear = year + "-" + futureyear;
        }
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //initView();
        btnSave = findViewById(R.id.btnSave);
        Glide.with(this)
                .asGif()
                .load(R.drawable.swipe) // your GIF in drawable
                .into(binding.imgScrollHint);
        binding.llScroller.setVisibility(View.GONE);

        //binding.imgScrollHint.postDelayed(() -> binding.imgScrollHint.setVisibility(View.GONE), 4000);



        recyclerCategory = findViewById(R.id.rvRecyclerView);

        // Layout Manager
        recyclerCategory.setLayoutManager(new LinearLayoutManager(this));

        // Data
        categoryList = new ArrayList<>();
        setCategory();

        Log.e("list", "onCreate: "+categoryList.size());
        // Adapter

        recyclerCategory.setAdapter(adapter);
        btnSave.setVisibility(View.GONE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                JSONObject finalObj = new JSONObject();

                try {

                    finalObj.put("Category", categoryname);
                    finalObj.put("EmployeeID", prefManager.getUserId());
                    finalObj.put("CategoryID", categoryId);
                    finalObj.put("FinancialYear", financialYear);
                    finalObj.put("Month", monthname);
                    finalObj.put("SalesPartyCode", prefManager.getSalesPartyCode());
                    finalObj.put("SecurityCode", prefManager.getSecurityCode());

                    JSONArray responseArray = new JSONArray();

                    int grandTotal = 0; // ✅ total

                    for (Category category : categoryList) {

                        JSONObject catObj = new JSONObject();
                        catObj.put("Category_Segment", category.categoryName);

                        JSONArray modelDetailsArray = new JSONArray();

                        for (BrandRow brand : category.brands) {

                            JSONObject brandObj = new JSONObject();
                            brandObj.put("CompanyName", brand.brandName);
                            brandObj.put("CompetitorCompanyID", brand.competitorCompanyId);

                            JSONArray capacityArray = new JSONArray();

                            for (CapacityItem item : brand.capacityList) {

                                JSONObject capObj = new JSONObject();

                                capObj.put("Model_ID", item.modelId); // ✅ INCLUDED
                                capObj.put("Value", item.value);
                                capObj.put("QTY", item.qty);

                                grandTotal += item.qty; // ✅ counting

                                capacityArray.put(capObj);
                            }

                            brandObj.put("Capacity", capacityArray);
                            modelDetailsArray.put(brandObj);
                        }

                        catObj.put("Model_Details", modelDetailsArray);
                        responseArray.put(catObj);
                    }

                    finalObj.put("ResponseData", responseArray);

                    // ✅ Total added


                    Log.e("FINAL_JSON", finalObj.toString());
                    Log.e("TOTAL_COUNT", String.valueOf(grandTotal));
                    totalcount=grandTotal;
                    postSave(finalObj);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = "";
                if (position > 0) {
                    categoryId = moduleCategory.get(position).getItemId();
                    categoryname=moduleCategory.get(position).getItem();
                    loadData();




                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryId.isEmpty()) {
                    Toast.makeText(NewCompetitorDisplayMatrixActivity.this, "Please select a category", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
        binding.imgBack.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.imgHome.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerCategory.setClipToPadding(false);
        recyclerCategory.setPadding(0, 0, 0, dpToPx(NewCompetitorDisplayMatrixActivity.this,100));
       /* recyclerCategory.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {

            if (bottom < oldBottom) {
                recyclerCategory.post(() -> recyclerCategory.smoothScrollToPosition(
                        categoryList.size() - 1
                ));
            }
        });*/

        getOnOFF();
    }

    private void loadData() {

        setProductStatus();

    }




    public class Category {
        public String categoryName; // Solo, Grill, etc.
        public List<BrandRow> brands;
    }

    public class BrandRow {
        public String brandName;
        public String competitorCompanyId;
        public List<CapacityItem> capacityList;
    }

    public class CapacityItem {
        public String modelId;
        public String value;
        public int qty;
    }
    private void setProductStatus() {
        Log.d("hitr", "1");

        String surl = AppController.APIV2URL + "api/get_CompetitorDisplayMatrixStatus?AEMEmployeeID="+prefManager.getUserId()+"&CategoryID="+categoryId+"&CategoryName=AIR&FinancialYear="+financialYear+"&Month="+monthname+"&SecurityCode="+prefManager.getSecurityCode()+"&SalesPartyCode="+prefManager.getSalesPartyCode();
        Log.d("ctegoryinput", surl);
        ProgressDialog pd=new ProgressDialog(NewCompetitorDisplayMatrixActivity.this);
        pd.setMessage("Loading");
        pd.show();
        pd.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        pd.dismiss();
                        binding.llScroller.setVisibility(View.VISIBLE);

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("DisplayStatus");


                            if (responseStatus){
                                setProduct(responseStatus);
                            }else {
                                displayMatrixAlert();

                            }
//                            if (responseStatus){
//                                btnSave.setText("Save");
//                                btnSave.setEnabled(true);
//                            }else {
//                                btnSave.setText(categoryname+" data is already saved");
//                                btnSave.setEnabled(false);
//                            }










                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NewCompetitorDisplayMatrixActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showAlert();

                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(NewCompetitorDisplayMatrixActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(NewCompetitorDisplayMatrixActivity.this);

        requestQueue.add(stringRequest);
    }

    private void setProduct(boolean responseStatus) {
        Log.d("hitr", "1");

        String surl = AppController.APIV2URL + "api/get_CompetitorDisplayMatrix?AEMEmployeeID="+prefManager.getUserId()+"&CategoryID="+categoryId+"&CategoryName=AIR&FinancialYear="+financialYear+"&Month="+monthname+"&SecurityCode="+prefManager.getSecurityCode()+"&SalesPartyCode="+prefManager.getSalesPartyCode();
        Log.d("ctegoryinput", surl);
        ProgressDialog pd=new ProgressDialog(NewCompetitorDisplayMatrixActivity.this);
        pd.setMessage("Loading");
        pd.show();
        pd.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        btnSave.setVisibility(View.VISIBLE);
                        pd.dismiss();
                        binding.llScroller.setVisibility(View.VISIBLE);

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatuss = job1.optBoolean("responseStatus");

                            JSONArray responseData = job1.optJSONArray("ResponseData");
                            if (responseData.length()>0){
                                parseApiResponse(job1,responseStatus);
                                binding.rvRecyclerView.setVisibility(View.VISIBLE);

                            }else {
                                binding.llScroller.setVisibility(View.GONE);
                                categoryList.clear();
                                binding.rvRecyclerView.setVisibility(View.GONE);
                                showAlert();
                            }









                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NewCompetitorDisplayMatrixActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showAlert();

                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(NewCompetitorDisplayMatrixActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(NewCompetitorDisplayMatrixActivity.this);
        requestQueue.add(stringRequest);


    }


    private void parseApiResponse(JSONObject response,boolean responseStatus) {
        binding.llScroller.postDelayed(() -> binding.llScroller.setVisibility(View.GONE), 4000);
        try {
            categoryList = new ArrayList<>();

            JSONArray responseData = response.optJSONArray("ResponseData");
            if (responseData == null) return;

            for (int i = 0; i < responseData.length(); i++) {

                JSONObject catObj = responseData.optJSONObject(i);

                Category category = new Category();
                category.categoryName = catObj.optString("Category_Segment");

                List<BrandRow> brandList = new ArrayList<>();

                JSONArray modelDetails = catObj.optJSONArray("Model_Details");

                for (int j = 0; j < modelDetails.length(); j++) {

                    JSONObject brandObj = modelDetails.optJSONObject(j);

                    BrandRow brand = new BrandRow();
                    brand.brandName = brandObj.optString("CompanyName");
                    brand.competitorCompanyId = brandObj.optString("CompetitorCompanyID");

                    List<CapacityItem> capList = new ArrayList<>();

                    JSONArray capacityArray = brandObj.optJSONArray("Capacity");

                    for (int k = 0; k < capacityArray.length(); k++) {

                        JSONObject capObj = capacityArray.optJSONObject(k);

                        CapacityItem item = new CapacityItem();
                        item.modelId = capObj.optString("Model_ID"); // ✅ KEY
                        item.value = capObj.optString("Value");
                        item.qty = capObj.optInt("QTY");

                        capList.add(item);
                    }

                    brand.capacityList = capList;
                    brandList.add(brand);
                }



                category.brands = brandList;
                categoryList.add(category);
            }


            LinearLayout headerContainer = findViewById(R.id.headerContainer);
            headerContainer.removeAllViews();

// 👉 Extract capacities from first category → first brand
//            if (!categoryList.isEmpty() && !categoryList.get(0).brands.isEmpty()) {
//
//                List<CapacityItem> capList = categoryList.get(0).brands.get(0).capacityList;
//
//                for (CapacityItem item : capList) {
//
//                    TextView tv = new TextView(this);
//
//                    LinearLayout.LayoutParams params =
//                            new LinearLayout.LayoutParams(70, 50);
//                    params.setMargins(4, 0, 4, 0);
//
//                    tv.setLayoutParams(params);
//                    tv.setText(item.value); // ✅ capacity value
//                    tv.setGravity(Gravity.CENTER);
//                    tv.setTextSize(10);
//                    tv.setBackgroundColor(Color.LTGRAY);
//
//                    headerContainer.addView(tv);
//                }
//            }

            adapter = new CategoryAdapter(categoryList,responseStatus);
            recyclerCategory.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String normalizeCapacity(String value) {
        if (value == null) return "";

        return value
                .trim()
                .toUpperCase()
                .replace("TO", "To")   // unify format
                .replace(" ", "");
    }


    private void setCategory() {
        Log.d("hitr", "1");
        prefManager=new PrefManager(NewCompetitorDisplayMatrixActivity.this);

        String surl = AppController.APIV2URL + "api/CommonDDL?ModuleNo=4&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
       ProgressDialog progressDialog=new ProgressDialog(NewCompetitorDisplayMatrixActivity.this);
       progressDialog.setMessage("Loading");
       progressDialog.show();
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                         progressDialog.dismiss();
                        category.clear();
                        moduleCategory.clear();
                        category.add("Please select Category");
                        moduleCategory.add(new SpinnerItemModule("0", "0"));

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String value = obj.optString("value");
                                    String id = obj.optString("id");
                                    category.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleCategory.add(itemModule);

                                }




                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (NewCompetitorDisplayMatrixActivity.this, android.R.layout.simple_spinner_item,
                                                category); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spCategory.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NewCompetitorDisplayMatrixActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(NewCompetitorDisplayMatrixActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(NewCompetitorDisplayMatrixActivity.this);

        requestQueue.add(stringRequest);

    }

    private void showAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Data found");
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        alertDialogBuilder.show();


    }


    private void postSave(JSONObject jsonObject) {

        final ProgressDialog pd = new ProgressDialog(NewCompetitorDisplayMatrixActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        String url=AppController.APIV2URL + "api/CompetitorDisplayMatrix/save?SecurityCode="+prefManager.getSecurityCode();

        AndroidNetworking.post(url)
                .addJSONObjectBody(jsonObject)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + prefManager.getAccessToken())
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        pd.show();

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        try {
                            JSONObject job1 = response;
                            boolean responseStatus=job1.optBoolean("responseStatus");
                            if (responseStatus){
                                successAlert();
                            }else {
                                Toast.makeText(NewCompetitorDisplayMatrixActivity.this, "Failed to save data", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {

                        pd.dismiss();
                        Toast.makeText(NewCompetitorDisplayMatrixActivity.this, "Error Occured 1", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void successAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NewCompetitorDisplayMatrixActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText(totalcount+" "+categoryname+" units(s) has been added successfully");

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                recyclerCategory.setVisibility(View.GONE);
                categoryList=new ArrayList<>();
                binding.spCategory.setSelection(0);
                btnSave.setVisibility(View.GONE);



            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }
    private int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }


    private void displayMatrixAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NewCompetitorDisplayMatrixActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_compsale, null);
        dialogBuilder.setView(dialogView);
        Button btnNow = (Button) dialogView.findViewById(R.id.btnNow);
        TextView tvResponse = (TextView) dialogView.findViewById(R.id.tvResponse);
        tvResponse.setText(categoryname + "'s Display Matrix already updated. Do you want to update ?");
        btnNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                setProduct(true);

            }
        });

        Button btnLate = (Button) dialogView.findViewById(R.id.btnLate);
        btnLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog.show();
    }


    private void getOnOFF() {
        Log.d("hitr", "1");

        String surl = AppController.APIV2URL + "api/Get_CompetitorDispalyMatrixOnOff?SecurityCode=IFB";
        Log.d("onoff", surl);
        ProgressDialog pd=new ProgressDialog(NewCompetitorDisplayMatrixActivity.this);
        pd.setMessage("Loading");
        pd.show();
        pd.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        pd.dismiss();


                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("DisplayStatus");
                            JSONArray responseData= job1.optJSONArray("responseData");
                            JSONObject frstObj=responseData.getJSONObject(0);
                            boolean Result=frstObj.optBoolean("Result");
                            if (Result){
                                binding.llClosed.setVisibility(View.GONE);
                                binding.llMain.setVisibility(View.VISIBLE);
                            }else {
                                binding.llClosed.setVisibility(View.VISIBLE);
                                binding.llMain.setVisibility(View.GONE);
                            }

//                            if (responseStatus){
//                                btnSave.setText("Save");
//                                btnSave.setEnabled(true);
//                            }else {
//                                btnSave.setText(categoryname+" data is already saved");
//                                btnSave.setEnabled(false);
//                            }










                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NewCompetitorDisplayMatrixActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showAlert();

                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(NewCompetitorDisplayMatrixActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(NewCompetitorDisplayMatrixActivity.this);

        requestQueue.add(stringRequest);
    }

}
