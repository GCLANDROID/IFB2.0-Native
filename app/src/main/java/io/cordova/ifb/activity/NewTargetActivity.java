package io.cordova.ifb.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.NewTargetAdapter;
import io.cordova.ifb.databinding.ActivityNewTargateBinding;
import io.cordova.ifb.module.NewTargetModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class NewTargetActivity extends AppCompatActivity {
    private static final String TAG = "NewTargetActivity";
    ActivityNewTargateBinding binding;
    ArrayList<NewTargetModel> targetList = new ArrayList<>();
    PrefManager prefManager;
    JSONArray ModelWiseDataArray = new JSONArray();
    JSONArray MarginWiseDataArray = new JSONArray();

    int y;
    String year,financialYear,month;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewTargateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        btnClick();
    }



    private void initView() {
        prefManager = new PrefManager(this);
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
        Log.d("financialYear",financialYear);

        binding.rvTarget.setLayoutManager(new LinearLayoutManager(this));

        getTargetDate();
    }

    private void getTargetDate() {
        binding.llLoading.setVisibility(View.VISIBLE);
        binding.llNoDataFound.setVisibility(View.GONE);
        binding.llDataLayout.setVisibility(View.GONE);
        String surl =  AppController.APIURL+"api/UserTargetV1?FinancialYear="+financialYear+"&Month="+month+"&AEMEmployeeID="+prefManager.getUserId()+"&LoginID="+prefManager.getMasterId()+"&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputLogin", surl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "USER_TARGET: "+response);
                        try {
                            JSONObject job1 = new JSONObject(response);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus){
                                String responseDate = job1.optString("responseData");
                                Log.e(TAG, "responseDate: "+responseDate);
                                JSONObject responseDateObj = new JSONObject(responseDate);
                                ModelWiseDataArray = responseDateObj.optJSONArray("ModelWiseData");
                                MarginWiseDataArray = responseDateObj.optJSONArray("MarginWiseData");
                                if (ModelWiseDataArray.length() > 0){
                                    for (int i = 0; i < ModelWiseDataArray.length(); i++) {
                                        JSONObject object = ModelWiseDataArray.optJSONObject(i);
                                        String Category = object.optString("Category");
                                        String Target = object.optString("Target");
                                        String Achivement = object.optString("Achivement");
                                        String TobeAchv = object.optString("TobeAchv");
                                        targetList.add(new NewTargetModel(Category,Target,Achivement,TobeAchv));
                                    }
                                    NewTargetAdapter adapter = new NewTargetAdapter(NewTargetActivity.this,targetList);
                                    binding.rvTarget.setAdapter(adapter);
                                }


                                binding.llLoading.setVisibility(View.GONE);
                                binding.llNoDataFound.setVisibility(View.GONE);
                                binding.llDataLayout.setVisibility(View.VISIBLE);
                            } else {
                                binding.llLoading.setVisibility(View.GONE);
                                binding.llNoDataFound.setVisibility(View.VISIBLE);
                                binding.llDataLayout.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.llLoading.setVisibility(View.GONE);
                binding.llNoDataFound.setVisibility(View.VISIBLE);
                binding.llDataLayout.setVisibility(View.GONE);
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(NewTargetActivity.this);
        requestQueue.add(stringRequest);
    }

    private void btnClick() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.llModelWise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llModelWise.setBackgroundResource(R.drawable.lldesign21);
                binding.llMarginWise.setBackgroundResource(R.drawable.lldesign23);
                getModelWise();
            }
        });
        binding.llMarginWise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llModelWise.setBackgroundResource(R.drawable.lldesign23);
                binding.llMarginWise.setBackgroundResource(R.drawable.lldesign21);
                getMarginWise();
            }
        });
    }

    private void getModelWise() {
        if (ModelWiseDataArray.length() > 0){
            targetList.clear();
            for (int i = 0; i < ModelWiseDataArray.length(); i++) {
                JSONObject object = ModelWiseDataArray.optJSONObject(i);
                String Category = object.optString("Category");
                String Target = object.optString("Target");
                String Achivement = object.optString("Achivement");
                String TobeAchv = object.optString("TobeAchv");
                targetList.add(new NewTargetModel(Category,Target,Achivement,TobeAchv));
            }
            NewTargetAdapter adapter = new NewTargetAdapter(NewTargetActivity.this,targetList);
            binding.rvTarget.setAdapter(adapter);
        } else {
            binding.llLoading.setVisibility(View.GONE);
            binding.llNoDataFound.setVisibility(View.GONE);
            binding.llDataLayout.setVisibility(View.VISIBLE);
        }
    }

    private void getMarginWise() {
        if (MarginWiseDataArray.length() > 0){
            targetList.clear();
            for (int i = 0; i < MarginWiseDataArray.length(); i++) {
                JSONObject object = MarginWiseDataArray.optJSONObject(i);
                String Category = object.optString("Category");
                String Target = object.optString("Target");
                String Achivement = object.optString("Achivement");
                String TobeAchv = object.optString("TobeAchv");
                targetList.add(new NewTargetModel(Category,Target,Achivement,TobeAchv));
            }
            NewTargetAdapter adapter = new NewTargetAdapter(NewTargetActivity.this,targetList);
            binding.rvTarget.setAdapter(adapter);
        } else {
            binding.llLoading.setVisibility(View.GONE);
            binding.llNoDataFound.setVisibility(View.GONE);
            binding.llDataLayout.setVisibility(View.VISIBLE);
        }
    }
}
