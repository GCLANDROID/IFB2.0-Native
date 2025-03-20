package io.cordova.ifb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.IncentiveCategoryBlockOneAdapter;
import io.cordova.ifb.adapter.IncentiveCategoryBlockTwoAdapter;
import io.cordova.ifb.adapter.IncentiveCategoryEarningAdapter;
import io.cordova.ifb.adapter.IncentiveDeductionAdapter;
import io.cordova.ifb.databinding.ActivityIncentiveCalculationDetailsBinding;
import io.cordova.ifb.module.DeductionModule;
import io.cordova.ifb.module.IncentiveCalculationModule;
import io.cordova.ifb.module.IncentiveCategoryDetailsModel;
import io.cordova.ifb.module.IncentiveCategoryEarningModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class IncentiveCalculationDetailsActivity extends AppCompatActivity {

    ActivityIncentiveCalculationDetailsBinding binding;
    ArrayList<IncentiveCategoryDetailsModel>blockOneList=new ArrayList<>();
    ArrayList<IncentiveCategoryDetailsModel>blockTwoList=new ArrayList<>();
    ArrayList<String>monthList=new ArrayList<>();
    ArrayList<String>yearList=new ArrayList<>();
    int y;
    String year,month,financialYear;
    PrefManager prefManager;
    ArrayList<DeductionModule>deductionList=new ArrayList<>();
    AlertDialog deductionDialog,earningDialog;
    ArrayList<IncentiveCategoryEarningModule>earningList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil. setContentView(this,R.layout.activity_incentive_calculation_details);
       initView();
    }

    private void initView(){
        prefManager=new PrefManager(IncentiveCalculationDetailsActivity.this);
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

        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");

        yearList.add("2023-2024");
        yearList.add("2024-2025");
        yearList.add("2025-2026");
        yearList.add("2026-2027");

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>
                (IncentiveCalculationDetailsActivity.this, android.R.layout.simple_spinner_item,
                        monthList); //selected item will look like a spinner set from XML
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spMonth.setAdapter(monthAdapter);

        int pos=monthList.indexOf(month);
        binding.spMonth.setSelection(pos-1);


        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>
                (IncentiveCalculationDetailsActivity.this, android.R.layout.simple_spinner_item,
                        yearList); //selected item will look like a spinner set from XML
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spFin.setAdapter(yearAdapter);

        int yearpos=yearList.indexOf(financialYear);
        binding.spFin.setSelection(yearpos);


        LinearLayoutManager onelayoutManager
                = new LinearLayoutManager(IncentiveCalculationDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        binding. rvBlockOne.setLayoutManager(onelayoutManager);

        LinearLayoutManager twolayoutManager
                = new LinearLayoutManager(IncentiveCalculationDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        binding. rvBlockTwo.setLayoutManager(twolayoutManager);

        binding.spFin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year=yearList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                month=monthList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIncentiveData();
            }
        });





    }

    private void blockOneAdapter(){
        IncentiveCategoryBlockOneAdapter oneAdapter=new IncentiveCategoryBlockOneAdapter(blockOneList,this);
        binding.rvBlockOne.setAdapter(oneAdapter);
    }

    private void blockTwoAdapter(){
        IncentiveCategoryBlockTwoAdapter oneAdapter=new IncentiveCategoryBlockTwoAdapter(blockTwoList,IncentiveCalculationDetailsActivity.this);
        binding.rvBlockTwo.setAdapter(oneAdapter);
    }


    public void getIncentiveData() {

        String surl = AppController.APIURL+ "api/get_EmployeeIncentive?FinancialYear="+year+"&Month="+month+"&AEMEmployeeID="+prefManager.getUserId()+"&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputLogin", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        progressBar.dismiss();
                        blockTwoList.clear();
                        blockOneList.clear();
                        deductionList.clear();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                binding.llData.setVisibility(View.VISIBLE);
                                binding.llNoData.setVisibility(View.GONE);
                               JSONObject IncentiveDetails=job1.optJSONObject("IncentiveDetails");
                               String Month=IncentiveDetails.optString("Month");
                               binding.tvMonth.setText(Month);

                               String SelfRank=IncentiveDetails.optString("SelfRank");
                               binding.tvSelfRank.setText(SelfRank);

                                String AverageIncentiveBranch=IncentiveDetails.optString("AverageIncentiveBranch");
                                binding.tvIncentiveBranch.setText(AverageIncentiveBranch);

                                String SelfAverageIncentive=IncentiveDetails.optString("CounterTypeRank");
                                binding.tvEarned.setText(SelfAverageIncentive);

                                String FinalIncentive=IncentiveDetails.optString("FinalIncentive");
                                binding.tvFinalIncentiveAmt.setText(FinalIncentive);

                                String FY_Max_Inc=IncentiveDetails.optString("FY_Max_Inc");
                                binding.tvHighestIncentive.setText(FY_Max_Inc);



                                JSONArray IncentiveCategory=job1.optJSONArray("IncentiveCategory");
                                for (int i=0;i<IncentiveCategory.length();i++){
                                    JSONObject incentiveCatObj=IncentiveCategory.optJSONObject(i);
                                    String Category=incentiveCatObj.optString("Category");
                                    String Target=incentiveCatObj.optString("Target");
                                    String Achive=incentiveCatObj.optString("Achive");
                                    String AchievementPercentage=incentiveCatObj.optString("AchievementPercentage");
                                    String Amount=incentiveCatObj.optString("Amount");
                                    JSONArray IncentiveCategoryEarningDetails=incentiveCatObj.optJSONArray("IncentiveCategoryEarningDetails");
                                    IncentiveCategoryDetailsModel module=new IncentiveCategoryDetailsModel();
                                    module.setCategory(Category);
                                    module.setTgt(Target);
                                    module.setAcheived(Achive);
                                    module.setAchievement(AchievementPercentage);
                                    module.setAmt(Amount);
                                    if (IncentiveCategoryEarningDetails.length()>0){
                                        module.setEarningList(IncentiveCategoryEarningDetails.toString());
                                    }else {
                                        module.setEarningList("");
                                    }

                                    blockOneList.add(module);
                                }


                                JSONArray OtherIncentive=job1.optJSONArray("OtherIncentive");
                                if (OtherIncentive.length()>0) {
                                    for (int i = 0; i < OtherIncentive.length(); i++) {
                                        JSONObject OtherIncentiveObj = OtherIncentive.optJSONObject(i);
                                        String Category = OtherIncentiveObj.optString("Category");
                                        String Amount = OtherIncentiveObj.optString("Amount");
                                        IncentiveCategoryDetailsModel module = new IncentiveCategoryDetailsModel();
                                        module.setCategory(Category);
                                        module.setTgt("");
                                        module.setAcheived("");
                                        module.setAchievement("");
                                        module.setAmt(Amount);
                                        blockTwoList.add(module);
                                    }
                                    blockTwoAdapter();
                                }

                                JSONArray DeductionDetails=job1.optJSONArray("DeductionDetails");
                                if (DeductionDetails.length()>0){
                                    for (int i=0;i<DeductionDetails.length();i++){
                                        JSONObject deductionOBJ=DeductionDetails.optJSONObject(i);
                                        String Remarks=deductionOBJ.optString("Remarks");
                                        String Amount=deductionOBJ.optString("Amount");
                                        DeductionModule deductionModule=new DeductionModule();
                                        deductionModule.setAmt(Amount);
                                        deductionModule.setRemarks(Remarks);
                                        deductionList.add(deductionModule);
                                    }
                                }

                                blockOneAdapter();



                            } else {
                                binding.llData.setVisibility(View.GONE);
                                binding.llNoData.setVisibility(View.VISIBLE);

                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(LoginActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(IncentiveCalculationDetailsActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();

                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(IncentiveCalculationDetailsActivity.this);
        requestQueue.add(stringRequest);

    }


    public void deductionAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(IncentiveCalculationDetailsActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_deduction_details, null);
        dialogBuilder.setView(dialogView);
        ImageView imgCancel = (ImageView) dialogView.findViewById(R.id.imgCancel);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deductionDialog.dismiss();


            }
        });

        RecyclerView rvItem=(RecyclerView)dialogView.findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(IncentiveCalculationDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);

        IncentiveDeductionAdapter deductionAdapter=new IncentiveDeductionAdapter(deductionList);
        rvItem.setAdapter(deductionAdapter);



        deductionDialog = dialogBuilder.create();
        deductionDialog.setCancelable(false);
        Window window = deductionDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        deductionDialog.show();
    }

    public void getEarningDetails(String list,String cat){
        earningList.clear();
        try {
            JSONArray IncentiveCategoryEarningDetails=new JSONArray(list);
            if (IncentiveCategoryEarningDetails.length()>0){
                for (int i=0;i<IncentiveCategoryEarningDetails.length();i++){
                    JSONObject object=IncentiveCategoryEarningDetails.optJSONObject(i);
                    String ModelCode=object.optString("ModelCode");
                    String Sold=object.optString("Sold");
                    String Amount=object.optString("Amount");
                    IncentiveCategoryEarningModule model=new IncentiveCategoryEarningModule();
                    model.setCategory(ModelCode);
                    model.setQty(Sold);
                    model.setAmount(Amount);
                    earningList.add(model);
                }
                earningDetailsAlert(cat);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void earningDetailsAlert(String cat) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(IncentiveCalculationDetailsActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_category_earning_details, null);
        dialogBuilder.setView(dialogView);
        ImageView imgCancel = (ImageView) dialogView.findViewById(R.id.imgCancel);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                earningDialog.dismiss();


            }
        });
        TextView tvCat=(TextView)dialogView.findViewById(R.id.tvCat);
        tvCat.setText(cat+" Earning Details");

        RecyclerView rvItem=(RecyclerView)dialogView.findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(IncentiveCalculationDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);

        IncentiveCategoryEarningAdapter earningAdapter=new IncentiveCategoryEarningAdapter(earningList);
        rvItem.setAdapter(earningAdapter);



        earningDialog = dialogBuilder.create();
        earningDialog.setCancelable(false);
        Window window = earningDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        earningDialog.show();
    }
}