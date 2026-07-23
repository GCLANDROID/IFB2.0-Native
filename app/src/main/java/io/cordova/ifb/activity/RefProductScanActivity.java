package io.cordova.ifb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.ACScanAdapter;
import io.cordova.ifb.adapter.REFScanAdapter;
import io.cordova.ifb.databinding.ActivityRefProductScanBinding;
import io.cordova.ifb.module.ACModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;
import okhttp3.OkHttpClient;

public class RefProductScanActivity extends AppCompatActivity {
    ActivityRefProductScanBinding binding;
    ArrayList<ACModel> itemList=new ArrayList<>();
    PrefManager prefManager;
    public static JSONArray selectedArray = new JSONArray();
    int y;
    String year,month;
    String financialYear;
    AlertDialog alerDialog1;
    REFScanAdapter sAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_ref_product_scan);
        initView();
    }

    private void initView(){
        prefManager=new PrefManager(RefProductScanActivity.this);
        OkHttpClient okHttpClient =
                AppController.getUnsafeOkHttpClient();
        AndroidNetworking.initialize(
                getApplicationContext(),
                okHttpClient
        );

        y = Calendar.getInstance().get(Calendar.YEAR);
        year = String.valueOf(y);
        Log.d("year", year);

        int m = Calendar.getInstance().get(Calendar.MONTH) + 1;
        Log.d("month", String.valueOf(m));
        if (m == 1) {
            month = "January";
        } else if (m == 2) {
            month = "February";
        } else if (m == 3) {
            month = "March";
        } else if (m == 4) {
            month = "April";
        } else if (m == 5) {
            month = "May";
        } else if (m == 6) {
            month = "June";
        } else if (m == 7) {
            month = "July";
        } else if (m == 8) {
            month = "August";
        } else if (m == 9) {
            month = "September";
        } else if (m == 10) {
            month = "October";
        } else if (m == 11) {
            month = "November";
        } else if (m == 12) {
            month = "December";
        }
        if(month.equals("January")){
            int futureyear = y - 1;
            financialYear = futureyear+"-"+year;
        }else if (month.equals("February")){
            int futureyear = y - 1;
            financialYear = futureyear+"-"+year;
        }else if (month.equals("March")){
            int futureyear = y - 1;
            financialYear = futureyear+"-"+year;
        }else {
            int futureyear = y + 1;
            financialYear = year+"-"+futureyear;
        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(RefProductScanActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvItem.setLayoutManager(layoutManager);
        setModel("IFBPC1000040");
        binding.tvShowingProduct.setText("Showing Product for REF-FF:");


        binding.btnSubmit.setOnClickListener(v -> {

            // ✅ Check if empty
            if (selectedArray == null || selectedArray.length() == 0) {
                Toast.makeText(this, "Please select at least one item", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject finalObject = new JSONObject();

                finalObject.put("BranchID", prefManager.getBranchId());
                finalObject.put("AEMEmployeeID", prefManager.getUserId());
                finalObject.put("Month", month);
                finalObject.put("FinancialYear", financialYear);
                finalObject.put("SalesPointID", prefManager.getSalesPointID());

                finalObject.put("ResponseData", selectedArray);

                Log.d("FINAL_JSON", finalObject.toString(4));
                postSave(finalObject);

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });

        binding.tvREFFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setModel("IFBPC1000040");
                binding.tvShowingProduct.setText("Showing Product for REF-FF:");
            }
        });

        binding.tvREFDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setModel("IFBPC1000013");
                binding.tvShowingProduct.setText("Showing Product for REF-DC:");
            }
        });

    }


    private void setModel(String categoryId) {
        String surl = AppController.APIV2URL + "api/CommonDDL?ModuleNo=18M&ID="+categoryId+"&ID1=0&ID2=" + prefManager.getBranchId() + "&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("modelinput", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseModel", response);

                        itemList.clear();




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


                                    ACModel itemModule = new ACModel();
                                    itemModule.setId(id);
                                    itemModule.setValue(value);

                                    itemList.add(itemModule);

                                }
                                progressBar.dismiss();

                                sAdpater=new REFScanAdapter(itemList,RefProductScanActivity.this);
                                binding.rvItem.setAdapter(sAdpater);




                            } else {

                                progressBar.dismiss();
                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RefProductScanActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "model");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(RefProductScanActivity.this);
//        requestQueue.add(stringRequest);
        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(RefProductScanActivity.this);

        requestQueue.add(stringRequest);

    }

    void filter(String text){
        ArrayList<ACModel> temp = new ArrayList();
        for(ACModel d: itemList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getValue().toLowerCase().contains(text)||(d.getValue().toUpperCase().contains(text))){
                temp.add(d);
            }
        }
        //update recyclerview
        sAdpater.updateList(temp);
    }

    private void postSave(JSONObject jsonObject) {

        final ProgressDialog pd = new ProgressDialog(RefProductScanActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        String url=AppController.APIV2URL + "api/InsertModelData?SecurityCode="+prefManager.getSecurityCode();

        AndroidNetworking.post(url)
                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", "Bearer " + prefManager.getAccessToken())
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
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
                            boolean responseStatus=job1.optBoolean("Status");
                            if (responseStatus){
                                showProductStatusDialog(RefProductScanActivity.this, job1);
                                selectedArray = new JSONArray();
                            }else {
                                Toast.makeText(RefProductScanActivity.this, "Failed to save data", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(RefProductScanActivity.this, "Error Occured 1", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void successAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RefProductScanActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText("Refrigerator has been added successfully");

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                onBackPressed();



            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }
    public void showProductStatusDialog(Context context, JSONObject response) {
        try {
            boolean status = response.optBoolean("Status", false);

            if (!status) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONArray dataArray = response.optJSONArray("Data");

            if (dataArray == null || dataArray.length() == 0) {
                Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder messageBuilder = new StringBuilder();

            int successCount = 0;
            int failCount = 0;

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject item = dataArray.getJSONObject(i);

                String modelCode = item.optString("ModelName", "N/A");
                int result = item.optInt("Result", 0);
                String message = item.optString("Message", "Unknown status");

                // Simplify backend messages
                if (message.toUpperCase().contains("ALREADY REGISTER")) {
                    message = "Already Registered";
                } else if (message.toUpperCase().contains("SUCCESS")) {
                    message = "Saved Successfully";
                }

                if (result == 1) {
                    successCount++;
                } else {
                    failCount++;
                }

                messageBuilder.append("• ")
                        .append(modelCode)
                        .append(" → ")
                        .append(message)
                        .append("\n\n");
            }

            // Decide title
            String title;
            String bgColor;
            if (successCount > 0 && failCount > 0) {
                title = "Partially Successful";
                bgColor = "#FFFFFF";
            } else if (successCount > 0) {
                title = "Success";
                bgColor = "#FFFFFF";
            } else {
                title = "Failed";
                bgColor = "#FFFFFF";
            }

            // Add summary on top
            String summary = "Success: " + successCount + " | Failed: " + failCount + "\n\n";
            messageBuilder.insert(0, summary);

            // Create TextView for scrollable content
            TextView textView = new TextView(context);
            textView.setText(messageBuilder.toString());
            textView.setTextSize(14f);
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setPadding(40, 40, 40, 40);

            // Enable scrolling
            ScrollView scrollView = new ScrollView(context);
            scrollView.addView(textView);

            // Show dialog
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setView(scrollView)
                    .setPositiveButton("OK", null)
                    .create();

            dialog.show();

            // 🔹 Set background color AFTER show()
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(bgColor)));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error parsing response", Toast.LENGTH_SHORT).show();
        }
    }
}