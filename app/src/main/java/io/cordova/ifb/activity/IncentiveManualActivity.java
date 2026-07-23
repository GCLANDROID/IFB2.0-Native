package io.cordova.ifb.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.IncentiveManualAdapter;
import io.cordova.ifb.databinding.ActivityIncentiveBinding;
import io.cordova.ifb.databinding.ActivityIncentiveManualBinding;
import io.cordova.ifb.module.ECatelogModel;
import io.cordova.ifb.module.IncentiveManualModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class IncentiveManualActivity extends AppCompatActivity {
    ActivityIncentiveManualBinding binding;
    ArrayList<IncentiveManualModule>itemList=new ArrayList<>();
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil. setContentView(this,R.layout.activity_incentive_manual);
       initView();
    }

    private void initView(){
        prefManager=new  PrefManager(IncentiveManualActivity.this);
        binding.rvItem.setLayoutManager(new LinearLayoutManager(IncentiveManualActivity.this));
        getItemlist();
    }


    private void getItemlist(){
        ProgressDialog pd=new ProgressDialog(IncentiveManualActivity.this);
        pd.setCancelable(false);
        pd.show();
        pd.setMessage("Loading");
        String surl =  AppController.APIV2URL+"api/get_IncentivePolicy?ECatalogID=0&CategoryId=0&Operation=3";
        Log.d("inputSalesReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);
                        pd.dismiss();

                        // attendabceInfiList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String CategoryName=obj.optString("CategoryName");
                                    String FNameUrl=obj.optString("FNameUrl");

                                    IncentiveManualModule incentiveModel=new IncentiveManualModule();
                                    incentiveModel.setDocName(CategoryName);
                                    incentiveModel.setDocURL(FNameUrl);
                                    itemList.add(incentiveModel);


                                }


                                IncentiveManualAdapter manualAdapter=new IncentiveManualAdapter(itemList,IncentiveManualActivity.this);
                                binding.rvItem.setAdapter(manualAdapter);
                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {
                               pd.dismiss();
                               showAlert();
                               Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(IncentiveManualActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               pd.dismiss();

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(IncentiveManualActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(IncentiveManualActivity.this);

        requestQueue.add(stringRequest);
    }


    private void showAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No data found");
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        onBackPressed();
                    }
                });
    }
}