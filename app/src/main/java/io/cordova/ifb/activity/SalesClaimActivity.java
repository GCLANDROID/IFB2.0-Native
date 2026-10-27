package io.cordova.ifb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.ClaimAdapter;
import io.cordova.ifb.databinding.ActivitySalesClaimBinding;
import io.cordova.ifb.module.ClaimItem;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class SalesClaimActivity extends AppCompatActivity {
    ActivitySalesClaimBinding binding;

    private ClaimAdapter adapter;
    private final List<ClaimItem> claimList = new ArrayList<>();
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sales_claim);
        initeView();
    }

    private void initeView(){

        prefManager=new PrefManager(SalesClaimActivity.this);
        binding.claimsRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        binding.claimsRecyclerView.setHasFixedSize(false);

        // Load sample claim data

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("AEMEmployeeID",prefManager.getUserId());
            loadClaimData(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Initialize adapter

    }




    private void loadClaimData(JSONObject jsonObject) {
        Log.e("LOGIN", "login: " + jsonObject.toString());
       binding.llLoader.setVisibility(View.VISIBLE);
       binding.llMainData.setVisibility(View.GONE);
        binding.llNoData.setVisibility(View.GONE);
        AndroidNetworking.post(AppController.APIV2URL + "api/IFBSalesPIClaim/GetList")
                .addJSONObjectBody(jsonObject)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONObject job1 = response;
                            Log.e("claimresponse", "@@@@@@" + job1);
                            claimList.clear();
                            String Response_Code = job1.optString("Response_Code");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (Response_Code.equals("101")) {

                                // Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();

                                JSONArray responseData = job1.optJSONArray("Response_Data");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String ClaimID = obj.optString("ClaimID");
                                    String SaleDate = obj.optString("SaleDate");
                                    String InvoiceValue = obj.optString("InvoiceValue");
                                    String Product = obj.optString("Product");
                                    int IsModelCodeMissing = obj.optInt("IsModelCodeMissing");
                                    String FNAME = obj.optString("FNAME");
                                    String Mobile_No = obj.optString("Mobile_No");
                                    ClaimItem claimItem =new ClaimItem(ClaimID,SaleDate, InvoiceValue,"","",IsModelCodeMissing,Product,FNAME,Mobile_No);
                                    claimList.add(claimItem);




                                    // Firebase(UserName);
                                }

                                binding.llLoader.setVisibility(View.GONE);
                                binding.llMainData.setVisibility(View.VISIBLE);
                                binding.llNoData.setVisibility(View.GONE);

                                adapter = new ClaimAdapter(
                                        SalesClaimActivity.this,
                                        claimList
                                );

                                // Attach adapter
                                binding.claimsRecyclerView.setAdapter(adapter);

//

                                } else {
                                binding.llLoader.setVisibility(View.GONE);
                                binding.llMainData.setVisibility(View.GONE);
                                binding.llNoData.setVisibility(View.VISIBLE);
                                }



                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(LoginActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("LOGIN", "onError: " + error);
                        binding.llLoader.setVisibility(View.GONE);
                        binding.llMainData.setVisibility(View.GONE);
                        binding.llNoData.setVisibility(View.VISIBLE);


                    }
                });
    }
}