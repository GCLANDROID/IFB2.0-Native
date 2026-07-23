package io.cordova.ifb.activity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class ReplenishedActivity extends AppCompatActivity {
    LinearLayout llTotal, llReplenished, llPending;
    TextView tvTotal, tvReplenshed, tvPending;
    PrefManager prefManager;
    ImageView imgBack,imgHome;
    LinearLayout llReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replenished);
        initView();
        onClick();
    }

    private void initView() {
        prefManager=new PrefManager(ReplenishedActivity.this);
        llTotal = (LinearLayout) findViewById(R.id.llTotal);
        llReplenished = (LinearLayout) findViewById(R.id.llReplenished);
        llPending = (LinearLayout) findViewById(R.id.llPending);
        llReport=(LinearLayout)findViewById(R.id.llReport);

        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvReplenshed = (TextView) findViewById(R.id.tvReplenshed);
        tvPending = (TextView) findViewById(R.id.tvPending);

        imgHome=(ImageView)findViewById(R.id.imgHome);
        imgBack=(ImageView)findViewById(R.id.imgBack);

        getItemNumber();
    }

    private void onClick() {
        llPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReplenishedActivity.this, ReplenishedItemActivity.class);
                intent.putExtra("status","1");
                startActivity(intent);
            }
        });
        llTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReplenishedActivity.this, ReplenishedItemActivity.class);
                intent.putExtra("status","0");
                startActivity(intent);
            }
        });

        llReplenished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReplenishedActivity.this, ReplenishedItemActivity.class);
                intent.putExtra("status","2");
                startActivity(intent);
            }
        });

        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReplenishedActivity.this, ReplenshedReportActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReplenishedActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void getItemNumber() {
        final ProgressDialog pd = new ProgressDialog(ReplenishedActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        String surl =  AppController.APIV2URL+"api/get_DisplaymatrixReplaced?DSR_ReferenceNo=0&AEMEmployeeID="+prefManager.getUserId()+"&Status=1&Opertaion=1&SubOpertaion=1&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputCheck", surl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        pd.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            JSONArray responseData = job1.optJSONArray("responseData");
                            JSONObject jobj = responseData.optJSONObject(0);
                            String Total_Model = jobj.optString("Total_Model");
                            String Total_Updated = jobj.optString("Total_Updated");
                            String Total_Pending = jobj.optString("Total_Pending");
                            tvTotal.setText(Total_Model);
                            tvPending.setText(Total_Pending);
                            tvReplenshed.setText(Total_Updated);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ReplenishedActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();

                Toast.makeText(ReplenishedActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
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
//        RequestQueue requestQueue = Volley.newRequestQueue(ReplenishedActivity.this);
//        requestQueue.add(stringRequest);
        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(ReplenishedActivity.this);

        requestQueue.add(stringRequest);

    }
}